package com.example.notesapp.data.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.notesapp.data.DB.dao.NoteDao;
import com.example.notesapp.data.DB.entities.Note;

@Database(entities = {Note.class},version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
