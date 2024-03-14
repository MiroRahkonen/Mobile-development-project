package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        Button appbar_logout_button = findViewById(R.id.appbar_logout_button);
        Button add_note_button = findViewById(R.id.add_note_button);
        EditText add_note_tv = findViewById(R.id.add_note_tv);

        //Getting current user from the intent
        Intent intent = getIntent();
        current_user = intent.getStringExtra("username");

        //Initializing appbar
        String activity_title = current_user + "'s Notes";
        appbar_title_tv.setText(activity_title);
        appbar_logout_button.setVisibility(View.VISIBLE);

        //SQLite database init
        dbManager = new DBManager(this);
        dbManager.open();
        notes = dbManager.fetchNotes(current_user);

        notes_recyclerview = findViewById(R.id.notes_rv);
        notes_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        notes_recyclerview.addItemDecoration(new DividerItemDecoration(notes_recyclerview.getContext(),DividerItemDecoration.VERTICAL));

        noteAdapter = new NoteAdapter(getApplicationContext(),notes);
        notes_recyclerview.setAdapter(noteAdapter);


        // Add button at the bottom of the screen to insert new note into database
        add_note_button.setOnClickListener(v ->  {
            // Sending new note to database
            Note new_note = dbManager.createNote(current_user,add_note_tv.getText().toString());
            if(new_note == null){
                Toast.makeText(MainActivity.this,"Error saving note...",Toast.LENGTH_SHORT).show();
            }
            else{
                notes.add(new_note);
                noteAdapter.notifyItemInserted(notes.size()-1);
            }
        });

        // Button in appbar to return to login screen
        appbar_logout_button.setOnClickListener(v ->{
            Toast.makeText(MainActivity.this,"Logged out of session",Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

