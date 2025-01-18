package com.example.appproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ConcreteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Utilise le layout de ton activité, si nécessaire

        Component component = new Component("Type1", "SubType1", "Description1", 10, "$100") {
            // Implémentation fictive de la classe abstraite Component
        };

    }
}
