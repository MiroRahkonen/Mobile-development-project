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
    List<Note> notes = new ArrayList<>();
    protected DBManager dbManager;
    protected String current_user;
    protected RecyclerView notes_recyclerview;
    protected NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView appbar_title_tv = findViewById(R.id.appbar_title_tv);
        Button add_note_button = findViewById(R.id.add_note_button);
        EditText add_note_tv = findViewById(R.id.add_note_tv);

        //Getting current user from the intent
        Intent intent = getIntent();
        current_user = intent.getStringExtra("username");

        //Initializing appbar title
        String activity_title = current_user + "'s Notes";
        appbar_title_tv.setText(activity_title);

        //SQLite database init
        dbManager = new DBManager(this);
        dbManager.open();
        fetchAllNotes();

        notes_recyclerview = findViewById(R.id.notes_rv);
        notes_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        notes_recyclerview.addItemDecoration(new DividerItemDecoration(notes_recyclerview.getContext(),DividerItemDecoration.VERTICAL));

        noteAdapter = new NoteAdapter(getApplicationContext(),notes);
        notes_recyclerview.setAdapter(noteAdapter);

        add_note_button.setOnClickListener(v ->  {
            String note = add_note_tv.getText().toString();
            Toast.makeText(MainActivity.this,note,Toast.LENGTH_SHORT).show();
            boolean success = dbManager.createNote(current_user,note);
            if(success){
                fetchNewestNote();
                noteAdapter.notifyItemInserted(notes.size()-1);
            }
            else{
                Toast.makeText(MainActivity.this,"Error saving note...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAllNotes(){
        Cursor cursor = dbManager.fetchNotes(current_user);
        if (cursor.moveToFirst()) {
            do{
                int id = cursor.getInt(0);
                String note = cursor.getString(1);
                String user = cursor.getString(2);
                notes.add(new Note(id,user,note));
            } while(cursor.moveToNext());
        }
        cursor.close();
    }

    private void fetchNewestNote(){
        try (Cursor cursor = dbManager.fetchLatestNote(current_user)) {
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            String note = cursor.getString(1);
            String user = cursor.getString(2);
            notes.add(new Note(id,user,note));
        }
    }
}

