package com.example.appproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    static String idcli = "";
    EditText emailI;
    EditText passwordI;

    Button login;
    Button signUpButton;

    DDB db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
        //    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        //    return insets;
        //});

        db = new DDB(this);

        // recovery of what the user has put in the input boxes
        emailI = findViewById(R.id.email_id);
        passwordI = findViewById(R.id.password_id);
        // initialisation of the signup button
        signUpButton = findViewById(R.id.signup_but);


        App MyPC_factory = new App();


        // + setting up the click listener on the login button, (we use a listener instead of a Onclick command)
        login = findViewById(R.id.loginbutton_id);
        login.setOnClickListener(new View.OnClickListener(){ // anonym class
            @Override
            public void onClick(View view){
                String email = emailI.getText().toString();
                String password = passwordI.getText().toString();
                // case where the admin tries to log in
                if (email.equals(MyPC_factory.admin.getEmail()) && password.equals(MyPC_factory.admin.getPassword())){
                    Toast.makeText(MainActivity.this, "login Sucessful !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                // case where the storekeeper tries to log in
                } else if (email.equals(MyPC_factory.storekeeper.getEmail()) && password.equals(MyPC_factory.storekeeper.getPassword())){
                    Toast.makeText(MainActivity.this, "login Sucessful !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, StorekeeperActivity.class);
                    startActivity(intent);
                // case where the assembler tries to log in
                } else if (email.equals(MyPC_factory.assembler.getEmail()) && password.equals(MyPC_factory.assembler.getPassword())){
                    Toast.makeText(MainActivity.this, "login Sucessful !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AssemblerActivity.class);
                    startActivity(intent);
                // in that case it must be a requester
                } else {
                    String requester = db.requesterExists(email, password);
                    if (requester!=null) {
                        Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Animation.class);
                        intent.putExtra("clientId", requester);
                        idcli = requester;
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "This User doesn't exist", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sign up button
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}