package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

// Created with help from this article
//https://www.digitalocean.com/community/tutorials/android-sqlite-database-example-tutorial
public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private final Context context;

    public DBManager(Context c){
        context = c;
    }

    public DBManager open() throws SQLException{
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public Cursor createUser(String username, String email, String password){
        //Stop if account with supplied username already exists
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?",new String[]{username},null);
        if(cursor.getCount() > 0){
            return null;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.USERNAME,username);
        contentValues.put(DBHelper.EMAIL,email);
        contentValues.put(DBHelper.PASSWORD,password);
        long result = db.insert(DBHelper.TABLE_NAME,null,contentValues);
        if(result == -1){ //-1 = ERROR
            return null;
        }
        return cursor;
    }

    public Cursor attemptLogin(String username, String password){
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",new String[]{username,password},null);
        if(cursor.getCount() > 0){
            return cursor;
        }
        else{
            return null;
        }
    }
}
