package com.example.project;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    TextView note_username_textview;
    TextView note_id_textview;
    EditText note_message_edittext;
    Button note_delete_button;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        note_username_textview = itemView.findViewById(R.id.note_username_textview);
        note_id_textview = itemView.findViewById(R.id.note_id_textview);
        note_message_edittext = itemView.findViewById(R.id.note_message_edittext);
        note_delete_button = itemView.findViewById(R.id.note_delete_button);

    }
}
