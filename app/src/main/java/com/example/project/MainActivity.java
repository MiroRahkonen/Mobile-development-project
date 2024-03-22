package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        TextView appbar_title_textview = findViewById(R.id.appbar_title_textview);
        Button appbar_logout_button = findViewById(R.id.appbar_logout_button);
        Button add_note_button = findViewById(R.id.add_note_button);
        EditText add_note_edittext = findViewById(R.id.add_note_edittext);

        //Getting current user from the intent
        Intent intent = getIntent();
        current_user = intent.getStringExtra("username");

        //Initializing appbar
        String activity_title = current_user + "'s Notes";
        appbar_title_textview.setText(activity_title);
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

        //Enable add note button only when some text has been entered
        add_note_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(add_note_edittext.getText().length() > 0){
                    add_note_button.setBackgroundTintList(getColorStateList(R.color.primary));
                    add_note_button.setClickable(true);
                    add_note_button.setTextColor(getColor(R.color.white));
                } else{
                    add_note_button.setBackgroundTintList(getColorStateList(R.color.inactive));
                    add_note_button.setClickable(false);
                    add_note_button.setTextColor(getColor(R.color.black));
                }

            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });



        // Add button at the bottom of the screen to insert new note into database
        add_note_button.setOnClickListener(v ->  {
            // Sending new note to database
            Note new_note = dbManager.createNote(current_user,add_note_edittext.getText().toString());
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

