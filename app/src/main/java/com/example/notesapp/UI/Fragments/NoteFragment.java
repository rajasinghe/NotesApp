package com.example.notesapp.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notesapp.R;
import com.example.notesapp.UI.UIStates.NoteUiState;
import com.example.notesapp.UI.ViewModels.NotesListViewModel;
import com.example.notesapp.databinding.FragmentNoteBinding;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    FragmentNoteBinding binding;
    NotesListViewModel notesListViewModel;



    public NoteFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(getLayoutInflater());
        notesListViewModel = new ViewModelProvider(requireActivity()).get(NotesListViewModel.class);
        //MutableLiveData<NoteUiState> noteUiStateMutabl=  notesListViewModel.getNoteUiState();
        binding.noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NoteUiState currentState = notesListViewModel.getNoteUiState().getValue();
                notesListViewModel.getNoteUiState().postValue(new NoteUiState(s+"",currentState.getBody(), currentState.getId()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.noteBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NoteUiState currentState = notesListViewModel.getNoteUiState().getValue();
                notesListViewModel.getNoteUiState().postValue(new NoteUiState(currentState.getTitle(),s+"", currentState.getId()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final Observer<NoteUiState> noteChangeObserver = new Observer<NoteUiState>() {
            @Override
            public void onChanged(NoteUiState noteUiState) {
                if(noteUiState != null){
                    if (!binding.noteTitle.getText().toString().equals(noteUiState.getTitle())) {
                        binding.noteTitle.setText(noteUiState.getTitle());
                    }

                    if (!binding.noteBody.getText().toString().equals(noteUiState.getBody())) {
                        binding.noteBody.setText(noteUiState.getBody());
                    }
                }

            }
        };

        notesListViewModel.getNoteUiState().observe(this,noteChangeObserver);

        return binding.getRoot();

    }
}