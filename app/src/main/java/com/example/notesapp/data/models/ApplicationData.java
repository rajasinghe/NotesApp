package com.example.notesapp.data.models;

import java.time.LocalDateTime;

public class ApplicationData {
    private boolean  synced;
    private LocalDateTime syncedTime;

    private  int userId ;

    public ApplicationData(boolean isSynced,LocalDateTime syncedTime,int userId){
        this.synced=isSynced;
        this.syncedTime=syncedTime;
        this.userId = userId;
    }


    public boolean isSynced() {
        return synced;
    }

    public LocalDateTime getSyncedTime() {
        return syncedTime;
    }

    public int getUserId() {
        return userId;
    }
}
