package com.example.appproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StorekeeperActivity extends AppCompatActivity {

    Button addHardware;
    Button addSoftware;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_storekeeper);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Preparation to initialize views for both buttons
        addHardware = findViewById(R.id.add_but4);
        addSoftware = findViewById(R.id.add_but);

        addHardware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StorekeeperActivity.this, HardwareComponentActivity.class);
                startActivity(intent);
            }
        });

        addSoftware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StorekeeperActivity.this, SoftwareComponentActivity.class);
                startActivity(intent);
            }
        });
    }
    public void logout(View view){
        Intent intent = new Intent(StorekeeperActivity.this, MainActivity.class);
        startActivity(intent);
    }


    public void viewStock(View view){
        Intent intent = new Intent(StorekeeperActivity.this, StockActivity.class);
        startActivity(intent);
    }
}