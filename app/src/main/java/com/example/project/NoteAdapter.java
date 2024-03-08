package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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
        holder.note_title_tv.setText(notes.get(position).getTitle());
        holder.note_username_tv.setText(notes.get(position).getUsername());

        // Button deleting a note
        holder.note_delete_button.setOnClickListener(v -> {
            boolean delete_success = dbManager.deleteNote(notes.get(position).getId(),notes.get(position).getUsername());
            if(delete_success){
                notes.remove(position);
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
}