package com.example.notesapp.UI.UIStates;

public class LoadingUiState {
    private final String statusText;

    private final StatusType status;

    public LoadingUiState(String statusText,StatusType status) {
        this.statusText = statusText;
        this.status=status;
    }

    public String getStatusText() {
        return statusText;
    }

    public StatusType getStatus(){
        return this.status;
    }

    public enum StatusType{
        IDLE,
        STARTED,
        ONGOING,
        FINISHED
    }
}
