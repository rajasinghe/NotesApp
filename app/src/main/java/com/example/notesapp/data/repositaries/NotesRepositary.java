package com.example.notesapp.data.repositaries;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.notesapp.data.Result;
import com.example.notesapp.data.apis.NotesApi;
import com.example.notesapp.data.models.ApplicationData;
import com.example.notesapp.data.models.Note; // note that is required by the ui layer
import com.example.notesapp.data.repositaries.states.SyncState;
import com.example.notesapp.data.sources.ApplicationLocalDataSource;
import com.example.notesapp.data.sources.NotesLocalDataSource;
import com.example.notesapp.data.sources.NotesRemoteDataSource;
import com.example.notesapp.data.sources.UserLocalDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotesRepositary {
    //repositary is responsible for merging the 2 data souces and make the Local Data Store updated with Api results
    /*
        TODO
            -updaing the room database using this source of data(only the notes related to the user is to be saved locally)
            -user id requred for sync of data
     */

    private final MutableLiveData<SyncState> syncState = new MutableLiveData(new SyncState("sync not started yet",false));

    //volatile for ensuring visibility when using multiple threads if needed
    private static  volatile NotesRepositary instance;
    private NotesLocalDataSource notesLocalDataSource;
    private NotesRemoteDataSource remoteNotesDataSource;

    private ApplicationLocalDataSource applicationLocalDataSource;


    private NotesRepositary(NotesLocalDataSource localNotesSource, NotesRemoteDataSource remoteNotesSource, ApplicationLocalDataSource applicationLocalDataSource){
            this.notesLocalDataSource=localNotesSource;
            this.remoteNotesDataSource=remoteNotesSource;
            this.applicationLocalDataSource=applicationLocalDataSource;
    }

    public static NotesRepositary getInstance(NotesLocalDataSource localNotesSource,NotesRemoteDataSource remoteNotesSource,ApplicationLocalDataSource applicationLocalDataSource){
        if(instance == null){
            instance = new NotesRepositary(localNotesSource,remoteNotesSource,applicationLocalDataSource);
        }
        return  instance;
    }

    public Result<ApplicationData> isSynced() {
        Result<ApplicationData> result = this.applicationLocalDataSource.getData();
        return result;
    }


    public void sync(NotesSyncCallback callback){
        //sync the remote datasore data with localdatastore
        this.remoteNotesDataSource.fetchNotes(new NotesApi.NotesApiCallback() {
            @Override
            public void onSuccess(Result<List<com.example.notesapp.data.apis.models.Note>> result) {
                int sucesscCount = 0;
                int failedCount = 0;
                //todo - do something with the failed and success counts
                if(result instanceof Result.Success){
                    for (com.example.notesapp.data.apis.models.Note note:((Result.Success<List<com.example.notesapp.data.apis.models.Note>>) result).getData()
                    ) {
                        Log.d("debug", "onSuccess: "+note.getTitle());
                        Result dbResult =  notesLocalDataSource.insert(new com.example.notesapp.data.DB.entities.Note(note.getTitle(),note.getBody()));
                        if(dbResult instanceof Result.Success){
                            sucesscCount++;
                        }else{
                            failedCount++;
                            Log.d("database op failed", "on failure: "+((Result.Error)dbResult).getError());
                        }
                    }
                    callback.onSuccess(new SyncState("synced successfully sc"+sucesscCount + "  fc"+ failedCount));
                }else{
                    callback.onFailure(new SyncState("failed :remote data source failed"));
                }
            }

            @Override
            public void onFailure(Result.Error error) {
                callback.onFailure(new SyncState("failed :remote data source failed"));
            }
        });
        //fetching from remote store may be failed


        //set the synced to false after syncing fro avoid future syncs // or keep a app in persiitentnt storage
    }

    public MutableLiveData<SyncState> getSyncedState (){
        return  this.syncState;
    }

    public Result<List<Note>> getNotes(){
        Result<List<com.example.notesapp.data.DB.entities.Note>> result = notesLocalDataSource.fetchAllNotes();
        List<Note> transformedList = new ArrayList<Note>();
        if(result instanceof Result.Success){
            for (
                    com.example.notesapp.data.DB.entities.Note note : ((Result.Success<List<com.example.notesapp.data.DB.entities.Note>>) result).getData()
            ) {
                transformedList.add(new Note(note.id,note.title,note.body));
            }
            return  new Result.Success(transformedList);
        }else{
            return new Result.Error(new IOException("fetch_data_locally_failed"));
        }

    }

    public Result<Boolean> insert(String tittle,String body){
        return notesLocalDataSource.insert(new com.example.notesapp.data.DB.entities.Note(tittle,body));
    }

    public Result<Boolean> delete(int id){
        return notesLocalDataSource.delete(new com.example.notesapp.data.DB.entities.Note(id,"",""));
    }

    public interface NotesSyncCallback{
        void onSuccess(SyncState syncStateResult);
        void onFailure(SyncState syncStateResult);
    }

}
