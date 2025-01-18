package com.example.appproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DDB extends SQLiteOpenHelper {
    static boolean res_stock_only = false;

    // Database name and version
    private static final String DATABASE_NAME = "inventoryManager";
    private static final int DATABASE_VERSION = 7;

    // Tables
    public static final String TABLE_STOCK = "Stock";
    public static final String TABLE_STAFF = "Staff";
    public static final String TABLE_ORDERS = "Orders";
    public static final String TABLE_CLIENTS = "Clients";

    // Common column
    public static final String COLUMN_ID_COMPONENT = "ID_component";

    // Stock columns
    public static final String COLUMN_TYPE_COMPONENT = "type_component";
    public static final String COLUMN_SOUS_TYPE_COMPONENT = "sous_type";
    public static final String COLUMN_QUANTITE_COMPONENT = "nombre";
    public static final String COLUMN_DESCRIPTION_COMPONENT = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_VOLTAGE = "voltage";
    public static final String COLUMN_WATTAGE = "wattage";
    public static final String COLUMN_DIMENSION = "dimension";
    public static final String COLUMN_VERSION = "version";
    public static final String COLUMN_LICENCE = "licence";
    public static final String COLUMN_COMPATIBILITY = "compatibility";

    // Staff columns
    public static final String COLUMN_ID_USERS = "ID_users";
    public static final String COLUMN_TYPE_USER = "type";
    public static final String COLUMN_FIRSTNAME = "prenom";
    public static final String COLUMN_LASTNAME = "nom";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "mdp";
    public static final String COLUMN_CREATION_DATE = "creationDateTime";
    public static final String COLUMN_MODIFICATION_DATE = "modificationDateTime";

    // Orders columns
    public static final String COLUMN_ID_ORDER = "ID_order";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_COMPONENTS = "components";
    public static final String COLUMN_DATE_OF_CRETAION = "date_de_creation";
    public static final String COLUMN_DATE_OF_MODIFICATION = "date_de_modif";


    // Clients columns
    public static final String COLUMN_FIRSTNAME_CLIENT = "prenom";
    public static final String COLUMN_ID_CLIENT = "ID_client";
    public static final String COLUMN_LASTNAME_CLIENT = "nom";
    public static final String COLUMN_PASSWORD_CLIENT = "mdp";
    public static final String COLUMN_EMAIL_CLIENT = "email";
    public static final String COLUMN_CREATION_DATE_CLIENT = "creationDateTime";
    public static final String COLUMN_MODIFICATION_DATE_CLIENT = "modificationDateTime";
    public static final String COLUMN_ORDERS_CLIENT = "commandes";
    public static final String COLUMN_CART = "panier";

    public DDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Stock table
        String CREATE_STOCK_TABLE = "CREATE TABLE " + TABLE_STOCK + "("
                + COLUMN_ID_COMPONENT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TYPE_COMPONENT + " TEXT,"
                + COLUMN_SOUS_TYPE_COMPONENT + " TEXT, "
                + COLUMN_QUANTITE_COMPONENT + " INTEGER,"
                + COLUMN_DESCRIPTION_COMPONENT + " TEXT, "
                + COLUMN_PRICE + " TEXT,"
                + COLUMN_BRAND + " TEXT,"
                + COLUMN_VOLTAGE + " TEXT,"
                + COLUMN_WATTAGE + " TEXT,"
                + COLUMN_DIMENSION + " TEXT,"
                + COLUMN_VERSION + " TEXT,"
                + COLUMN_LICENCE + " TEXT,"
                + COLUMN_COMPATIBILITY + " TEXT" + ")";

        // Staff table
        String CREATE_STAFF_TABLE = "CREATE TABLE " + TABLE_STAFF + "("
                + COLUMN_ID_USERS + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TYPE_USER + " TEXT,"
                + COLUMN_FIRSTNAME + " TEXT,"
                + COLUMN_LASTNAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT UNIQUE,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_CREATION_DATE + " TEXT,"
                + COLUMN_MODIFICATION_DATE + " TEXT" + ")";

        // Orders table
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID_ORDER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ID_CLIENT + " INTEGER,"
                + COLUMN_STATE + " TEXT,"
                + COLUMN_MESSAGE + " TEXT,"
                + COLUMN_DATE_OF_CRETAION + " TEXT, "
                + COLUMN_DATE_OF_MODIFICATION + " TEXT, "
                + COLUMN_COMPONENTS + " TEXT" + ")";

        // Clients table
        String CREATE_CLIENTS_TABLE = "CREATE TABLE " + TABLE_CLIENTS + "("
                + COLUMN_ID_CLIENT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIRSTNAME_CLIENT + " TEXT,"
                + COLUMN_LASTNAME_CLIENT + " TEXT,"
                + COLUMN_PASSWORD_CLIENT + " TEXT,"
                + COLUMN_EMAIL_CLIENT + " TEXT UNIQUE,"
                + COLUMN_CREATION_DATE_CLIENT + " TEXT,"
                + COLUMN_MODIFICATION_DATE_CLIENT + " TEXT,"
                + COLUMN_ORDERS_CLIENT + " TEXT UNIQUE,"
                + COLUMN_CART + " TEXT" + ")";

        // Execute table creation
        db.execSQL(CREATE_STOCK_TABLE);
        db.execSQL(CREATE_STAFF_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_CLIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 6) {
            db.execSQL("ALTER TABLE " + TABLE_STOCK + " ADD COLUMN " + COLUMN_DATE_OF_CRETAION + " TEXT;");
            db.execSQL("ALTER TABLE " + TABLE_STOCK + " ADD COLUMN " + COLUMN_DATE_OF_MODIFICATION + " TEXT;");
            db.execSQL("ALTER TABLE " + TABLE_STOCK + " ADD COLUMN " + COLUMN_QUANTITE_COMPONENT + " TEXT UNIQUE;");
        }
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        onCreate(db);
    }

    public long addClient(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRSTNAME_CLIENT, user.getFirstname());
        contentValues.put(COLUMN_LASTNAME_CLIENT, user.getLastname());
        contentValues.put(COLUMN_EMAIL_CLIENT, user.getEmail());
        contentValues.put(COLUMN_PASSWORD_CLIENT, user.getPassword());
        contentValues.put(COLUMN_CREATION_DATE_CLIENT, user.getCreationDate());
        long result = db.insert(TABLE_CLIENTS, null, contentValues);
        db.close();
        return result;
    }

    public boolean addHComponent(HardwareComponent component) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TYPE_COMPONENT, component.getType());
        contentValues.put(COLUMN_DESCRIPTION_COMPONENT, component.getDescription());
        contentValues.put(COLUMN_PRICE, component.getPrice());
        contentValues.put(COLUMN_VOLTAGE, component.getVoltage());
        contentValues.put(COLUMN_WATTAGE, component.getWattage());
        contentValues.put(COLUMN_DIMENSION, component.getDimensions());
        contentValues.put(COLUMN_QUANTITE_COMPONENT, component.getQuantite());
        contentValues.put(COLUMN_SOUS_TYPE_COMPONENT, component.getSousType());

        long result = db.insert(TABLE_STOCK, null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean addSComponent(SoftwareComponent component){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TYPE_COMPONENT, component.getType());
        contentValues.put(COLUMN_SOUS_TYPE_COMPONENT, component.getSousType());
        contentValues.put(COLUMN_DESCRIPTION_COMPONENT, component.getDescription());
        contentValues.put(COLUMN_QUANTITE_COMPONENT, component.getQuantite());
        contentValues.put(COLUMN_VERSION, component.getVersion());
        contentValues.put(COLUMN_LICENCE, component.getLicense());
        contentValues.put(COLUMN_COMPATIBILITY, component.getCompatibility());
        contentValues.put(COLUMN_PRICE, component.getPrice());

        long result = db.insert(TABLE_STOCK, null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean addProductToCart(String clientId, String productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (clientId == null || clientId.isEmpty() || productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Client ID and Product ID cannot be null or empty");
        }

        String query = "SELECT " + COLUMN_CART + " FROM " + TABLE_CLIENTS + " WHERE " + COLUMN_ID_CLIENT + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{clientId});

        String newCart = "";
        long res = 0;
        if (cursor.moveToFirst()) {
            String currentCart = cursor.getString(cursor.getColumnIndex(COLUMN_CART));

            // Add the product with its quantity to the cart
            String productWithQuantity = productId + ":" + quantity;
            if (currentCart != null && !currentCart.isEmpty()) {
                newCart = currentCart + "," + productWithQuantity;
            } else {
                newCart = productWithQuantity;
            }

            ContentValues values = new ContentValues();
            values.put(COLUMN_CART, newCart);
            res = db.update(TABLE_CLIENTS, values, COLUMN_ID_CLIENT + " = ?", new String[]{clientId});
        }
        cursor.close();
        return res != -1;
    }

    public int updateSoftwareComponent(int id_new, String soustype, String description, int quantity, String version, String license, String Compatibilite, String prix){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();

        val.put(COLUMN_SOUS_TYPE_COMPONENT, soustype);
        val.put(COLUMN_DESCRIPTION_COMPONENT, description);
        val.put(COLUMN_QUANTITE_COMPONENT, quantity);
        val.put(COLUMN_VERSION, version);
        val.put(COLUMN_LICENCE, license);
        val.put(COLUMN_COMPATIBILITY, Compatibilite);
        val.put(COLUMN_PRICE, prix);

        // Update the row in the database
        int rowsAffected = db.update(DDB.TABLE_STOCK, val, DDB.COLUMN_ID_COMPONENT + " = ?", new String[]{String.valueOf(id_new)});

        db.close(); // Close the database after the operation
        Log.d("UpdateDatabase", "Rows affected: " + rowsAffected);
        return rowsAffected;
    }

    public boolean updateOrderStatebyId(int id_ord,String new_state,String message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();

        val.put(COLUMN_STATE,new_state);
        val.put(COLUMN_MESSAGE,message);

        int rowsAffected = db.update(DDB.TABLE_ORDERS,val,DDB.COLUMN_ID_ORDER + " = ?", new String[]{String.valueOf(id_ord)});
        db.close();

        return rowsAffected > 0;
    }

    public int updateHardwareComponent(int id_n, String soustype, String description, int quantity, String voltage, String wattage, String dimensions, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(COLUMN_SOUS_TYPE_COMPONENT, soustype);
        val.put(COLUMN_DESCRIPTION_COMPONENT, description);
        val.put(COLUMN_QUANTITE_COMPONENT, quantity);
        val.put(COLUMN_VOLTAGE, voltage);
        val.put(COLUMN_WATTAGE, wattage);
        val.put(COLUMN_DIMENSION, dimensions);
        val.put(COLUMN_PRICE, price);

        // Update the row in the database
        int rowsAffected = db.update(DDB.TABLE_STOCK, val, DDB.COLUMN_ID_COMPONENT + " = ?", new String[]{String.valueOf(id_n)});

        db.close(); // Close the database after the operation
        Log.d("UpdateDatabase", "Rows affected: " + rowsAffected);
        return rowsAffected;
    }



    public void updateRequester(int id, String firstname, String lastname, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DDB.COLUMN_FIRSTNAME_CLIENT, firstname);
        values.put(DDB.COLUMN_LASTNAME_CLIENT, lastname);
        values.put(DDB.COLUMN_EMAIL_CLIENT, email);
        values.put(DDB.COLUMN_PASSWORD_CLIENT, password);
        values.put(DDB.COLUMN_MODIFICATION_DATE_CLIENT, "2024-10-19"); // Date de modification
        db.update(DDB.TABLE_CLIENTS, values, DDB.COLUMN_ID_CLIENT + " = ?", new String[]{String.valueOf(id)});
    }


    public String requesterExists(String email, String password) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_CLIENTS + " WHERE " + COLUMN_EMAIL_CLIENT + " = ? AND " + COLUMN_PASSWORD_CLIENT + " = ?";
            cursor = db.rawQuery(query, new String[]{email, password});

            // Si le curseur contient des résultats, l'utilisateur existe
            if (cursor.moveToFirst()){
                @SuppressLint("Range") String clientId = cursor.getString(cursor.getColumnIndex(COLUMN_ID_CLIENT));
                return clientId;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log l'exception pour le débogage
            return null; // Si une exception se produit, retourne false
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }


    public int deleteClient(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CLIENTS, COLUMN_ID_CLIENT + " = ?", new String[]{id});
    }

    public boolean deleteComponent(String clientId, String compId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            // Récupère la liste des composants du panier du client
            String query = "SELECT " + COLUMN_CART + " FROM " + TABLE_CLIENTS + " WHERE " + COLUMN_ID_CLIENT + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{clientId});

            String updatedCart = null;
            if (cursor.moveToFirst()) {
                String cart = cursor.getString(cursor.getColumnIndex(COLUMN_CART));
                if (cart != null && !cart.isEmpty()) {
                    // Supprime l'ID du composant (compId) de la liste
                    List<String> components = new ArrayList<>(Arrays.asList(cart.split(",")));

                    if (components.contains(compId)) {
                        components.remove(compId); // Supprimer l'élément du panier
                        updatedCart = String.join(",", components);
                    }
                }
            }
            cursor.close();

            if (updatedCart == null) {
                return false; // Aucun composant trouvé ou la suppression a échoué
            }

            // Met à jour la colonne COLUMN_CART avec la nouvelle liste de composants
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_CART, updatedCart);
            int rows = db.update(TABLE_CLIENTS, contentValues, COLUMN_ID_CLIENT + " = ?", new String[]{clientId});

            if (rows > 0) {
                db.setTransactionSuccessful();
                return true;
            } else {
                return false;
            }
        } finally {
            db.endTransaction();
        }
    }

    public boolean isStaffTypeExists(String userType) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STAFF + " WHERE " + COLUMN_TYPE_USER + " = ?", new String[]{userType});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public Cursor getClientData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CLIENTS, null);
        return res;
    }

    public Cursor getClientData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                TABLE_CLIENTS + "." + COLUMN_ID_CLIENT + " AS client_id, " +
                TABLE_CLIENTS + "." + COLUMN_FIRSTNAME_CLIENT + " || ' ' || " + TABLE_CLIENTS + "." + COLUMN_LASTNAME_CLIENT + " AS full_name, " +
                "COUNT(" + TABLE_ORDERS + "." + COLUMN_ID_ORDER + ") AS order_count, " +
                TABLE_CLIENTS + "." + COLUMN_EMAIL_CLIENT + " AS email " +
                "FROM " + TABLE_CLIENTS +
                " LEFT JOIN " + TABLE_ORDERS +
                " ON " + TABLE_CLIENTS + "." + COLUMN_ID_CLIENT + " = " + TABLE_ORDERS + "." + COLUMN_ID_CLIENT +
                " WHERE " + TABLE_CLIENTS + "." + COLUMN_ID_CLIENT + " = ?" +
                " GROUP BY " + TABLE_CLIENTS + "." + COLUMN_ID_CLIENT;

        return db.rawQuery(query, new String[]{id});
    }

    public String getClientIdByOrderId(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        String clientId = null;

        String query = "SELECT " + COLUMN_ID_CLIENT + " FROM " + TABLE_ORDERS + " WHERE " + COLUMN_ID_ORDER + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id});

        if (cursor.moveToFirst()){
            clientId = cursor.getString(cursor.getColumnIndex(COLUMN_ID_CLIENT));
        }

        cursor.close();
        db.close();

        return clientId != null ? clientId : "Unknown Client";
    }

    public List<Pair<String, Integer>> getOrderComponentsWithStock(int orderId) {
        List<Pair<String, Integer>> componentsWithStock = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        try {
            // Étape 1 : Récupérer les IDs des composants pour la commande donnée
            String queryOrder = "SELECT " + COLUMN_COMPONENTS + " FROM " + TABLE_ORDERS +
                    " WHERE " + COLUMN_ID_ORDER + " = ?";
            Cursor orderCursor = database.rawQuery(queryOrder, new String[]{String.valueOf(orderId)});

            if (orderCursor.moveToFirst()) {
                String components = orderCursor.getString(0); // Les ID des composants sous forme de chaîne
                String[] componentIds = components.split(","); // Séparer les ID par des virgules

                // Étape 2 : Pour chaque ID de composant, récupérer les informations de stock
                for (String componentId : componentIds) {
                    String queryStock = "SELECT " + COLUMN_DESCRIPTION_COMPONENT + ", " + COLUMN_QUANTITE_COMPONENT +
                            " FROM " + TABLE_STOCK +
                            " WHERE " + COLUMN_ID_COMPONENT + " = ?";
                    Cursor stockCursor = database.rawQuery(queryStock, new String[]{componentId.trim()});

                    if (stockCursor.moveToFirst()) {
                        String description = stockCursor.getString(0); // Description du composant
                        int quantity = stockCursor.getInt(1); // Quantité en stock
                        componentsWithStock.add(new Pair<>(description, quantity));
                    }
                    stockCursor.close();
                }
            }
            orderCursor.close();
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération des composants : " + e.getMessage());
        }

        return componentsWithStock;
    }


    public void deleteAllRequesters(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + DDB.TABLE_CLIENTS);  //
        db.close();
    }
    public void deleteAllStockItems(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + DDB.TABLE_STOCK);  // Deletes all rows in TABLE_STOCK
        db.close();
    }

    public List<Products> getProducts(){
        List<Products> productsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_STOCK;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do{
                int idI = cursor.getColumnIndex(COLUMN_ID_COMPONENT);
                int priceI = cursor.getColumnIndex(COLUMN_PRICE);
                int ptypeI = cursor.getColumnIndex(COLUMN_TYPE_COMPONENT);
                int pdescriptionI = cursor.getColumnIndex(COLUMN_DESCRIPTION_COMPONENT);
                int pnameI = cursor.getColumnIndex(COLUMN_SOUS_TYPE_COMPONENT);

                if (idI != -1 && priceI != -1 && ptypeI != -1 && pdescriptionI != -1 && pnameI != -1){
                    String id = cursor.getString(idI);
                    String price = cursor.getString(priceI);
                    String ptype = cursor.getString(ptypeI);
                    String pname = cursor.getString(pnameI);

                    Products product = new Products(pname, price, ptype, id);
                    productsList.add(product);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return productsList;
    }

    public String getCartIdsForClient(String clientId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_CART + " FROM " + TABLE_CLIENTS + " WHERE " + COLUMN_ID_CLIENT + " = ?", new String[]{clientId});

        String cartIds = "";
        if (cursor != null && cursor.moveToFirst()) {
            cartIds = cursor.getString(cursor.getColumnIndex(COLUMN_CART));  // Get the cart IDs
        }

        if (cursor != null) {
            cursor.close();
        }

        // Log the retrieved cart IDs
        Log.d("DDB", "Retrieved Cart IDs for client " + clientId + ": " + cartIds);

        return cartIds;
    }

    public List<CartModel> getCartItemsForClient(String clientId) {
        List<CartModel> cartItems = new ArrayList<>();

        // Get the product IDs from the client's cart
        String cartIds = getCartIdsForClient(clientId);

        // Check if cartIds is not null and not empty before proceeding
        if (cartIds != null && !cartIds.isEmpty()) {
            String[] productIds = cartIds.split(",");  // Split the comma-separated product IDs

            SQLiteDatabase db = this.getReadableDatabase();

            // Loop through the product IDs and fetch details for each one
            for (String productId : productIds) {
                Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STOCK + " WHERE " + COLUMN_ID_COMPONENT + " = ?", new String[]{productId});

                if (cursor != null && cursor.moveToFirst()) {
                    CartModel cartItem = new CartModel(
                            cursor.getString(cursor.getColumnIndex(COLUMN_ID_COMPONENT)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_COMPONENT)),  // Product name
                            cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_COMPONENT)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_BRAND)),  // Assuming this is the image or other component data
                            cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITE_COMPONENT)),
                            cursor.getString(cursor.getColumnIndex(COLUMN_SOUS_TYPE_COMPONENT))

                    );
                    cartItems.add(cartItem);
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else {
            Log.e("DDB", "Cart IDs are null or empty for client: " + clientId);
        }

        return cartItems;
    }

    public List<OrderModel> getOrdersByIds(List<String> orderIds) { //new
        List<OrderModel> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String placeholders = TextUtils.join(",", Collections.nCopies(orderIds.size(), "?"));
        String query = "SELECT * FROM " + TABLE_ORDERS + " WHERE " + COLUMN_ID_ORDER + " IN (" + placeholders + ")";

        Cursor cursor = db.rawQuery(query, orderIds.toArray(new String[0]));

        if (cursor != null) {
            while (cursor.moveToNext()) {
                OrderModel order = new OrderModel(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID_ORDER)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID_CLIENT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_STATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_COMPONENTS))


                        // Add other fields as needed
                );
                orders.add(order);
            }
            cursor.close();
        }
        return orders;
    }

    public List<String> getOrderIdsForClient(String clientId) { // new
        List<String> orderIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ORDERS_CLIENT + " FROM " + TABLE_CLIENTS + " WHERE " + COLUMN_ID_CLIENT + " = ?", new String[]{clientId});

        if (cursor != null && cursor.moveToFirst()) {
            String orders = cursor.getString(cursor.getColumnIndex(COLUMN_ORDERS_CLIENT));
            if (orders != null && !orders.isEmpty()) {
                orderIds.addAll(Arrays.asList(orders.split(","))); // Split comma-separated Order IDs
            }
            cursor.close();
        }
        return orderIds;
    }

    public boolean removeOrderFromClient(String clientId, String orderIdToRemove) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Query the TABLE_CLIENTS to get the current orders list
        String query = "SELECT " + DDB.COLUMN_ORDERS_CLIENT + " FROM " + DDB.TABLE_CLIENTS + " WHERE " + DDB.COLUMN_ID_CLIENT + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{clientId});

        String updatedOrders = null;
        if (cursor != null && cursor.moveToFirst()) {
            // Get the existing orders
            String existingOrders = cursor.getString(cursor.getColumnIndex(DDB.COLUMN_ORDERS_CLIENT));
            if (existingOrders != null && !existingOrders.isEmpty()) {
                // Remove the orderId from the list of orders
                updatedOrders = removeOrderIdFromList(existingOrders, orderIdToRemove);
            }
            cursor.close();
        }

        boolean orderDeleted = false;
        if (updatedOrders != null) {
            // Now update the COLUMN_ORDERS_CLIENT for this client
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_ORDERS_CLIENT, updatedOrders);
            int rowsUpdated = db.update(TABLE_CLIENTS, contentValues, COLUMN_ID_CLIENT + " = ?", new String[]{clientId});

            if (rowsUpdated > 0) {
                // If the client record was updated, now delete the order from the TABLE_ORDERS
                orderDeleted = deleteOrder(orderIdToRemove);
            }
        }

        return orderDeleted;
    }

    public boolean deleteOrder(String orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ORDERS, COLUMN_ID_ORDER + " = ?", new String[]{orderId});
        db.close();
        return result > 0; // Return true if the order was deleted successfully
    }
    /**
     * Helper method to remove the order ID from the comma-separated list.
     */
    private String removeOrderIdFromList(String orders, String orderIdToRemove) {
        // Split the string into an array of order IDs
        List<String> orderList = new ArrayList<>(Arrays.asList(orders.split(",")));

        // Remove the specific order ID
        orderList.remove(orderIdToRemove);

        // Join the remaining order IDs back into a comma-separated string
        return TextUtils.join(",", orderList);
    }


    public List<Stock> getStock() {
        List<Stock> stockItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Requête pour sélectionner tous les éléments du stock
        String selectQuery = "SELECT * FROM " + TABLE_STOCK;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int identification = cursor.getColumnIndex(COLUMN_ID_COMPONENT);

                int typeIndex = cursor.getColumnIndex(COLUMN_TYPE_COMPONENT);
                int subTypeIndex = cursor.getColumnIndex(COLUMN_SOUS_TYPE_COMPONENT);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION_COMPONENT);
                int quantityIndex = cursor.getColumnIndex(COLUMN_QUANTITE_COMPONENT);
                int priceIndex = cursor.getColumnIndex(COLUMN_PRICE);

                if (identification != -1 && typeIndex != -1 && subTypeIndex != -1 && descriptionIndex != -1 && quantityIndex != -1) {
                    String identi = cursor.getString(identification);
                    String type = cursor.getString(typeIndex);
                    String subType = cursor.getString(subTypeIndex);
                    String description = cursor.getString(descriptionIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    String price = cursor.getString(priceIndex);

                    Stock stockItem = new Stock(Integer.parseInt(identi),type, subType, description, quantity, price);
                    stockItemList.add(stockItem);
                } else {
                    // Log an error or handle the missing column case
                }
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return stockItemList;
    }

    public void loadDataFromCsvFiles(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            // Load Requesters from CSV

            InputStream isRequesters = context.getAssets().open("requesters.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(isRequesters));
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 7) { // Adjust this to match the exact number of expected columns
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_ID_CLIENT, Integer.parseInt(columns[0]));
                    values.put(COLUMN_FIRSTNAME_CLIENT, columns[1]);
                    values.put(COLUMN_LASTNAME_CLIENT, columns[2]);
                    values.put(COLUMN_EMAIL_CLIENT, columns[3]);
                    values.put(COLUMN_PASSWORD_CLIENT, columns[4]);
                    values.put(COLUMN_CREATION_DATE_CLIENT, columns[5]);
                    values.put(COLUMN_MODIFICATION_DATE_CLIENT, columns[6]);
                    if (!res_stock_only){
                        db.insert(TABLE_CLIENTS, null, values);
                    }
                } else {
                    System.err.println("Invalid row format in requesters.csv: " + line);
                }
            }
            res_stock_only = false;
            reader.close();

            // Load Stock Items from CSV
            InputStream isStock = context.getAssets().open("stock_items.csv");
            reader = new BufferedReader(new InputStreamReader(isStock));
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length == 9) { // Adjust this to match the exact number of expected columns
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_ID_COMPONENT, Integer.parseInt(columns[0]));
                    values.put(COLUMN_TYPE_COMPONENT, columns[1]);
                    values.put(COLUMN_SOUS_TYPE_COMPONENT, columns[2]);
                    values.put(COLUMN_DESCRIPTION_COMPONENT, columns[3]);
                    values.put(COLUMN_QUANTITE_COMPONENT, Integer.parseInt(columns[4]));
                    values.put(COLUMN_VOLTAGE, columns[5]);
                    values.put(COLUMN_WATTAGE, columns[6]);
                    values.put(COLUMN_DIMENSION, columns[7]);
                    values.put(COLUMN_PRICE, columns[8]);
                    db.insert(TABLE_STOCK, null, values);
                } else {
                    System.err.println("Invalid row format in stock_items.csv: " + line);
                }
            }
            reader.close();

            db.setTransactionSuccessful(); // Commit transaction if all goes well
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction(); // End transaction
        }
    }
}
