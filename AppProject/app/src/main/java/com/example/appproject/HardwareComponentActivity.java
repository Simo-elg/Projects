package com.example.appproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class HardwareComponentActivity extends AppCompatActivity {
    // to edit a component, the storekeeper uses the same activity
    static int id_issued_by_storek = -1;

    String type;
    com.google.android.material.textfield.TextInputEditText sous_type_Input;
    com.google.android.material.textfield.TextInputEditText descriptionInput;
    TextView number_scrollInput;
    com.google.android.material.textfield.TextInputEditText voltageInput;
    com.google.android.material.textfield.TextInputEditText wattageInput;
    com.google.android.material.textfield.TextInputEditText dimensionInput;
    EditText priceInput;
    Button goBack;
    Button logout;
    Button add;

    DDB database;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Removed EdgeToEdge
        setContentView(R.layout.activity_storekeeper_addhardware);

        // Initialize the views
        type = "Hardware";
        sous_type_Input = findViewById(R.id.editText35);
        descriptionInput = findViewById(R.id.editText34);
        voltageInput = findViewById(R.id.editText33);
        number_scrollInput = findViewById(R.id.textView126);
        wattageInput = findViewById(R.id.editText36);
        dimensionInput = findViewById(R.id.editText31);
        priceInput = findViewById(R.id.editTextNumber6);
        goBack = findViewById(R.id.logoutbutton_id9);
        logout = findViewById(R.id.logoutbutton_id10);
        add = findViewById(R.id.add_but3); // Initialize "add" button (ensure correct ID in XML)

        database = new DDB(this); // Make sure DDB is properly defined

        // Logout button click listener
        logout.setOnClickListener(view -> {
            Intent intent = new Intent(HardwareComponentActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Go back button click listener
        goBack.setOnClickListener(view -> {
            Intent intent = new Intent(HardwareComponentActivity.this, StorekeeperActivity.class);
            startActivity(intent);
        });
    }

    // Increment counter
    public void increment(View view) {
        count++;
        number_scrollInput.setText("" + count); // Update scroll view
    }

    // Decrement counter
    public void decrement(View view) {
        if (count <= 0) {
            count = 0;
        } else {
            count--;
        }
        number_scrollInput.setText("" + count);
    }

    public void addComponent(View view){
        String sous_type = Objects.requireNonNull(sous_type_Input.getText()).toString();
        String description = Objects.requireNonNull(descriptionInput.getText()).toString();
        String voltage = Objects.requireNonNull(voltageInput.getText()).toString();
        String wattage = Objects.requireNonNull(wattageInput.getText()).toString();
        String dimension = Objects.requireNonNull(dimensionInput.getText()).toString();
        String price = priceInput.getText().toString();

        // Validate input fields
        if (sous_type.isEmpty() || description.isEmpty() || voltage.isEmpty() || wattage.isEmpty() || dimension.isEmpty() || price.isEmpty()) {
            Toast.makeText(HardwareComponentActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add / update component to the database

        if (id_issued_by_storek == -1){
            HardwareComponent hComp = new HardwareComponent(type, sous_type, description, count, price, voltage, wattage, dimension);
            boolean res = database.addHComponent(hComp);
            Log.d("DB", "Result: " + res);
            if (res) {
                Toast.makeText(HardwareComponentActivity.this, "Component added successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HardwareComponentActivity.this, StorekeeperActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(HardwareComponentActivity.this, "Component Not added!", Toast.LENGTH_SHORT).show();
            }
        } else {
            int result = database.updateHardwareComponent(id_issued_by_storek, sous_type, description, count, voltage, wattage, dimension, price);
            id_issued_by_storek = -1; // we set this back to normal
            if (result == 0){
                Toast.makeText(HardwareComponentActivity.this, "couldn't update component...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HardwareComponentActivity.this, "Component updated !", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(HardwareComponentActivity.this, StorekeeperActivity.class);
            startActivity(intent);
        }
    }
}
