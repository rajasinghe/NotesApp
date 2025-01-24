package com.example.notesapp.UI.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notesapp.UI.UIStates.NoteUiState;
import com.example.notesapp.data.Result;
import com.example.notesapp.data.models.Note;
import com.example.notesapp.data.repositaries.NotesRepositary;

import java.util.ArrayList;
import java.util.List;

public class NotesListViewModel extends ViewModel {
    private NotesRepositary notesRepositary;

    MutableLiveData<List<NoteUiState>> notesUiState = new MutableLiveData(new ArrayList<NoteUiState>());
    public NotesListViewModel(NotesRepositary notesRepositary){
        this.notesRepositary=notesRepositary;
    }

    public Result<Boolean> fetchNotes(){
        Result<List<Note>> result = notesRepositary.getNotes();
        //update the notes ui state
        if(result instanceof Result.Success){
            List<Note> notes = ((Result.Success<List<Note>>) result).getData();
            List<NoteUiState> uiStates = new ArrayList<>();
            for (Note note:
                 notes) {
                uiStates.add(new NoteUiState(note.getTitle(),note.getBody()));
            }
            if(notesUiState != null){
                notesUiState.getValue().clear();
            }
            notesUiState.setValue(uiStates);
            return  new Result.Success(true);
        }else{
            return new Result.Error(((Result.Error) result).getError());
        }
    }

    public MutableLiveData<List<NoteUiState>> getNotesUiState() {
        return notesUiState;
    }

    public Result<Boolean> addNote(String tittle, String body){
       return notesRepositary.insert(tittle,body);
    }

    public Result<Boolean> deleteNote(int id){
        return  notesRepositary.delete(id);
    }

}
