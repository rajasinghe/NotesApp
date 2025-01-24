package com.example.notesapp.UI.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notesapp.UI.UIStates.NoteUiState;
import com.example.notesapp.data.Result;
import com.example.notesapp.data.models.Note;
import com.example.notesapp.data.repositaries.NotesRepositary;

import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class NotesListViewModel extends ViewModel {
    private NotesRepositary notesRepositary;

    private MutableLiveData<List<NoteUiState>> notesUiState = new MutableLiveData(new ArrayList<NoteUiState>());

    private MutableLiveData<NoteUiState> noteUiState = new MutableLiveData<NoteUiState>();

    private MutableLiveData<Boolean> navigatedState = new MutableLiveData<Boolean>(false);

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
                uiStates.add(new NoteUiState(note.getTitle(),note.getBody(),note.getId()));
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

    public MutableLiveData<NoteUiState> getNoteUiState(){
        return  noteUiState;
    }

    public MutableLiveData<Boolean> getNavigatedState(){
        return this.navigatedState;
    }

    public Result<Boolean> addNote(){
        NoteUiState uiState = this.noteUiState.getValue();
        Result result = notesRepositary.insert(uiState.getTitle(), uiState.getBody());
        //now the state needs to be updated so that the update will be shown
        fetchNotes();
        return  result;
    }

    public Result<Boolean> deleteNote(int id){
        Result result =  notesRepositary.delete(id);
        if(result instanceof Result.Success){
            fetchNotes();
        }
        return  result;
    }


    public Result<Boolean> updateNote(){
        NoteUiState uiState = this.noteUiState.getValue();
        Result result = notesRepositary.update(uiState.getTitle(), uiState.getBody(), uiState.getId());
        fetchNotes();
        return  result;
    }


}
