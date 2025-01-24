package com.example.notesapp.data.repositaries.states;

import androidx.annotation.Nullable;

public class SyncState {
    private String status;

    private boolean synced=false;
    @Nullable
    private Exception Error;

    public SyncState(String status,@Nullable Exception error){
        this.status=status;
        this.Error= error;
    }

    public SyncState(String status,@Nullable Exception error,Boolean iSyncFinished){
        this.status=status;
        this.Error= error;
        this.synced =iSyncFinished;
    }

    public SyncState(String status){
        this.status=status;
    }

    public SyncState(String status,Boolean iSyncFinished){
        this.status=status;
        this.synced =iSyncFinished;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSynced() {
        return synced;
    }

    @Nullable
    public Exception getError() {
        return Error;
    }
}
