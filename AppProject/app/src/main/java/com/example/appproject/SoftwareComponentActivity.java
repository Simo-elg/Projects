package com.example.appproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class SoftwareComponentActivity extends AppCompatActivity {
    // to edit a component the storek uses the same activity
    public static int id_issued_by_storek = -1;

    // initialization
    String type;
    com.google.android.material.textfield.TextInputEditText sous_type_Input;
    com.google.android.material.textfield.TextInputEditText descriptionInput;
    TextView number_scrollInput; // shows how many times we'll add this component
    com.google.android.material.textfield.TextInputEditText versionInput;
    com.google.android.material.textfield.TextInputEditText licenseInput;
    com.google.android.material.textfield.TextInputEditText compatibilityInput;
    EditText priceInput;
    Button goBack;
    Button logout;
    Button add;

    DDB database;
    int count2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storekeeper_addsoftware);

        count2 = 0;
        type = "Software";
        sous_type_Input = findViewById(R.id.editText35);
        descriptionInput = findViewById(R.id.editText34);
        versionInput = findViewById(R.id.editText33);
        licenseInput = findViewById(R.id.editText36);
        compatibilityInput = findViewById(R.id.editText31);
        number_scrollInput = findViewById(R.id.textView126);
        priceInput = findViewById(R.id.editTextNumber6);
        goBack = findViewById(R.id.logoutbutton_id9);
        logout = findViewById(R.id.logoutbutton_id10);
        add = findViewById(R.id.add_but3); // Initialize "add" button (ensure correct ID in XML)

        database = new DDB(this); // Make sure DDB is properly defined

        logout.setOnClickListener(view -> {
            Intent intent = new Intent(SoftwareComponentActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Go back button click listener
        goBack.setOnClickListener(view -> {
            Intent intent = new Intent(SoftwareComponentActivity.this, StorekeeperActivity.class);
            startActivity(intent);
        });

    }
    // Quantity
    public void increment(View view) {
        count2++;
        number_scrollInput.setText("" + count2); // Update scroll view
    }
    public void decrement(View view) {
        if (count2 <= 0) {
            count2 = 0;
        } else {
            count2--;
        }
        number_scrollInput.setText("" + count2);
    }
    public void addComponent(View view){
        String sous_type = Objects.requireNonNull(sous_type_Input.getText()).toString();
        String description = Objects.requireNonNull(descriptionInput.getText()).toString();
        String version = Objects.requireNonNull(versionInput.getText()).toString();
        String license = Objects.requireNonNull(licenseInput.getText()).toString();
        String compatibility = Objects.requireNonNull(compatibilityInput.getText()).toString();
        String price = priceInput.getText().toString();

        // Validate input fields
        if (sous_type.isEmpty() || description.isEmpty() || version.isEmpty() || license.isEmpty() || compatibility.isEmpty() || price.isEmpty()) {
            Toast.makeText(SoftwareComponentActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add component to the database
        if (id_issued_by_storek == -1){
            SoftwareComponent sComp = new SoftwareComponent(type, sous_type, description, count2, price, version, license, compatibility);
            boolean res = database.addSComponent(sComp);
            Log.d("DB", "Result: " + res);

            if (res) {
                Toast.makeText(SoftwareComponentActivity.this, "Component added successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SoftwareComponentActivity.this, StorekeeperActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(SoftwareComponentActivity.this, "Component Not added...", Toast.LENGTH_SHORT).show();
            }
        } else { // update
            int result = database.updateSoftwareComponent(id_issued_by_storek, sous_type, description, count2, price, version, license, compatibility);
            id_issued_by_storek = -1;
            if (result == 0){
                Toast.makeText(SoftwareComponentActivity.this, "couldn't update component...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SoftwareComponentActivity.this, "Component updated !", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(SoftwareComponentActivity.this, StorekeeperActivity.class);
            startActivity(intent);

        }
    }
}