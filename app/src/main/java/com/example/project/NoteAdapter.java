package com.example.project;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
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
        String id_text = "ID: " + String.valueOf(notes.get(position).getId());

        holder.note_username_textview.setText(username_text);
        holder.note_id_textview.setText(id_text);
        holder.note_message_edittext.setText(notes.get(position).getMessage());

        // Note editing button in each RecyclerView item
        holder.note_edit_button.setOnClickListener(v ->{
            holder.note_message_edittext.setFocusable(true);
            holder.note_message_edittext.requestFocus();
        });

        holder.note_delete_button.setOnClickListener(view -> {
            int note_id = notes.get(position).getId();
            String message = notes.get(position).getMessage();
            String username = notes.get(position).getUsername();

            boolean delete_success = dbManager.deleteNote(note_id,username);
            if(delete_success){
                Toast.makeText(context,"Note deleted!",Toast.LENGTH_SHORT);
                int deleted_note_position = notes.indexOf(new Note(note_id,message,username));
                Toast.makeText(context,String.valueOf(deleted_note_position),Toast.LENGTH_SHORT).show();
                notes.remove(deleted_note_position);
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
}