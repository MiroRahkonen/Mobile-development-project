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


    // help with this equals function from this answer on stackoverflow: https://stackoverflow.com/a/41061950
    // equals override is required for IndexOf() function calls to find the correct object from Note list
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