package com.example.appproject;

import static com.example.appproject.App.orders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
public class OrderActivity extends AppCompatActivity { //new
    //new
    private RecyclerView rvOrders;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orders;
    private DDB db;
    public String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_commands);

        rvOrders = findViewById(R.id.rv_history);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));

        db = new DDB(this);
        clientId = MainActivity.idcli;

        Toast.makeText(OrderActivity.this,  "clientid : " + clientId , Toast.LENGTH_SHORT).show();

        List<String> orderIds = db.getOrderIdsForClient(clientId);

        orders = db.getOrdersByIds(orderIds); // Create this method to retrieve full order details

//        Log.d("OrderActivity", "clientId: " + clientId);
//        Log.d("OrderActivity", "orderIds: " + orderIds);
//        Log.d("OrderActivity", "orders count: " + (orders != null ? orders.size() : "null"));

        orderAdapter = new OrderAdapter(this, orders);
        rvOrders.setAdapter(orderAdapter);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView3);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id_item = item.getItemId();
            if (id_item == R.id.nav_cart){
                Intent intent = new Intent(OrderActivity.this, CartActivity.class);
                intent.putExtra("clientId", clientId);
                startActivity(intent);
                return true;
            } else if (id_item == R.id.nav_setting) {
                startActivity(new Intent(OrderActivity.this, SettingActivity.class));
                return true;
            } else if (id_item == R.id.home) {
                Intent intent = new Intent(OrderActivity.this, RequesterActivity.class);
                startActivity(intent);
                return true;
            } else if (id_item == R.id.nav_history) {
                Intent intent = new Intent(OrderActivity.this, OrderActivity.class);
                intent.putExtra("clientId", clientId);
                startActivity(intent);
                return true;
            } else { // lastcase
                return false;
            }
        });

    }
    // Method to remove an order from the client's orders
    public void removeOrderFromClient(String clientId, String orderId, int position) {
        // Delete the order from the database
        boolean success = db.removeOrderFromClient(clientId, orderId);

        if (success) {
            // If successfully removed, update the list
            orders.remove(position); // Remove the order from the list at the given position

            // Notify the adapter that the item was removed
            orderAdapter.notifyItemRemoved(position);  // Notify only the specific item that was removed
            Toast.makeText(OrderActivity.this, "Order removed successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(OrderActivity.this, "Failed to remove order", Toast.LENGTH_SHORT).show();
        }
    }
}