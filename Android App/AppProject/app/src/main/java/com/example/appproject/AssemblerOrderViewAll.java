package com.example.appproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssemblerOrderViewAll extends AppCompatActivity {

    TextView clientId, orderId;
    DDB db;
    FragmentContainerView fragmentContainerView;
    String clientID, orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assembler_order_view_all);
//
        RecyclerView rvComponentsStock = findViewById(R.id.rv_components_stock);
        rvComponentsStock.setLayoutManager(new LinearLayoutManager(this));

        db = new DDB(this);

        clientId = findViewById(R.id.client_name);
        orderId = findViewById(R.id.order_id);
        fragmentContainerView = (FragmentContainerView) findViewById(R.id.fragmentContainerView);
        //
        Intent intent = getIntent();
        orderID = intent.getStringExtra("order id");

        clientID = db.getClientIdByOrderId(orderID);

        orderId.setText("Order's ID : " + orderID);

        String clientName = getClientName(orderID);
        clientId.setText("Client's info : " + clientName);

        viewClientData();

        List<Pair<String, Integer>> componentsWithStock = db.getOrderComponentsWithStock(Integer.parseInt(orderID));

        ComponentStockAdapter adapter = new ComponentStockAdapter(this, componentsWithStock);
        rvComponentsStock.setAdapter(adapter);
    }

    public void viewClientData(){
        fragmentContainerView.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        Cursor res = db.getClientData(String.valueOf(clientID));
                        if (res.getCount() == 0){
                            showMessage("Error", "No Data Found !");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        int clientIdIndex = res.getColumnIndex("client_id");
                        int fullNameIndex = res.getColumnIndex("full_name");
                        int emailIndex = res.getColumnIndex("email");
                        int orderCountIndex = res.getColumnIndex("order_count");
                        while (res.moveToNext()){
                            buffer.append("ID : " + res.getString(clientIdIndex) + "\n");
                            buffer.append("FirstName : " + res.getString(fullNameIndex) + "\n");
                            buffer.append("Email : " + res.getString(emailIndex) + "\n");
                            buffer.append("OrderCount : " + res.getString(orderCountIndex) + "\n");
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

    private String getClientName(String orderId) {
        SQLiteDatabase data = db.getReadableDatabase();
        String clientName = null;

        // Requête SQL pour récupérer le nom du client en liant les tables Orders et Clients
        String query = "SELECT " + DDB.COLUMN_FIRSTNAME_CLIENT + ", " + DDB.COLUMN_LASTNAME_CLIENT +
                " FROM " + DDB.TABLE_CLIENTS + " c INNER JOIN " + DDB.TABLE_ORDERS + " o " +
                "ON c." + DDB.COLUMN_ID_CLIENT + " = o." + DDB.COLUMN_ID_CLIENT + " " +
                "WHERE o." + DDB.COLUMN_ID_ORDER + " = ?";

        Cursor cursor = data.rawQuery(query, new String[]{orderId});

        if (cursor.moveToFirst()) {
            // Concaténer prénom et nom
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DDB.COLUMN_FIRSTNAME_CLIENT));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DDB.COLUMN_LASTNAME_CLIENT));
            clientName = firstName + " " + lastName;
        }
        cursor.close();
        data.close();

        return clientName != null ? clientName : "Unknown Client";
    }

    public void go_back(View view){
        Intent intent = new Intent(AssemblerOrderViewAll.this, AssemblerOrderActivity.class);
        startActivity(intent);
    }

}
