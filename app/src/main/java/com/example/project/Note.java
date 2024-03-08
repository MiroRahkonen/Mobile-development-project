package com.example.project;

public class Note{
    int id;
    String title;
    String username;

    public Note(int id, String title, String username) {
        this.id = id;
        this.username = username;
        this.title = title;
    }

    public int getId(){return id;}
    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }
}