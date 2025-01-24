package com.example.notesapp.data.apis;

import com.example.notesapp.data.Result;
import com.example.notesapp.data.apis.models.Note;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesApi {
    private static final String BASE_URL="https://jsonplaceholder.typicode.com";
    public void getNotes(NotesApiCallback callback) {

            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonplaceholderService service = retrofit.create(JsonplaceholderService.class);
            Call<List<Note>> call = service.notes();

            call.enqueue(new Callback<List<Note>>() {
                @Override
                public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                    if (response.isSuccessful()) {
                        callback.onSuccess(new Result.Success(response.body()));
                    } else {
                        callback.onFailure(new Result.Error(new IOException("Error with status code :" + response.code())));
                    }
                }

                @Override
                public void onFailure(Call<List<Note>> call, Throwable throwable) {
                    callback.onFailure(new Result.Error(new IOException(throwable)));
                }
            });
        }


    public interface NotesApiCallback{
        void onSuccess(Result<List<Note>> result);
        void onFailure(Result.Error error);
    }
}
