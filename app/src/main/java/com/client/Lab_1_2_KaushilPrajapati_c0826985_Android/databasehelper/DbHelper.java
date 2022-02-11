package com.client.Lab_1_2_KaushilPrajapati_c0826985_Android.databasehelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.client.Lab_1_2_KaushilPrajapati_c0826985_Android.AddProductModel;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "products_db";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        // create categories table
        db.execSQL(AddProductModel.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + AddProductModel.TABLE_NAME);

        // Create tables again
        onCreate(db);

    }



    public long insertCategory(String productName,String productDescription,String productPrice,String productLat,String productlong) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(AddProductModel.COLUMN_PRODUCT_NAME, productName);
        values.put(AddProductModel.COLUMN_PRODUCT_DESCRIPTION, productDescription);
        values.put(AddProductModel.COLUMN_PRODUCT_PRICE, productPrice);
        values.put(AddProductModel.COLUMN_PRODUCT_LAT, productLat);
        values.put(AddProductModel.COLUMN_PRODUCT_LONG, productlong);

        // insert row
        long id = db.insert(AddProductModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }


    @SuppressLint("Range")
    public List<AddProductModel> getAllCategories() {
        List<AddProductModel> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + AddProductModel.TABLE_NAME + " ORDER BY " +
                AddProductModel.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AddProductModel note = new AddProductModel();
                note.setId(cursor.getInt(cursor.getColumnIndex(AddProductModel.COLUMN_ID)));
                note.setProductName(cursor.getString(cursor.getColumnIndex(AddProductModel.COLUMN_PRODUCT_NAME)));
                note.setProductDescription(cursor.getString(cursor.getColumnIndex(AddProductModel.COLUMN_PRODUCT_DESCRIPTION)));
                note.setProductPrice(cursor.getString(cursor.getColumnIndex(AddProductModel.COLUMN_PRODUCT_PRICE)));
                note.setProductLat(cursor.getString(cursor.getColumnIndex(AddProductModel.COLUMN_PRODUCT_LAT)));
                note.setProductLong(cursor.getString(cursor.getColumnIndex(AddProductModel.COLUMN_PRODUCT_LONG)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(AddProductModel.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public void deleteCategory(AddProductModel note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AddProductModel.TABLE_NAME, AddProductModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }


    public int updateCategory(AddProductModel note, String categoryName, String description, String price, String lat, String lon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AddProductModel.COLUMN_PRODUCT_NAME, categoryName);
        values.put(AddProductModel.COLUMN_PRODUCT_DESCRIPTION, description);
        values.put(AddProductModel.COLUMN_PRODUCT_PRICE, price);
        values.put(AddProductModel.COLUMN_PRODUCT_LAT, lat);
        values.put(AddProductModel.COLUMN_PRODUCT_LONG, lon);
        // updating row
        return db.update(AddProductModel.TABLE_NAME, values, AddProductModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

}
