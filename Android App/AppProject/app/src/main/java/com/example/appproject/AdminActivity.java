package com.example.appproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    EditText id_to_delete;
    EditText id_to_update;
    DDB databaseManager;
    Button btnClientData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);


        id_to_delete = findViewById(R.id.delete_id);
        id_to_update = findViewById(R.id.update_id);
        databaseManager = new DDB(this);
        btnClientData = (Button) findViewById(R.id.viewData_but);
        viewClientData();
    }

    public void viewClientData(){
        btnClientData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = databaseManager.getClientData();
                        if (res.getCount() == 0){
                            showMessage("Error", "No Data Found !");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        int idIndex = res.getColumnIndex(databaseManager.COLUMN_ID_CLIENT);
                        int firstNameIndex = res.getColumnIndex(databaseManager.COLUMN_FIRSTNAME_CLIENT);
                        int lastNameIndex = res.getColumnIndex(databaseManager.COLUMN_LASTNAME_CLIENT);
                        int emailIndex = res.getColumnIndex(databaseManager.COLUMN_EMAIL_CLIENT);
                        int passwordIndex = res.getColumnIndex(databaseManager.COLUMN_PASSWORD_CLIENT);
                        while (res.moveToNext()){
                            buffer.append("id : " + res.getString(idIndex) + "\n");
                            buffer.append("Firstname : " + res.getString(firstNameIndex) + "\n");
                            buffer.append("Lastname : " + res.getString(lastNameIndex) + "\n");
                            buffer.append("Email : " + res.getString(emailIndex) + "\n");
                            buffer.append("Password : " + res.getString(passwordIndex) + "\n\n");
                        }
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void logout(View view){
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void goBack(View view){
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void delete_user(View view){
        if (!id_to_delete.getText().toString().isEmpty()){
            String id = id_to_delete.getText().toString();
            int res = databaseManager.deleteClient(id);
            if (res != 0){
                Toast.makeText(AdminActivity.this, "Requester successfully removed !", Toast.LENGTH_SHORT).show();
                return;
            }else {
                Toast.makeText(AdminActivity.this, "Requester Not removed !", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AdminActivity.this, "Please fill the id field", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(AdminActivity.this, "No requester has this ID", Toast.LENGTH_SHORT).show();
    }
    public void create_req(View view){
        SignUpActivity.issued_by_admin = true; // we let SignUpActivity know that it going to be used by an admin
        Intent intent = new Intent(AdminActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
    public void update_req(View view){
        SignUpActivity.issued_by_admin = true;
        SignUpActivity.id_issued_to_update = Integer.parseInt(id_to_update.getText().toString());
        Intent intent = new Intent(AdminActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
    public void vider_ddb(View view){
        databaseManager.deleteAllStockItems();
        databaseManager.deleteAllRequesters();
        Toast.makeText(this, "All items deleted!", Toast.LENGTH_SHORT).show();
    }
    public void resDDB(View view){
        vider_ddb(view); // first we empty our ddb
        databaseManager.loadDataFromCsvFiles(this); // then load the data
        Toast.makeText(this, "Database reset successful !", Toast.LENGTH_SHORT).show();
    }
    public void resStockOnly(View view){
        databaseManager.deleteAllStockItems();
        DDB.res_stock_only = true; // we tell our app that we'll only load the stock
        databaseManager.loadDataFromCsvFiles(this);
        Toast.makeText(this, "Database reset successful !", Toast.LENGTH_SHORT).show();
    }
}
