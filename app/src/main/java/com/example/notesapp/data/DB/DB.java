package com.example.notesapp.data.DB;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DB extends Application {
    private static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db=Room
                .databaseBuilder(this.getApplicationContext(),AppDatabase.class,"notes-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() //thi (need to use another mechanism)
                .build();
    }


    public static AppDatabase getInstance() {
        if (db == null) {
            throw new IllegalStateException("Database has not been initialized. Ensure 'DB' is added in AndroidManifest.xml");
        }
        return db;
    }

}
