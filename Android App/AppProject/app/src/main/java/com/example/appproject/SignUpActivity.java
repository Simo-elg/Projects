package com.example.appproject;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    static boolean issued_by_admin = false; // Used to indicate if an admin is creating a user
    static int id_issued_to_update = -1; // If updating a user, this will store the user ID to update
    EditText firstnameInput, lastnameInput, emailInput, passwordInput, confirmPasswordInput;
    Button signUpButton;
    DDB dbManager; // Database manager instance
    User user;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context = this;
        // List of Requester users
        ArrayList<Requester> list = App.clients;

        dbManager = new DDB(context);

        // Initialize layout elements
        firstnameInput = findViewById(R.id.firstname_input);
        lastnameInput = findViewById(R.id.lastname_input2);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        signUpButton = findViewById(R.id.signup_button);

        // Set up the sign-up button functionality
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve user-entered data
                String firstname = firstnameInput.getText().toString();
                String lastname = lastnameInput.getText().toString();
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();

                user = new Requester(context,email, password, firstname, lastname, "Requester");

                // Check if all fields are filled
                if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Validate email format
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignUpActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                // At this point, all fields are properly filled

                // Check if updating an existing user
                if (id_issued_to_update != -1) {
                    // Update existing user
                    dbManager.updateRequester(id_issued_to_update, firstname, lastname, email, password);
                    id_issued_to_update = -1; // Reset ID to avoid conflicts
                    issued_by_admin = false;
                    Toast.makeText(SignUpActivity.this, "Requester updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, AdminActivity.class);
                    startActivity(intent);
                }else{
                    if (dbManager.requesterExists(email, password) != null){
                        Toast.makeText(SignUpActivity.this, "Client already exists.", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        long result = dbManager.addClient(user);
                        if (result != 0){
                            Toast.makeText(SignUpActivity.this, "Client successfully created", Toast.LENGTH_SHORT).show();
                            if (issued_by_admin == false){
                                Intent intent = new Intent(SignUpActivity.this, RequesterActivity.class);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(SignUpActivity.this, AdminActivity.class);
                                startActivity(intent);
                            }
                        }else {
                            Toast.makeText(SignUpActivity.this, "Client not created", Toast.LENGTH_SHORT).show();
                        }
                    }
                }



            }
        });
    }


    // Method to navigate to the main menu
    public void menu(View view){
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
