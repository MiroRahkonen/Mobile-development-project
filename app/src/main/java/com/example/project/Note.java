package com.example.project;

public class Note{
    int id;
    String message;
    String username;

    public Note(int id, String message, String username) {
        this.id = id;
        this.message = message;
        this.username = username;
    }

    public int getId(){return id;}
    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }


    // equals override is required for IndexOf() function calls, checking for all variables
    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (object == null) return false;
        if (getClass() != object.getClass()) return false;

        Note other = (Note) object;
        if(id != other.id) return false;
        if(!message.equals(other.message)) return false;
        if(!username.equals(other.username)) return false;
        return true;
    }
}