package com.example.project;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder>{
    Context context;
    List<Note> notes;
    protected DBManager dbManager;

    public NoteAdapter(Context context,List<Note> notes){
        this.context = context;
        this.notes = notes;

        //SQLite database init
        dbManager = new DBManager(this.context);
        dbManager.open();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.note_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        String username_text = "Added by: " +notes.get(position).getUsername();
        holder.note_username_textview.setText(username_text);

        holder.note_message_edittext.setText(notes.get(position).getMessage());

        // Note editing button in each RecyclerView item
        holder.note_edit_button.setOnClickListener(v ->{
            holder.note_message_edittext.setFocusable(true);
            holder.note_message_edittext.requestFocus();
        });

        holder.note_delete_button.setOnClickListener(view -> {
            boolean delete_success = dbManager.deleteNote(notes.get(position).getId(),notes.get(position).getUsername());
            if(delete_success){
                Toast.makeText(context,"Note deleted!",Toast.LENGTH_SHORT);
                notes = dbManager.fetchNotes(notes.get(position).getUsername());
                notifyDataSetChanged();

                //Optimal, but doesn't always delete the correct item
                //notes.remove(position);
                //notifyItemRemoved(position);
            }
        });


    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
}