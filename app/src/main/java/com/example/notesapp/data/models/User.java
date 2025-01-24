package com.example.notesapp.data.models;

public class User {
    //the user reqiured by the application
    private int userId;
    private String userName;

    public User(int userId,String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
