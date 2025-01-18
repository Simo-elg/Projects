package com.example.appproject;// StockActivity.java
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.List;

public class StockActivity extends AppCompatActivity {

    private TableLayout tableLayoutStock;
    private List<Stock> stockItemList;
    private DDB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storekeeper_viewstock);

        // Initializing the table layout
        tableLayoutStock = findViewById(R.id.tableLayoutStock);

        // access to our database
        dbHelper = new DDB(this);
        stockItemList = dbHelper.getStock();

        populateTable();
    }

    private void populateTable(){
        Typeface customFont = ResourcesCompat.getFont(this, R.font.alata); // Replace 'your_font_file_name' with the actual font file name (without extension)

        for (Stock stockItem : stockItemList) {
            TableRow tableRow = new TableRow(this);

            // Create TextViews for each attribute
            TextView typeTextView = new TextView(this);
            typeTextView.setText(stockItem.getType());
            typeTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            typeTextView.setGravity(Gravity.CENTER);
            typeTextView.setTypeface(customFont); // Set the custom font

            TextView subTypeTextView = new TextView(this);
            subTypeTextView.setText(stockItem.getSubType());
            subTypeTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            subTypeTextView.setGravity(Gravity.CENTER);
            subTypeTextView.setTypeface(customFont); // Set the custom font

            TextView descriptionTextView = new TextView(this);
            descriptionTextView.setText(stockItem.getDescription());
            descriptionTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            descriptionTextView.setGravity(Gravity.CENTER);
            descriptionTextView.setTypeface(customFont); // Set the custom font

            TextView quantityTextView = new TextView(this);
            quantityTextView.setText(String.valueOf(stockItem.getQuantity()));
            quantityTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
            quantityTextView.setGravity(Gravity.CENTER);
            quantityTextView.setTypeface(customFont); // Set the custom font

            // Create ImageView for the edit icon
            ImageView editImageView = new ImageView(this);
            editImageView.setImageResource(R.drawable.image_edit); // Replace with your actual edit drawable
            TableRow.LayoutParams editParams = new TableRow.LayoutParams(48, 48);
            editParams.setMargins(8, 0, 0, 0);
            editImageView.setLayoutParams(editParams);
            editImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            // Set OnClickListener for edit action
            editImageView.setOnClickListener(v -> {
                // Navigate to Edit Activity
                int item_ID = stockItem.getId();
                String type = stockItem.getType();
                if (type.equals("Software")){
                    SoftwareComponentActivity.id_issued_by_storek = item_ID;
                    Intent intent = new Intent(StockActivity.this, SoftwareComponentActivity.class);
                    intent.putExtra("COMPONENT_ID", stockItem.getId());
                    startActivity(intent);
                } else {
                    HardwareComponentActivity.id_issued_by_storek = item_ID;
                    Intent intent = new Intent(StockActivity.this, HardwareComponentActivity.class);
                    intent.putExtra("itemId", stockItem.getId()); // Pass the item ID to the edit activity
                    startActivity(intent);
                }
            });

            // Create ImageView for the delete icon
            ImageView deleteImageView = new ImageView(this);
            deleteImageView.setImageResource(R.drawable.image_delete); //

            // Set fixed width and height for the ImageView
            TableRow.LayoutParams params = new TableRow.LayoutParams(48, 48); // Width and height in dp
            params.setMargins(8, 0, 0, 0); //
            deleteImageView.setLayoutParams(params);

            // Set scale type for the ImageView to ensure it fits
            deleteImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            // Set an OnClickListener to handle the delete action
            deleteImageView.setOnClickListener(v -> {
                // Get the current index of the row to remove
                Log.d("StockActivity", "Attempting to delete item with ID: " + stockItem.getId());
                deleteItemFromDatabase(stockItem.getId());
                stockItemList.remove(stockItem);
                tableLayoutStock.removeView(tableRow);
            });


            // Add TextViews to the TableRow
            tableRow.addView(typeTextView);
            tableRow.addView(subTypeTextView);
            tableRow.addView(descriptionTextView);
            tableRow.addView(quantityTextView);
            tableRow.addView(editImageView);
            tableRow.addView(deleteImageView);

            // Add TableRow to the TableLayout
            tableLayoutStock.addView(tableRow);
    }
}
    public void deleteItemFromDatabase(int itemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete(DDB.TABLE_STOCK, DDB.COLUMN_ID_COMPONENT + "=?", new String[]{String.valueOf(itemId)});
        db.close();
        Log.d("StockActivity", "Deleted item with ID: " + itemId + ", Rows affected: " + rowsAffected);
        if (rowsAffected == 0 ){
            Toast.makeText(StockActivity.this, "no rows affected", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view){
        Intent intent = new Intent(StockActivity.this, StorekeeperActivity.class);
        startActivity(intent);
    }
}
