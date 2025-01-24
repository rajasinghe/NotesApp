package com.example.notesapp.data.DB.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesapp.data.DB.entities.Note;

import java.util.List;

@Dao
public interface  NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Query("SELECT * FROM note WHERE id=:id")
    List<Note> getById(int id);

    @Insert
    void insertAll(Note... notes);

    @Update
    void updateAll(Note... notes);

    @Delete
    void delete(Note note);

}
