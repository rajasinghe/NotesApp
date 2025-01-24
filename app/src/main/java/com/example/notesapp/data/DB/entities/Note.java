package com.example.notesapp.data.DB.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;

    public String body;

    public Note(){}

    @Ignore
    public Note(String title,String body){
        this.title=title;
        this.body=body;
    }

    @Ignore
    public Note(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
