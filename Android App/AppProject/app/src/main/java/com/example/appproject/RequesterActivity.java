package com.example.appproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appproject.Interface.ItemClickListner;
import com.example.appproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class RequesterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    public List<Products> productList;
    public DDB ddb;
    public String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ddb = new DDB(this);
        productList = ddb.getProducts(); // Récupérer les produits de la base de données

        //clientId = getIntent().getStringExtra("clientId"); // Recover the client's Id via intent.
        clientId = MainActivity.idcli;
        if (clientId != null){
            Toast.makeText(RequesterActivity.this, "Hi " + clientId, Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(RequesterActivity.this, clientId, Toast.LENGTH_SHORT).show();
        // Initialiser l'adaptateur avec l'écouteur de click
        productAdapter = new ProductAdapter(this, productList, new ItemClickListner() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Products selectedProduct = productList.get(position);
                String id = selectedProduct.getPid();
                boolean res = addIdToCart(clientId, id);
                if (res){
                    Toast.makeText(RequesterActivity.this,  selectedProduct.getPname() + " Selected" , Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RequesterActivity.this,  selectedProduct.getPname() + " not Selected" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.setAdapter(productAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_cart){
                Intent intent = new Intent(RequesterActivity.this, CartActivity.class);
                intent.putExtra("clientId", clientId);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_setting) {
                startActivity(new Intent(RequesterActivity.this, SettingActivity.class));
                return true;
            } else if (id == R.id.home) {
                Intent intent = new Intent(RequesterActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_history) {
                Intent intent = new Intent(RequesterActivity.this, OrderActivity.class);
                intent.putExtra("clientId", clientId);
                startActivity(intent);
                return true;
            } else { // lastcase
                return false;
            }
        });

    }

    public boolean addIdToCart(String clientId, String compId) {
        // Check if either clientId or compId is null or empty
        if (clientId == null || clientId.isEmpty()) {
            throw new IllegalArgumentException("Client ID cannot be null or empty");
        }
        if (compId == null || compId.isEmpty()) {
            throw new IllegalArgumentException("Component ID cannot be null or empty");
        }

        SQLiteDatabase db = ddb.getWritableDatabase();
        String query = "SELECT " + DDB.COLUMN_CART + " FROM " + DDB.TABLE_CLIENTS + " WHERE " + DDB.COLUMN_ID_CLIENT + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{clientId});

        String newCart = "";
        long res = 0;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String currentCart = cursor.getString(cursor.getColumnIndex(DDB.COLUMN_CART));

            if (currentCart != null && !currentCart.isEmpty()) {
                newCart = currentCart + "," + compId;
            } else {
                newCart = compId;
            }

            ContentValues values = new ContentValues();
            values.put(DDB.COLUMN_CART, newCart);
            res = db.update(DDB.TABLE_CLIENTS, values, DDB.COLUMN_ID_CLIENT + " = ?", new String[]{clientId});
        }
        cursor.close();
        return res != -1;
    }

//    public void addElem(View view){
//        int position = (int) view.getTag();
//        Products selectedProduct = productList.get(position);
//        String id = selectedProduct.getPid();
//
//        ddb.addIdToCart(clientId, id);              // We give the client's Id and the Component's Id that he chose.
//        //Toast.makeText(RequesterActivity.this, selectedProduct.getPname() + " ajouté au panier", Toast.LENGTH_SHORT).show();
//    }

    public void logout(View view) {
        Intent intent = new Intent(RequesterActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
