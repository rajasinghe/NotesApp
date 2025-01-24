package com.example.notesapp.UI.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.R;
import com.example.notesapp.UI.Fragments.ViewNotesFragment;
import com.example.notesapp.UI.UIStates.NoteUiState;
import com.example.notesapp.UI.ViewModels.NotesListViewModel;
import com.example.notesapp.UI.ViewModels.factories.NotesListViewModelFactory;
import com.example.notesapp.data.Result;
import com.example.notesapp.databinding.ActivityNotesBinding;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
    ActivityNotesBinding binding;

    NotesListViewModel notesListViewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityNotesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        notesListViewModel = new ViewModelProvider(this,new NotesListViewModelFactory(this)).get(NotesListViewModel.class);
        Result<Boolean> result =  notesListViewModel.fetchNotes();
        /*
        final Observer<List<NoteUiState>> noteUiStateObserver =new Observer<List<NoteUiState>>() {
            @Override
                public void onChanged(List<NoteUiState> noteUiStates) {
                    for (NoteUiState noteUiState:
                         noteUiStates ) {
                        Log.d("states", "onChanged: "+noteUiState.getTitle());
                    }
                }
            };
                   notesListViewModel.getNotesUiState().observe(this,noteUiStateObserver);

            */
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.notes_fragment_container,ViewNotesFragment.class,null).commit();

    }
}