package com.example.notesapp.UI.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.notesapp.UI.UIStates.LoadingUiState;
import com.example.notesapp.data.Result;
import com.example.notesapp.data.repositaries.NotesRepositary;
import com.example.notesapp.data.repositaries.states.SyncState;

public class LoadingViewModal extends ViewModel {
    private final MutableLiveData<LoadingUiState> uiState  = new MutableLiveData(new LoadingUiState("initializing", LoadingUiState.StatusType.IDLE));
    private final NotesRepositary notesRepositary;

    public LoadingViewModal(NotesRepositary notesRepositary) {
        this.notesRepositary = notesRepositary;
    }

    public void sync(){
        uiState.setValue(new LoadingUiState("sync statrted", LoadingUiState.StatusType.STARTED));
        notesRepositary.sync(new NotesRepositary.NotesSyncCallback() {
            @Override
            public void onSuccess(SyncState syncStateResult) {
                uiState.setValue(new LoadingUiState("sync success msg:" +syncStateResult.getStatus(), LoadingUiState.StatusType.FINISHED ));
            }

            @Override
            public void onFailure(SyncState syncStateResult) {
                uiState.setValue(new LoadingUiState("sync success msg:" +syncStateResult.getStatus(), LoadingUiState.StatusType.FINISHED));
            }
        });

    }

    public MutableLiveData<LoadingUiState> getLoadingState(){
        return  this.uiState;
    }

    public interface SyncCallback {
        void onSuccess();
        Void onFailure();
    }

}
