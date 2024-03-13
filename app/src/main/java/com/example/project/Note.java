package com.example.project;

public class Note{
    int id;
    String message;
    String username;

    public Note(int id, String message, String username) {
        this.id = id;
        this.username = username;
        this.message = message;
    }

    public int getId(){return id;}
    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}