package com.example.notesapp.data.models;

public class Note {
    //the notes model that is required by the application .the structure is as of what the application expects nothing more for avoid unneccessary memory usage
    private int id;
    private String title;
    private String body;

    public Note(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
