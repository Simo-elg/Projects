package com.example.appproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appproject.Interface.ItemClickListner;


import java.util.ArrayList;
import java.util.List;

public class AssemblerOrderActivity extends AppCompatActivity {

    Assembler assembler;
    App app;

    private RecyclerView rvOrder;
    private List<AssemblerOrderModel> orders;
    private AssemblerOrderAdapter assemblerOrderAdapter;
    private DDB db;
    public String clientId;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assembler_commands);

        rvOrder = findViewById(R.id.rv_Order);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));

        db = new DDB(this);
        app = new App();
        assembler = new Assembler(this, "assembler@mypcfactory. com", "1111", "Salma", "Sajid", "Assembler");

        List<String> orderId;
        orders = assembler.viewAllOrders();
        assemblerOrderAdapter = new AssemblerOrderAdapter(this, orders, new ItemClickListner() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                AssemblerOrderModel selectedOrder = orders.get(position);

                int orderId = selectedOrder.getOrderId();
            }
        });
        rvOrder.setAdapter(assemblerOrderAdapter);
    }

    public void goBack0(View view){
        Intent intent = new Intent(AssemblerOrderActivity.this, AssemblerActivity.class);
        startActivity(intent);
    }
}
