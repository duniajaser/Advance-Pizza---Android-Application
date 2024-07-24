package com.example.project_1201345_12012093;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 5;
    private static final String TABLE_NAME = "users";
    private static final String COL_EMAIL = "email";
    private static final String COL_PHONE = "phone";
    private static final String COL_FIRST_NAME = "first_name";
    private static final String COL_LAST_NAME = "last_name";
    private static final String COL_GENDER = "gender";
    private static final String COL_PASSWORD = "password";
    private static final String COL_ROLE = "role";

    private static final String TABLE_FAVORITE_PIZZAS = "FavoritePizzas";
    private static final String COL_CUSTOMER_EMAIL = "CUSTOMER_EMAIL";
    private static final String COL_PIZZA_TYPE = "PIZZA_TYPE";

    private final Context context;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    public DataBaseHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_EMAIL + " TEXT PRIMARY KEY," +
                COL_PHONE + " TEXT," +
                COL_FIRST_NAME + " TEXT," +
                COL_LAST_NAME + " TEXT," +
                COL_GENDER + " TEXT," +
                COL_PASSWORD + " TEXT," +
                COL_ROLE + " TEXT" + ")";
        db.execSQL(createUsersTableQuery);

        String createFavoritePizzasTableQuery = "CREATE TABLE " + TABLE_FAVORITE_PIZZAS + " (" +
                COL_CUSTOMER_EMAIL + " TEXT, " +
                COL_PIZZA_TYPE + " TEXT, " +
                "PRIMARY KEY (" + COL_CUSTOMER_EMAIL + ", " + COL_PIZZA_TYPE + "), " +
                "FOREIGN KEY (" + COL_CUSTOMER_EMAIL + ") REFERENCES " + TABLE_NAME + "(" + COL_EMAIL + "))";
        db.execSQL(createFavoritePizzasTableQuery);

        db.execSQL("CREATE TABLE IF NOT EXISTS Orders (" +
                "OrderID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CustomerEmail TEXT, " +
                "TotalQuantity INTEGER, " +
                "OrderDateTime TEXT, " +
                "TotalPrice REAL, " +
                "IsOffer INTEGER DEFAULT 0, " +
                "FOREIGN KEY (CustomerEmail) REFERENCES " + TABLE_NAME + "(" + COL_EMAIL + "))");

        db.execSQL("CREATE TABLE IF NOT EXISTS Pizzas (" +
                "PizzaID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "OrderID INTEGER, " +
                "PizzaType TEXT, " +
                "PizzaSize TEXT, " +
                "Quantity INTEGER," +
                "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID))");

        db.execSQL("CREATE TABLE IF NOT EXISTS SpecialOffers (" +
                "OfferID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "StartingOfferDate Text, " +
                "EndingOfferDate TEXT, " +
                "TotalPrice REAL)");

        // Create SpecialOfferPizzas table
        db.execSQL("CREATE TABLE IF NOT EXISTS SpecialOfferPizzas (" +
                "SpecialOfferPizzaID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "OfferID INTEGER, " +
                "PizzaType TEXT, " +
                "PizzaSize TEXT, " +
                "Quantity INTEGER,"+
                "FOREIGN KEY (OfferID) REFERENCES SpecialOffers(OfferID))");


        insertDefaultAdminUser(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_ROLE + " TEXT");
        }
        if (oldVersion < 3) {
            String createFavoritePizzasTableQuery = "CREATE TABLE " + TABLE_FAVORITE_PIZZAS + " (" +
                    COL_CUSTOMER_EMAIL + " TEXT, " +
                    COL_PIZZA_TYPE + " TEXT, " +
                    "PRIMARY KEY (" + COL_CUSTOMER_EMAIL + ", " + COL_PIZZA_TYPE + "), " +
                    "FOREIGN KEY (" + COL_CUSTOMER_EMAIL + ") REFERENCES " + TABLE_NAME + "(" + COL_EMAIL + "))";
            db.execSQL(createFavoritePizzasTableQuery);
        }
        if (oldVersion < 4) {
            // Add Orders table
            db.execSQL("CREATE TABLE IF NOT EXISTS Orders (" +
                    "OrderID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "CustomerEmail TEXT, " +
                    "TotalQuantity INTEGER, " +
                    "OrderDateTime TEXT, " +
                    "TotalPrice REAL, " +
                    "IsOffer INTEGER DEFAULT 0, " +
                    "FOREIGN KEY (CustomerEmail) REFERENCES " + TABLE_NAME + "(" + COL_EMAIL + "))");
        }
        if (oldVersion < 5) {
            // Add Pizzas table
            db.execSQL("CREATE TABLE IF NOT EXISTS Pizzas (" +
                    "PizzaID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "OrderID INTEGER, " +
                    "PizzaType TEXT, " +
                    "PizzaSize TEXT, " +
                    "PizzaPrice REAL, " +
                    "Quantity INTEGER," +
                    "Category TEXT," +
                    "FOREIGN KEY (OrderID) REFERENCES Orders(OrderID))");
        }
    }

    private void insertDefaultAdminUser(SQLiteDatabase db) {
        ContentValues adminValues = new ContentValues();
        adminValues.put(COL_EMAIL, "admin1@gmail.com");
        adminValues.put(COL_PHONE, "0534567890");
        adminValues.put(COL_FIRST_NAME, "Admin");
        adminValues.put(COL_LAST_NAME, "User");
        adminValues.put(COL_GENDER, "Male");
        adminValues.put(COL_PASSWORD, Hash.hashPassword("admin123")); // Make sure to hash the password
        adminValues.put(COL_ROLE, "admin");

        long result = db.insert(TABLE_NAME, null, adminValues);
        if (result == -1) {
            Log.e(TAG, "Failed to insert default admin user");
        } else {
            Log.i(TAG, "Default admin user inserted successfully");
        }
    }

    public long insertUser(String email, String phone, String firstName, String lastName, String gender, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, email);
        values.put(COL_PHONE, phone);
        values.put(COL_FIRST_NAME, firstName);
        values.put(COL_LAST_NAME, lastName);
        values.put(COL_GENDER, gender);
        values.put(COL_PASSWORD, Hash.hashPassword(password));
        values.put(COL_ROLE, role);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        if (result == -1) {
            Toast.makeText(context, "Failed to register user", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User registered successfully", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COL_EMAIL + "=?",
                new String[]{email}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean checkPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL_PASSWORD},
                COL_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int passwordIndex = cursor.getColumnIndex(COL_PASSWORD);
            if (passwordIndex != -1) {
                String storedPassword = cursor.getString(passwordIndex);
                cursor.close();
                return (Hash.hashPassword(password)).equals(storedPassword);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }


    public String getRole(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String role = "";

        Cursor cursor = db.query(TABLE_NAME, new String[]{COL_ROLE},
                COL_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int roleIndex = cursor.getColumnIndex(COL_ROLE);
            if (roleIndex != -1) {
                role = cursor.getString(roleIndex);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return role;
    }

    public String getName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name = "";

        Cursor cursor = db.query(TABLE_NAME, new String[]{COL_FIRST_NAME, COL_LAST_NAME},
                COL_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int firstNameIndex = cursor.getColumnIndex(COL_FIRST_NAME);
            int lastNameIndex = cursor.getColumnIndex(COL_LAST_NAME);
            if (firstNameIndex != -1 && lastNameIndex != -1) {
                String firstName = cursor.getString(firstNameIndex);
                String lastName = cursor.getString(lastNameIndex);
                name = firstName + " " + lastName;
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return name;
    }

    public String getEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String userEmail = null;

        try {
            cursor = db.rawQuery("SELECT email FROM users WHERE email = ?", new String[]{email});
            if (cursor != null && cursor.moveToFirst()) {
                userEmail = cursor.getString(0);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving email", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return userEmail;
    }

    public long insertFavoritePizza(String customerEmail, String pizzaType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CUSTOMER_EMAIL", customerEmail);
        values.put("PIZZA_TYPE", pizzaType);

        long result = db.insert("FavoritePizzas", null, values);
        db.close();

        return result;
    }

    public long addOrder(String customerEmail, ArrayList<PizzaType> pizzas, int quantity, String orderDateTime, double totalPrice, int isOffer) {
        SQLiteDatabase db = this.getWritableDatabase();
        long orderId = -1;

        db.beginTransaction(); // Start transaction
        try {
            ContentValues orderValues = new ContentValues();
            orderValues.put("CustomerEmail", customerEmail);
            orderValues.put("TotalQuantity", quantity);
            orderValues.put("OrderDateTime", orderDateTime);
            orderValues.put("TotalPrice", totalPrice);
            orderValues.put("IsOffer", isOffer);

            Log.d(TAG, "Inserting Order: " + orderValues.toString());

            orderId = db.insert("Orders", null, orderValues);

            if (orderId != -1) {
                for (PizzaType pizza : pizzas) {
                    ContentValues pizzaValues = new ContentValues();
                    pizzaValues.put("OrderID", orderId);
                    pizzaValues.put("PizzaType", pizza.getName());
                    pizzaValues.put("PizzaSize", pizza.getSize());
                    pizzaValues.put("Quantity", pizza.getQuantity());

                    Log.d(TAG, "Inserting Pizza: " + pizzaValues.toString());

                    long result = db.insert("Pizzas", null, pizzaValues);
                    if (result == -1) {
                        Log.e(TAG, "Failed to insert pizza for order ID: " + orderId);
                    }
                }
                db.setTransactionSuccessful(); // Mark transaction as successful
            } else {
                Log.e(TAG, "Failed to insert order");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error inserting order", e);
        } finally {
            db.endTransaction(); // End transaction
            db.close();
        }

        return orderId;
    }

    public ArrayList<Order> getAllOrdersByEmail(String email) {
        ArrayList<Order> orders = new ArrayList<>();

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery("SELECT * FROM Orders WHERE CustomerEmail = ?", new String[]{email})) {
            if (cursor.moveToFirst()) {
                int orderIdIndex = cursor.getColumnIndex("OrderID");
                int customerEmailIndex = cursor.getColumnIndex("CustomerEmail");
                int totalQuantityIndex = cursor.getColumnIndex("TotalQuantity");
                int orderDateTimeIndex = cursor.getColumnIndex("OrderDateTime");
                int totalPriceIndex = cursor.getColumnIndex("TotalPrice");

                while (!cursor.isAfterLast()) {
                    if (orderIdIndex != -1 && customerEmailIndex != -1 && totalQuantityIndex != -1 &&
                            orderDateTimeIndex != -1 && totalPriceIndex != -1) {

                        int orderId = cursor.getInt(orderIdIndex);
                        String customerEmail = cursor.getString(customerEmailIndex);
                        int totalQuantity = cursor.getInt(totalQuantityIndex);
                        String orderDateTime = cursor.getString(orderDateTimeIndex);
                        double totalPrice = cursor.getDouble(totalPriceIndex);

                        ArrayList<PizzaType> pizzas = getPizzasForOrder(orderId, db);

                        Order order = new Order(customerEmail, pizzas, totalQuantity, orderDateTime, totalPrice);

                        orders.add(order);
                    }
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving orders", e);
        }

        return orders;
    }

    private ArrayList<PizzaType> getPizzasForOrder(int orderId, SQLiteDatabase db) {
        ArrayList<PizzaType> pizzas = new ArrayList<>();

        try (Cursor cursor = db.rawQuery("SELECT * FROM Pizzas WHERE OrderID = ?", new String[]{String.valueOf(orderId)})) {
            if (cursor != null && cursor.moveToFirst()) {
                int pizzaTypeIndex = cursor.getColumnIndex("PizzaType");
                int pizzaSizeIndex = cursor.getColumnIndex("PizzaSize");
                int quantityIndex = cursor.getColumnIndex("Quantity");

                do {
                    if (pizzaTypeIndex != -1 && pizzaSizeIndex != -1 && quantityIndex != -1) {
                        String pizzaType = cursor.getString(pizzaTypeIndex);
                        String pizzaSize = cursor.getString(pizzaSizeIndex);
                        int quantity = cursor.getInt(quantityIndex);

                        pizzas.add(new PizzaType(pizzaType, pizzaSize, quantity));
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving pizzas for order ID: " + orderId, e);
        }

        return pizzas;
    }

    public ArrayList<Favorite> getAllFavoritesForCustomer(String customerEmail) {
        ArrayList<Favorite> favorites = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT CUSTOMER_EMAIL, PIZZA_TYPE FROM FavoritePizzas WHERE CUSTOMER_EMAIL = ?", new String[]{customerEmail});
            if (cursor != null) {
                int pizzaTypeIndex = cursor.getColumnIndex("PIZZA_TYPE");
                int emailIndex = cursor.getColumnIndex("CUSTOMER_EMAIL");

                if (pizzaTypeIndex != -1 && emailIndex != -1) {
                    while (cursor.moveToNext()) {
                        String pizzaType = cursor.getString(pizzaTypeIndex);
                        String email = cursor.getString(emailIndex);
                        favorites.add(new Favorite(email, pizzaType));
                    }
                } else {
                    Log.e("DatabaseError", "Column index not found");
                }
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error while fetching favorites", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return favorites;
    }


    // Method to reset the database
    public void resetAllTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.beginTransaction();

            // Drop all existing tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_PIZZAS);
            db.execSQL("DROP TABLE IF EXISTS Orders");
            db.execSQL("DROP TABLE IF EXISTS Pizzas");
            db.execSQL("DROP TABLE IF EXISTS SpecialOffers");
            db.execSQL("DROP TABLE IF EXISTS SpecialOfferPizzas");

            // Recreate all tables
            onCreate(db);

            db.setTransactionSuccessful();
            Log.i(TAG, "All tables reset successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error resetting tables", e);
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public boolean removeFavorite(String userEmail, String pizzaType) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete("FavoritePizzas", "CUSTOMER_EMAIL = ? AND PIZZA_TYPE = ?", new String[]{userEmail, pizzaType});
        db.close();
        return rowsAffected > 0;
    }

    public Cursor getAllCustomers() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM users WHERE role = 0", null);
    }

    public void updateCustomer(Customer user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", user.getFirstName());
        contentValues.put("last_name", user.getLastName());
        contentValues.put("phone", user.getPhone());
        contentValues.put("password", user.getHashedPassword());
        contentValues.put("gender", user.getGender());

        // Define the selection criteria (where clause)
        String selection = "email = ? AND role = ?";
        String[] selectionArgs = { user.getEmail(), "0" };

        // Perform the update on the database table
        sqLiteDatabase.update("users", contentValues, selection, selectionArgs);
    }

    public int updateClientPassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        // Define the criteria for selecting the specific row
        String selection = "email = ? AND role = ?";
        String[] selectionArgs = { email, "0" }; // Assuming "0" is the role for clients

        // Perform the update on the database table
        int count = db.update("users", values, selection, selectionArgs);
        db.close();
        return count;
    }



    public ArrayList<SpecialOffer> getAllOffers() {
        ArrayList<SpecialOffer> specialOffers = new ArrayList<>();
        String selectOffersQuery = "SELECT * FROM SpecialOffers";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor offersCursor = null;
        Cursor pizzasCursor = null;

        try {
            offersCursor = db.rawQuery(selectOffersQuery, null);
            if (offersCursor.moveToFirst()) {
                int offerIdIndex = offersCursor.getColumnIndex("OfferID");
                int startIndex = offersCursor.getColumnIndex("StartingOfferDate");
                int endIndex = offersCursor.getColumnIndex("EndingOfferDate");
                int priceIndex = offersCursor.getColumnIndex("TotalPrice");

                do {
                    SpecialOffer specialOffer = new SpecialOffer();
                    int offerId = offersCursor.getInt(offerIdIndex);
                    specialOffer.setStartingOfferDate(offersCursor.getString(startIndex));
                    specialOffer.setEndingOfferDate(offersCursor.getString(endIndex));
                    specialOffer.setTotalPrice(offersCursor.getDouble(priceIndex));

                    // Fetch associated pizzas
                    String selectPizzasQuery = "SELECT * FROM SpecialOfferPizzas WHERE OfferID = ?";
                    pizzasCursor = db.rawQuery(selectPizzasQuery, new String[]{String.valueOf(offerId)});

                    ArrayList<PizzaType> pizzas = new ArrayList<>();
                    if (pizzasCursor.moveToFirst()) {
                        int pizzaTypeIndex = pizzasCursor.getColumnIndex("PizzaType");
                        int pizzaSizeIndex = pizzasCursor.getColumnIndex("PizzaSize");
                        int pizzaQuantityIndex = pizzasCursor.getColumnIndex("Quantity");
                        do {
                            String pizzaType = pizzasCursor.getString(pizzaTypeIndex);
                            String pizzaSize = pizzasCursor.getString(pizzaSizeIndex);
                            int pizzaQuantity = pizzasCursor.getInt(pizzaQuantityIndex);
                            pizzas.add(new PizzaType(pizzaType, pizzaSize, 0, pizzaQuantity)); // Assuming price is not relevant here
                            // because we have a constant price for the sizes: S, M, L
                        } while (pizzasCursor.moveToNext());
                    }
                    pizzasCursor.close();
                    pizzasCursor = null;  // Ensure the cursor is not double closed.

                    specialOffer.setPizzas(pizzas);
                    specialOffers.add(specialOffer);
                } while (offersCursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DataBaseHelper", "Error while getting all offers", e);
        } finally {
            if (offersCursor != null) {
                offersCursor.close();
            }
            if (pizzasCursor != null) {
                pizzasCursor.close();
            }
            db.close();
        }

        return specialOffers;
    }


    public AdminClass getAdminByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        AdminClass admin = null;

        Cursor cursor = db.query(TABLE_NAME, null, COL_EMAIL + "=? AND " + COL_ROLE + "=?",
                new String[]{email, "admin"}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int phoneIndex = cursor.getColumnIndex(COL_PHONE);
            int firstNameIndex = cursor.getColumnIndex(COL_FIRST_NAME);
            int lastNameIndex = cursor.getColumnIndex(COL_LAST_NAME);
            int genderIndex = cursor.getColumnIndex(COL_GENDER);
            int passwordIndex = cursor.getColumnIndex(COL_PASSWORD);
            int roleIndex = cursor.getColumnIndex(COL_ROLE);

            if (phoneIndex >= 0 && firstNameIndex >= 0 && lastNameIndex >= 0 &&
                    genderIndex >= 0 && passwordIndex >= 0 && roleIndex >= 0) {

                String phone = cursor.getString(phoneIndex);
                String firstName = cursor.getString(firstNameIndex);
                String lastName = cursor.getString(lastNameIndex);
                String gender = cursor.getString(genderIndex);
                String password = cursor.getString(passwordIndex);
                String role = cursor.getString(roleIndex);

                admin = new AdminClass(email, phone, firstName, lastName, gender, password, role);
            }

            cursor.close();
        }
        db.close();
        return admin;
    }

    public boolean updateAdmin(AdminClass admin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_PHONE, admin.getPhone());
        values.put(COL_FIRST_NAME, admin.getFirstName());
        values.put(COL_LAST_NAME, admin.getLastName());
        values.put(COL_GENDER, admin.getGender());
        values.put(COL_PASSWORD, admin.getHashedPassword());
        values.put(COL_ROLE, admin.getRole());

        int rowsAffected = db.update(TABLE_NAME, values, COL_EMAIL + "=?", new String[]{admin.getEmail()});

        db.close();
        return rowsAffected > 0;
    }


    public long insertAdmin(AdminClass admin) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, admin.getEmail());
        values.put(COL_PHONE, admin.getPhone());
        values.put(COL_FIRST_NAME, admin.getFirstName());
        values.put(COL_LAST_NAME, admin.getLastName());
        values.put(COL_GENDER, admin.getGender());
        values.put(COL_PASSWORD, admin.getHashedPassword());
        values.put(COL_ROLE, "admin");

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        if (result == -1) {
            Toast.makeText(context, "Failed to register admin", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Admin registered successfully", Toast.LENGTH_SHORT).show();
        }

        return result;
    }



    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery("SELECT * FROM Orders", null)) {
            if (cursor.moveToFirst()) {
                int orderIdIndex = cursor.getColumnIndex("OrderID");
                int customerEmailIndex = cursor.getColumnIndex("CustomerEmail");
                int totalQuantityIndex = cursor.getColumnIndex("TotalQuantity");
                int orderDateTimeIndex = cursor.getColumnIndex("OrderDateTime");
                int totalPriceIndex = cursor.getColumnIndex("TotalPrice");

                while (!cursor.isAfterLast()) {
                    if (orderIdIndex != -1 && customerEmailIndex != -1 && totalQuantityIndex != -1 &&
                            orderDateTimeIndex != -1 && totalPriceIndex != -1) {

                        int orderId = cursor.getInt(orderIdIndex);
                        String customerEmail = cursor.getString(customerEmailIndex);
                        int totalQuantity = cursor.getInt(totalQuantityIndex);
                        String orderDateTime = cursor.getString(orderDateTimeIndex);
                        double totalPrice = cursor.getDouble(totalPriceIndex);

                        ArrayList<PizzaType> pizzas = getPizzasForOrder(orderId, db);

                        Order order = new Order(customerEmail, pizzas, totalQuantity, orderDateTime, totalPrice);

                        orders.add(order);
                    }
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving orders", e);
        }

        return orders;
    }


    public long addSpecialOffer(SpecialOffer specialOffer) {
        SQLiteDatabase db = this.getWritableDatabase();
        long offerId = -1;

        db.beginTransaction(); // Start transaction
        try {
            // Insert into SpecialOffers table
            ContentValues offerValues = new ContentValues();
            offerValues.put("StartingOfferDate", specialOffer.getStartingOfferDate());
            offerValues.put("EndingOfferDate", specialOffer.getEndingOfferDate());
            offerValues.put("TotalPrice", specialOffer.getTotalPrice());

            offerId = db.insert("SpecialOffers", null, offerValues);

            if (offerId != -1) {
                // Insert into SpecialOfferPizzas table
                ArrayList<PizzaType> pizzas = specialOffer.getPizzas();
                for (PizzaType pizza : pizzas) {
                    ContentValues pizzaValues = new ContentValues();
                    pizzaValues.put("OfferID", offerId);
                    pizzaValues.put("PizzaType", pizza.getName());
                    pizzaValues.put("PizzaSize", pizza.getSize());
                    pizzaValues.put("Quantity", pizza.getQuantity());

                    db.insert("SpecialOfferPizzas", null, pizzaValues);
                }
                db.setTransactionSuccessful(); // Mark transaction as successful
            } else {
                Log.e(TAG, "Failed to insert special offer");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error inserting special offer", e);
        } finally {
            db.endTransaction(); // End transaction
            db.close();
        }

        return offerId;
    }







}
