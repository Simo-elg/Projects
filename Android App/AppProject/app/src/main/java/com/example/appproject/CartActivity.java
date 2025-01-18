package com.example.appproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.Interface.ItemClickListner;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    public DDB ddb;
    private RecyclerView rvMyCart;
    CartAdapter cart_adapter;
    List<CartModel> cartItems;
    DDB db;
    String id ; // for the client

    String date_of_creation;
    String date_of_modification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Date currentDate = new Date();
        date_of_creation = currentDate.toString();
        date_of_modification = currentDate.toString();

        rvMyCart = findViewById(R.id.rv_cart);
        rvMyCart.setLayoutManager(new LinearLayoutManager(this));

        id = MainActivity.idcli;
        Log.d("CartActivity", "Client ID: " + id);
        db = new DDB(this);

        cartItems = db.getCartItemsForClient(id);

        cart_adapter = new CartAdapter(this, cartItems, new ItemClickListner() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                CartModel selectedProduct = cartItems.get(position);
                String compId = selectedProduct.getProductId();
                boolean res = db.deleteComponent(id, compId);
                if (res){
                    Toast.makeText(CartActivity.this, "Component removed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CartActivity.this, CartActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(CartActivity.this, "Failed to remove component", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rvMyCart.setAdapter(cart_adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id_item = item.getItemId();
            if (id_item == R.id.nav_cart){
                Intent intent = new Intent(CartActivity.this, CartActivity.class);
                intent.putExtra("clientId", id);
                startActivity(intent);
                return true;
            } else if (id_item == R.id.nav_setting) {
                startActivity(new Intent(CartActivity.this, SettingActivity.class));
                return true;
            } else if (id_item == R.id.home) {
                Intent intent = new Intent(CartActivity.this, RequesterActivity.class);
                startActivity(intent);
                return true;
            } else if (id_item == R.id.nav_history) {
                Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                intent.putExtra("clientId", id);
                startActivity(intent);
                return true;
            } else { // lastcase
                return false;
            }
        });
    }

    public void addOrder(View view){
        boolean res = addOr(id, cartItems, date_of_creation);
        if (res){
            Toast.makeText(CartActivity.this, "Order Sent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CartActivity.this, CartActivity.class));
        }else{
            Toast.makeText(CartActivity.this, "Order not sent", Toast.LENGTH_SHORT).show();

        }
    }

    // méthode censée être dans DDB
    private boolean addOr(String clientId, List<CartModel> CartItems, String date_of_creation){
        SQLiteDatabase data = db.getWritableDatabase();

        StringBuilder orderItems = new StringBuilder();
        for (CartModel item : cartItems){
            orderItems.append(item.getProductId()).append(",");
        }
        if (orderItems.length() > 0) {
            orderItems.setLength(orderItems.length() - 1);
        }

        long newOrderId = -1;
        data.beginTransaction();
        try { // putting the newly created Order in its table
            ContentValues contentValues = new ContentValues();
            contentValues.put(DDB.COLUMN_ID_CLIENT, clientId);
            contentValues.put(DDB.COLUMN_STATE, "Awaiting Approval...");
            contentValues.put(DDB.COLUMN_MESSAGE, "New Order created");
            contentValues.put(DDB.COLUMN_COMPONENTS, orderItems.toString());
            contentValues.put(DDB.COLUMN_DATE_OF_CRETAION, date_of_creation);
            newOrderId = data.insert(DDB.TABLE_ORDERS, null, contentValues);

            if (newOrderId == -1){
                return false;
            }

            String query = "SELECT " + DDB.COLUMN_ORDERS_CLIENT + " FROM " + DDB.TABLE_CLIENTS + " WHERE " + DDB.COLUMN_ID_CLIENT + " = ?";
            Cursor cursor = data.rawQuery(query, new String[]{clientId});

            String updatedOrders;
            if (cursor.moveToFirst()){
                String existingOrders = cursor.getString(cursor.getColumnIndex(DDB.COLUMN_ORDERS_CLIENT));
                if (existingOrders != null && !existingOrders.isEmpty()) {
                    updatedOrders = existingOrders + "," + newOrderId;
                } else {
                    updatedOrders = String.valueOf(newOrderId);
                }
            } else {
                updatedOrders = String.valueOf(newOrderId);
            }
            cursor.close();

            // Mettre à jour la colonne COLUMN_ORDERS_CLIENT du client
            ContentValues updateValues = new ContentValues();
            updateValues.put(DDB.COLUMN_ORDERS_CLIENT, updatedOrders);
            data.update(DDB.TABLE_CLIENTS, updateValues, DDB.COLUMN_ID_CLIENT + " = ?", new String[]{clientId});

            // Clear COLUMN_CART for the client by setting it to null
            ContentValues clearCartValues = new ContentValues();
            clearCartValues.putNull(DDB.COLUMN_CART); // or you could use `clearCartValues.put(DDB.COLUMN_CART, "");` if you want an empty string
            data.update(DDB.TABLE_CLIENTS, clearCartValues, DDB.COLUMN_ID_CLIENT + " = ?", new String[]{clientId});

            // Valider la transaction
            data.setTransactionSuccessful();
            return true;
        } finally {
            data.endTransaction();
            // Start a new intent after the transaction is complete
            Intent intent = new Intent(this, RequesterActivity.class); // replace `NextActivity` with the target activity
            intent.putExtra("clientId", clientId); // pass any necessary data
            startActivity(intent);
        }
    }
}

