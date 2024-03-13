package com.example.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

// Created with help from this article
//https://www.digitalocean.com/community/tutorials/android-sqlite-database-example-tutorial
public class DBHelper extends SQLiteOpenHelper
{
    public static final String DBNAME = "Login.db";

    //User table information
    public static final String USER_TABLE = "users";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    //Note table information
    public static final String NOTE_TABLE = "notes";
    public static final String NOTE_ID = "note_id";
    public static final String MESSAGE = "message";

    public DBHelper(Context context){
        super(context,DBNAME,null,4);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (username TEXT PRIMARY KEY, email TEXT, password TEXT)");
        db.execSQL("CREATE TABLE notes (note_id INTEGER PRIMARY KEY, username TEXT, message TEXT)");
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
        onCreate(db);
    }
}