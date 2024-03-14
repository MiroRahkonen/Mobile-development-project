package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    public String attemptLogin(String username, String password){
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",new String[]{username,password},null);
        if(cursor.getCount() > 0){
            cursor.close();
            return username;
        }
        else{
            cursor.close();
            return null;
        }
    }

    public Note createNote(String username,String message){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.MESSAGE,message);
        contentValues.put(DBHelper.USERNAME,username);

        long result = db.insert(DBHelper.NOTE_TABLE,null,contentValues);
        if(result == -1){
            return null;
        }

        return fetchLatestNote(username);
    }

    public Note fetchLatestNote(String user){
        // Fetch latest note
        Cursor cursor = db.rawQuery("SELECT * FROM notes WHERE username = ? ORDER BY note_id DESC LIMIT 1",new String[]{user});

        // Create a new note if one is found
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String message = cursor.getString(1);
            String username = cursor.getString(2);
            cursor.close();
            return new Note(id,message,username);
        }
        else{
            cursor.close();
            return null;
        }
    }

    public List<Note> fetchNotes(String user){
        Cursor cursor = db.rawQuery("SELECT * FROM notes WHERE username = ?",new String[]{user},null);

        List<Note> notes = new ArrayList<>();
        //Add all results to notes list in a loop
        if (cursor.moveToFirst()) {
            do{
                int id = cursor.getInt(0);
                String message = cursor.getString(1);
                String username = cursor.getString(2);
                notes.add(new Note(id,message,username));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return notes;
    }

    public boolean deleteNote(int id, String user){
        long result = db.delete(DBHelper.NOTE_TABLE,"note_id = ? AND username=?",new String[]{String.valueOf(id),user});
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
}
