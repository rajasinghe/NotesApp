package com.example.notesapp.UI.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.R;
import com.example.notesapp.UI.Fragments.NoteFragment;
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
    public void onBackPressed() {
        super.onBackPressed();
        if(notesListViewModel.getNavigatedState().getValue()){
            notesListViewModel.getNavigatedState().postValue(false);
            notesListViewModel.getNoteUiState().postValue(null);
            binding.fab.setImageResource(android.R.drawable.ic_menu_add);
        }
    }

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        notesListViewModel = new ViewModelProvider(this,new NotesListViewModelFactory(this)).get(NotesListViewModel.class);
        Result<Boolean> result =  notesListViewModel.fetchNotes();
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notesListViewModel.getNavigatedState().getValue()){
                    Log.d("binding", "onClick: null ");
                    //insert operation should done in here
                    Log.d("insert values", "onClick: "+notesListViewModel.getNoteUiState().getValue().getTitle());
                    if(notesListViewModel.getNoteUiState().getValue().getId() == null){
                        Result addNoteResult = notesListViewModel.addNote();
                        if(addNoteResult instanceof Result.Success){
                            Toast.makeText(NotesActivity.this, "Successfully saved", Toast.LENGTH_SHORT).show();
                            Log.d("success", "onClick: successfullly inserted");
                        }else{
                            Toast.makeText(NotesActivity.this, "failed to saved", Toast.LENGTH_SHORT).show();
                            Log.d("error",((Result.Error)addNoteResult).getError().toString());
                        }
                    }else{
                        Result addNoteResult = notesListViewModel.updateNote();
                        if(addNoteResult instanceof Result.Success){
                            Toast.makeText(NotesActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                            Log.d("success", "onClick: successfullly updated");
                        }else{
                            Toast.makeText(NotesActivity.this, "failed to update", Toast.LENGTH_SHORT).show();
                            Log.d("error",((Result.Error)addNoteResult).getError().toString());
                        }
                    }

                    //if noteuistate has id then its a update operation
                    //the noteui state in viewmodel has the corresponding changed object
                    //fragmentManager.beginTransaction().replace(R.id.notes_fragment_container,NoteFragment.class,null).commit();
                    //binding.fab.setImageResource(android.R.drawable.ic_menu_save);
                }else{
                    Log.d("binding", "onClick: not null ");
                        //insert no pre data
                    notesListViewModel.getNoteUiState().postValue(new NoteUiState("","",null));
                    notesListViewModel.getNavigatedState().postValue(true);
                    fragmentManager.beginTransaction().replace(R.id.notes_fragment_container, NoteFragment.class,null).addToBackStack(null).commit();
                }
            }
        });


        final Observer<Boolean> navigationObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean navigated) {
                if (navigated) {
                    binding.fab.setImageResource(android.R.drawable.ic_menu_save);//i need to access this from ViewNotesList fragment
                }else{
                    binding.fab.setImageResource(android.R.drawable.ic_menu_add);//i need to access this from ViewNotesList fragment
                }
            }
        };
        notesListViewModel.getNavigatedState().observe(this,navigationObserver);


        //default navigating to the notes list
        fragmentManager.beginTransaction().replace(R.id.notes_fragment_container,ViewNotesFragment.class,null).commit();
    }
}