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

    public NoteAdapter(Context context,List<Note> notes){
        this.context = context;
        this.notes = notes;
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
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}