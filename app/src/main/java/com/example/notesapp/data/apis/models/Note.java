package com.example.notesapp.data.apis.models;

public class Note {
    private int userId;
    private int id;
    private String title;
    private String body;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
