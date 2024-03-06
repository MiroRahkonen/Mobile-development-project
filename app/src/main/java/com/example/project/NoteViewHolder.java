package com.example.project;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    TextView note_title_tv;
    TextView note_username_tv;
    Button note_delete_button;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        note_title_tv = itemView.findViewById(R.id.note_title_tv);
        note_username_tv = itemView.findViewById(R.id.note_username_tv);
        note_delete_button = itemView.findViewById(R.id.note_delete_button);
    }
}
