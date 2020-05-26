package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import static com.example.project.DbHelper.USERS.USERS_COLUMN_ADDRESS;
import static com.example.project.DbHelper.USERS.USERS_COLUMN_EMAIL;
import static com.example.project.DbHelper.USERS.USERS_COLUMN_NAME;
import static com.example.project.DbHelper.USERS.USERS_COLUMN_PHONE;
import static com.example.project.DbHelper.USERS.USERS_TABLE_NAME;

public class products_Helper extends SQLiteOpenHelper {



    public class Products {
        public static final String DATABASE_NAME = "VegaDB";
        public static final int DATABASE_VERSION = 3;


        public static final String PRODUCTS_TABLE_NAME ="Products";
        public static final String PRODUCTS_COLUMN_ID ="ProductsID";
        public static final String PRODUCTS_COLUMN_PRICE ="ProductPrice";
        public static final String PRODUCTS_COLUMN_NAME ="ProductName";
        public static final String PRODUCTS_COLUMN_QUANTITY ="ProductQuantity";
        public static final String PRODUCTS_COLUMN_OWNER ="ProductOwner";
        public static final String PRODUCTS_COLUMN_TYPE = "ProductType";
        public static final String PRODUCTS_COLUMN_IMAGE = "ProductImage";

        public static final String PRODUCTS_CREATE_TABLE = "CREATE TABLE " + PRODUCTS_TABLE_NAME + " ( " + PRODUCTS_COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRODUCTS_COLUMN_NAME +" text, " +
                PRODUCTS_COLUMN_PRICE + " text, " + PRODUCTS_COLUMN_QUANTITY + " text, " + PRODUCTS_COLUMN_OWNER + " text, " +
                PRODUCTS_COLUMN_TYPE + " ,text, " + PRODUCTS_COLUMN_IMAGE + " BLOB);";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Products.PRODUCTS_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Products.PRODUCTS_CREATE_TABLE);
    }

    public products_Helper(@Nullable Context context) {
        super(context, Products.DATABASE_NAME, null, Products.DATABASE_VERSION);
    }

    public String addProduct(String name, String price, String quantity, String owner, String type, byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values =new ContentValues();
        values.put(Products.PRODUCTS_COLUMN_NAME,name);
        values.put(Products.PRODUCTS_COLUMN_PRICE,price);
        values.put(Products.PRODUCTS_COLUMN_QUANTITY,quantity);
        values.put(Products.PRODUCTS_COLUMN_OWNER, owner);
        values.put(Products.PRODUCTS_COLUMN_TYPE, type);
        values.put(Products.PRODUCTS_COLUMN_IMAGE, image);

        long result = db.insert(Products.PRODUCTS_TABLE_NAME,null,values);

        String msg = "Not INSERTED";
        if (result>0)
        {
            msg = "INSERTED Successfully";
        }
        return msg;
    }
    public ArrayList<products> getProductSInfo(){
        ArrayList<products> productInfo = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Products.PRODUCTS_TABLE_NAME, null, null, null, null,null,null);
        while (cursor.moveToNext()){
            products product = new products(cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_PRICE)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_QUANTITY)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_OWNER)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_TYPE)),
                    cursor.getBlob(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_IMAGE)));
            productInfo.add(product);
        }

        cursor.close();
        return productInfo;
    }
    public ArrayList<products> getUserProductsInfo(String user){
        ArrayList<products> productInfo = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String whereCalue = Products.PRODUCTS_COLUMN_OWNER + " = ?";
        String whereArgs[] = new String[]{user};
        Cursor cursor = db.query(Products.PRODUCTS_TABLE_NAME, null, whereCalue, whereArgs, null,null,null);
        while (cursor.moveToNext()){
            products product = new products(cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_PRICE)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_QUANTITY)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_OWNER)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_TYPE)),
                    cursor.getBlob(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_IMAGE)));
            productInfo.add(product);
        }

        cursor.close();
        return productInfo;
    }
    public String deleteProduct(String product, String seller){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereCalue = Products.PRODUCTS_COLUMN_NAME + " = ? and " + Products.PRODUCTS_COLUMN_OWNER + " = ?";
        String whereArgs[] = new String[]{product,seller};
        long result = db.delete(Products.PRODUCTS_TABLE_NAME, whereCalue, whereArgs);
        String msg = "Not DELETED";
        if (result>0){
            msg = "DELETED Successfully";
        }
        return msg;
    }
    public ArrayList<products> getProductsByType(String type){
        ArrayList<products> productInfo = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String whereCalue = Products.PRODUCTS_COLUMN_TYPE + " = ?";
        String whereArgs[] = new String[]{type};
        Cursor cursor = db.query(Products.PRODUCTS_TABLE_NAME, null, whereCalue, whereArgs, null,null,null);
        while (cursor.moveToNext()){
            products product = new products(cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_PRICE)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_QUANTITY)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_OWNER)),
                    cursor.getString(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_TYPE)),
                    cursor.getBlob(cursor.getColumnIndex(Products.PRODUCTS_COLUMN_IMAGE)));
            productInfo.add(product);
        }

        cursor.close();
        return productInfo;
    }
}
