package com.example.appproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AssemblerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assembler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.scroll2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void viewListOfOrders(View view){
        Intent intent = new Intent(AssemblerActivity.this, AssemblerOrderActivity.class);
        startActivity(intent);
    }

    public void logout(View view){
        Intent intent = new Intent(AssemblerActivity.this, MainActivity.class);
        startActivity(intent);
    }
}