package com.example.notesapp.UI.UIStates;

import androidx.annotation.Nullable;

public class NoteUiState {
    final String title;
    final String body;
    final Integer id;

    public NoteUiState(String title, String body,@Nullable Integer id) {
        this.title = title;
        this.body = body;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Nullable
    public Integer getId() {
        return id;
    }
    //here this state model should have update and delete methods so consumer can call them if needed
}
