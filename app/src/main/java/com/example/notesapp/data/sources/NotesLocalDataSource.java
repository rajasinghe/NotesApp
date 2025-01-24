package com.example.notesapp.data.sources;

import com.example.notesapp.data.DB.AppDatabase;
import com.example.notesapp.data.DB.DB;
import com.example.notesapp.data.DB.dao.NoteDao;
import com.example.notesapp.data.DB.entities.Note;
import com.example.notesapp.data.Result;

import java.util.List;

public class NotesLocalDataSource {
    //responsible for operations related to the local data store
    /*
        TODO
        -implement the fetching mechnism from room database.
        -implement the datasource update mechnisms for updating the state of the room database.
     */
    public Result<List<Note>> fetchAllNotes(){
        try{
            AppDatabase db = DB.getInstance();
            NoteDao noteDao = db.noteDao();
            List<Note> dbNotes = noteDao.getAll();
            return new Result.Success(dbNotes);
        }catch (Exception e){
            return new Result.Error(e);
        }
    }

    public Result<Boolean> insert(Note note){
        try {
            AppDatabase db = DB.getInstance();
            NoteDao noteDao = db.noteDao();
            noteDao.insertAll(note);
            return new Result.Success(true);
        }catch (Exception e){
            return  new Result.Error(e);
        }

    }

    public Result<Boolean> update(Note note){
        try{
            AppDatabase db = DB.getInstance();
            NoteDao noteDao = db.noteDao();
            noteDao.updateAll(note);
            return new Result.Success(true);
        }catch (Exception e){
            return  new Result.Error(e);
        }
    }

    public Result<Boolean> delete(Note note){
        try{
            AppDatabase db = DB.getInstance();
            NoteDao noteDao = db.noteDao();
            noteDao.delete(note);
            return new Result.Success(true);
        }catch (Exception e){
            return  new Result.Error(e);
        }

    }

}
