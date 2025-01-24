package com.example.notesapp.data.apis;


import com.example.notesapp.data.apis.models.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonplaceholderService {
    @GET("/posts")
    Call<List<Note>> notes();
}
