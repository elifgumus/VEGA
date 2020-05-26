package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class card_Helper extends SQLiteOpenHelper {

    public class Card {
        public static final String DATABASE_NAME = "VegaDB";
        public static final int DATABASE_VERSION = 3;

        public static final String CARD_TABLE_NAME ="Card";
        public static final String CARD_COLUMN_ID ="ProductId";
        public static final String CARD_COLUMN_NAME ="ProductName";
        public static final String CARD_COLUMN_PRICE ="ProductPrice";
        public static final String CARD_COLUMN_SELLER ="Seller";
        public static final String CARD_COLUMN_USER = "User";
        public static final String CARD_COLUMN_IMAGE = "pImage";

        public static final String CARD_CREATE_TABLE = "CREATE TABLE " + CARD_TABLE_NAME + " ( " + CARD_COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CARD_COLUMN_NAME + " text, " + CARD_COLUMN_PRICE + " text, " + CARD_COLUMN_SELLER + " TEXT, "
                + CARD_COLUMN_USER  + " TEXT, " + CARD_COLUMN_IMAGE + " BOLOB);";
    }

    public card_Helper(@Nullable Context context) {
        super(context, Card.DATABASE_NAME, null, Card.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Card.CARD_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Card.CARD_CREATE_TABLE);
    }

    public String addToCard(String name, String price, String seller, String user, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Card.CARD_COLUMN_NAME, name);
        values.put(Card.CARD_COLUMN_PRICE, price);
        values.put(Card.CARD_COLUMN_SELLER, seller);
        values.put(Card.CARD_COLUMN_USER, user);
        values.put(Card.CARD_COLUMN_IMAGE, image);

        long result = db.insert(Card.CARD_TABLE_NAME, null, values);
        String msg = "Something went error";
        if (result>0)
            msg = "Added to card successfully";
        return msg;
    }

    public ArrayList<CardInfo> getProducts(String user){
        ArrayList<CardInfo> productsInCard = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String whereCalue = Card.CARD_COLUMN_USER + " = ?";
        String whereAgs[] = new String[]{user};
        Cursor cursor = db.query(Card.CARD_TABLE_NAME, null, whereCalue, whereAgs, null, null,null);
        while (cursor.moveToNext()){
            CardInfo cardInfo = new CardInfo(cursor.getString(cursor.getColumnIndex(Card.CARD_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(Card.CARD_COLUMN_PRICE)),
                    cursor.getString(cursor.getColumnIndex(Card.CARD_COLUMN_SELLER)),
                    cursor.getString(cursor.getColumnIndex(Card.CARD_COLUMN_USER)),
                    cursor.getBlob(cursor.getColumnIndex(Card.CARD_COLUMN_IMAGE)));
            productsInCard.add(cardInfo);
        }
        return productsInCard;
    }

    public String removeProductFromCard(String pName, String seller , String user){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereCalue = Card.CARD_COLUMN_NAME + " = ? and " + Card.CARD_COLUMN_SELLER + " = ? and " + Card.CARD_COLUMN_USER + " = ?";
        String whereArgs[] = new String[]{pName, seller, user};
        long result = db.delete(Card.CARD_TABLE_NAME, whereCalue, whereArgs);
        String msg = "Not DELETED";
        if (result > 0){
            msg = "DELETED Successfully";
        }
        return msg;
    }
}
