package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected DBManager dbManager;
    protected String current_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView notes_rv = findViewById(R.id.notes_rv);
        TextView appbar_title_tv = findViewById(R.id.appbar_title_tv);
        Button add_note_button = findViewById(R.id.add_note_button);
        EditText add_note_tv = findViewById(R.id.add_note_tv);

        // List for all the notes in the RecyclerView
        List<Note> notes = new ArrayList<Note>();

        //Getting current user from the intent
        Intent intent = getIntent();
        current_user = intent.getStringExtra("username");

        String activity_title = current_user + "'s Notes";
        appbar_title_tv.setText(activity_title);

        //SQLite database init
        dbManager = new DBManager(this);
        dbManager.open();

        Cursor cursor = dbManager.fetchNotes(current_user);
        if (cursor.moveToFirst()) {
            do{
                String note = cursor.getString(1);
                String user = cursor.getString(2);
                notes.add(new Note(user,note));
            } while(cursor.moveToNext());
        }
        cursor.close();

        notes.add(new Note("Random title 1","Username 1"));
        notes.add(new Note("Random title 2","Username 2"));
        notes.add(new Note("Random title 3","Username 3"));
        notes.add(new Note("Random title 4","Username 4"));
        notes.add(new Note("Random title 5","Username 5"));
        notes.add(new Note("Random title 6","Username 6"));
        notes.add(new Note("Random title 7","Username 7"));
        notes.add(new Note("Random title 8","Username 8"));
        notes.add(new Note("Random title 9","Username 9"));
        notes.add(new Note("Random title 10","Username 10"));
        notes.add(new Note("Random title 11","Username 11"));
        notes.add(new Note("Random title 12","Username 12"));
        notes.add(new Note("Random title 13","Username 13"));
        notes.add(new Note("Random title 14","Username 14"));
        notes.add(new Note("Random title 15","Username 15"));
        notes.add(new Note("Random title 16","Username 16"));

        notes_rv.setLayoutManager(new LinearLayoutManager(this));
        notes_rv.setAdapter(new NoteAdapter(getApplicationContext(),notes));
        notes_rv.addItemDecoration(new DividerItemDecoration(notes_rv.getContext(),DividerItemDecoration.VERTICAL));

        add_note_button.setOnClickListener(v ->  {
            String note = add_note_tv.getText().toString();
            Toast.makeText(MainActivity.this,note,Toast.LENGTH_SHORT).show();
            boolean success = dbManager.createNote(current_user,note);
            if(success){
                Toast.makeText(MainActivity.this,"Note saved",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"Error saving note...",Toast.LENGTH_SHORT).show();
            }
        });
    }
}