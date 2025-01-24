package com.example.notesapp.data.sources;

import com.example.notesapp.data.Result;
import com.example.notesapp.data.apis.NotesApi;
import com.example.notesapp.data.apis.models.Note;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class NotesRemoteDataSource {
    //responsible for dealing with the remote database (reuest from the API only)
    /*
        TODO
        -fetching the JSON string by Making a Api call ()
     */
    private final NotesApi noteapi;

    public NotesRemoteDataSource(){
        this.noteapi=new NotesApi();
    }
    public void fetchNotes(NotesApi.NotesApiCallback callback) {
        noteapi.getNotes(callback);
    }
}
