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

    public boolean createUser(String username, String email, String password){
        //Stop if account with supplied username already exists
        String[] user = {username};
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?",user,null);
        if(cursor.getCount() > 0){
            return false;
        }
        cursor.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.USERNAME,username);
        contentValues.put(DBHelper.EMAIL,email);
        contentValues.put(DBHelper.PASSWORD,password);
        long result = db.insert(DBHelper.USER_TABLE,null,contentValues);
        if(result == -1){ //-1 = ERROR
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor attemptLogin(String username, String password){
        String[] credentials = {username,password};
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",credentials,null);
        if(cursor.getCount() > 0){
            return cursor;
        }
        else{
            cursor.close();
            return null;
        }
    }

    public boolean createNote(String username,String note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.USERNAME,username);
        contentValues.put(DBHelper.NOTE,note);
        long result = db.insert(DBHelper.NOTE_TABLE,null,contentValues);
        if(result == -1){
            return false;
        }
        return true;
    }

    public Cursor fetchNotes(String username){
        String[] user = {username};
        Cursor cursor = db.rawQuery("SELECT * FROM notes WHERE username = ?",user,null);
        if(cursor.getCount() > 0){
            return cursor;
        }
        else{
            cursor.close();
            return null;
        }
    }
}
