package com.example.notesapp.UI.UIStates;

public class NoteUiState {
    final String title;
    final String body;

    public NoteUiState(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
    //here this state model should have update and delete methods so consumer can call them if needed
}
