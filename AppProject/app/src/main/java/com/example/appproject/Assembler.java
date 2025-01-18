package com.example.appproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Assembler extends User{
    //Initialisation of specific StoreKeeper attributes
    DDB db;

    // Constructor
    Assembler(Context context,String mail,String passcode, String fname, String lname, String Assembler){
        super(context,mail,passcode, fname,lname, "Assembler");
        db = new DDB(context);
    }

    public List<AssemblerOrderModel> viewAllOrders(){
        List<AssemblerOrderModel> orderItems = new ArrayList<>();
        SQLiteDatabase data = db.getReadableDatabase();
        Cursor cursor = null;
        boolean sucess = false;
        try {
            // Query to retrieve all orders
            String query = "SELECT * FROM " + DDB.TABLE_ORDERS;
            cursor = data.rawQuery(query, null);
            if (cursor != null){
                while (cursor.moveToNext()){
                    int orderId = cursor.getInt(cursor.getColumnIndex(DDB.COLUMN_ID_ORDER));
                    String status = cursor.getString(cursor.getColumnIndex(DDB.COLUMN_STATE));
                    AssemblerOrderModel order = new AssemblerOrderModel(orderId,status);
                    if (!status.equals("Order Sent!")){
                        orderItems.add(order);
                    }
                }
                cursor.close();
            }
        }catch (Exception e){
            System.out.println("Error --Cannot print Orders for Assembler");
        }
        return orderItems;
    }
}
