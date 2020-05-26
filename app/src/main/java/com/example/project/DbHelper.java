package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.tech.NfcA;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.example.project.DbHelper.USERS.DATABASE_NAME;
import static com.example.project.DbHelper.USERS.DATABASE_VERSION;
import static com.example.project.DbHelper.USERS.USERS_COLUMN_ADDRESS;
import static com.example.project.DbHelper.USERS.USERS_COLUMN_EMAIL;
import static com.example.project.DbHelper.USERS.USERS_COLUMN_ID;
import static com.example.project.DbHelper.USERS.USERS_COLUMN_NAME;
import static com.example.project.DbHelper.USERS.USERS_COLUMN_PASSWORD;
import static com.example.project.DbHelper.USERS.USERS_COLUMN_PHONE;
import static com.example.project.DbHelper.USERS.USERS_CREATE_TABLE;
import static com.example.project.DbHelper.USERS.USERS_TABLE_NAME;

public class DbHelper extends SQLiteOpenHelper {

    public class USERS {
        public static final String DATABASE_NAME = "VegaDB";
        public static final int DATABASE_VERSION = 3;


        public static final String USERS_TABLE_NAME ="Users";
        public static final String USERS_COLUMN_ID ="UserId";
        public static final String USERS_COLUMN_EMAIL ="Email";
        public static final String USERS_COLUMN_NAME ="FullName";
        public static final String USERS_COLUMN_PASSWORD ="Password";
        public static final String USERS_COLUMN_CONFIRM ="Confirm";
        public static final String USERS_COLUMN_ADDRESS = "Address";
        public static final String USERS_COLUMN_PHONE = "Phone";

        public static final String USERS_CREATE_TABLE = "CREATE TABLE " + USERS_TABLE_NAME + " ( " + USERS_COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERS_COLUMN_EMAIL +" text, " +
                USERS.USERS_COLUMN_NAME + " text, " + USERS.USERS_COLUMN_PASSWORD + " text, " + USERS_COLUMN_ADDRESS + " TEXT, "
                + USERS_COLUMN_PHONE  + " TEXT, "+ USERS.USERS_COLUMN_CONFIRM + " text);";
    }


    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS.USERS_CREATE_TABLE);
        db.execSQL(products_Helper.Products.PRODUCTS_CREATE_TABLE);
        db.execSQL(card_Helper.Card.CARD_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(USERS_CREATE_TABLE);
        db.execSQL(products_Helper.Products.PRODUCTS_CREATE_TABLE);
        db.execSQL(card_Helper.Card.CARD_CREATE_TABLE);
    }

    public ArrayList<User> getUserInfo(String email){
        ArrayList<User> userInfo = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String whereCalue = USERS_COLUMN_EMAIL + " = ?";
        String whereArgs[] = new String[]{email};
        Cursor cursor = db.query(USERS_TABLE_NAME, null, whereCalue, whereArgs, null,null,null);
        cursor.moveToFirst();
        User user = new User(cursor.getString(cursor.getColumnIndex(USERS_COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(USERS_COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(USERS_COLUMN_ADDRESS)),
                cursor.getString(cursor.getColumnIndex(USERS_COLUMN_PHONE)));
        userInfo.add(user);
        cursor.close();
        return userInfo;
    }

    public String updateUser(String name, String email, String address, String phone){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(USERS_COLUMN_NAME, name);
    values.put(USERS_COLUMN_EMAIL, email);
    values.put(USERS_COLUMN_ADDRESS, address);
    values.put(USERS_COLUMN_PHONE, phone);
    String whereCalue = USERS_COLUMN_EMAIL + " = ?";
    String whereArgs[] = new String[]{email};
    long result = db.update(USERS_TABLE_NAME, values, whereCalue, whereArgs);
    String msg = "Not UPDATED";
    if (result>0){
        msg = "UPDATED Successfully";
    }
    return msg;
    }

    public String getUserName(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String whereCalue = USERS_COLUMN_EMAIL + " = ?";
        String whereArgs[] = new String[]{email};
        Cursor cursor = db.query(USERS_TABLE_NAME, null, whereCalue, whereArgs, null,null,null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(USERS_COLUMN_NAME));
        cursor.close();
        return name;
    }

    public String UserAdd(String email,String name, String password, String confirm)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values =new ContentValues();
        values.put(USERS_COLUMN_EMAIL,email);
        values.put(USERS.USERS_COLUMN_NAME,name);
        values.put(USERS.USERS_COLUMN_PASSWORD,password);
        values.put(USERS.USERS_COLUMN_CONFIRM,confirm);

        long result = db.insert(USERS_TABLE_NAME,null,values);

        String msg = "Kayıt Başarısız!";
        if (result>0)
        {
            msg = "Kayıt Başarılı!";
        }
        return msg;
    }

    public Boolean SignIn(String email,String password){
        String [] columns = {USERS_COLUMN_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USERS_COLUMN_EMAIL +" =? "+ " AND " + USERS.USERS_COLUMN_PASSWORD +" =? ";
        String [] selectionArgs = { email, password };
        Cursor cursor = db.query(USERS_TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return true;
        else
            return false;
    }
    public static boolean isValidPassword(String password) {
        if (!((password.length() >= 8)
                && (password.length() <= 15))) {
            return false;
        }
        if (password.contains(" ")) {
            return false;
        }
        if (true) {
            int count = 0;
            for (int i = 0; i <= 9; i++) {
                String str1 = Integer.toString(i);

                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }
        if (!(password.contains("@") || password.contains("#")
                || password.contains("!") || password.contains("~")
                || password.contains("$") || password.contains("%")
                || password.contains("^") || password.contains("&")
                || password.contains("*") || password.contains("(")
                || password.contains(")") || password.contains("-")
                || password.contains("+") || password.contains("/")
                || password.contains(":") || password.contains(".")
                || password.contains(", ") || password.contains("<")
                || password.contains(">") || password.contains("?")
                || password.contains("|"))) {
            return false;
        }

        if (true) {
            int count = 0;
            for (int i = 65; i <= 90; i++) {
                char c = (char)i;

                String str1 = Character.toString(c);
                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }

        if (true) {
            int count = 0;
            for (int i = 90; i <= 122; i++) {
                char c = (char)i;
                String str1 = Character.toString(c);

                if (password.contains(str1)) {
                    count = 1;
                }
            }
            if (count == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public boolean chkemail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE_NAME + " WHERE " + USERS_COLUMN_EMAIL+ " = ?", new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    public boolean chkemail1(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USERS_TABLE_NAME + " WHERE " + USERS_COLUMN_EMAIL+ " = ?", new String[]{email});
        if(cursor.getCount()>0) return true;
        else return false;
    }
    public String updatePassword(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERS_COLUMN_EMAIL, email);
        values.put(USERS_COLUMN_PASSWORD, password);
        String whereCalue = USERS_COLUMN_EMAIL + " = ?";
        String whereArgs[] = new String[]{email};
        long result = db.update(USERS_TABLE_NAME, values, whereCalue, whereArgs);
        String msg = "PASSWORD Not UPDATED";
        if (result>0){
            msg = "PASSWORD UPDATED Successfully";
        }
        return msg;
    }

}
