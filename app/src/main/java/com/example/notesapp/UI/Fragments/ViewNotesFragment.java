package com.example.notesapp.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesapp.R;
import com.example.notesapp.UI.UIStates.NoteUiState;
import com.example.notesapp.UI.ViewModels.NotesListViewModel;
import com.example.notesapp.UI.adapters.NotesListAdapter;
import com.example.notesapp.data.Result;
import com.example.notesapp.databinding.FragmentViewNotesBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewNotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewNotesFragment extends Fragment {

    private NotesListViewModel notesListViewModel;

    private NotesListAdapter notesListAdapter;
    FragmentViewNotesBinding binding;

    public ViewNotesFragment() {
        // Required empty public constructor
    }


    public static ViewNotesFragment newInstance() {
        ViewNotesFragment fragment = new ViewNotesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        binding = FragmentViewNotesBinding.inflate(inflater,container,false);
        RecyclerView recyclerView = binding.notesRecyclerView;

        notesListViewModel =new ViewModelProvider(requireActivity()).get(NotesListViewModel.class);

        notesListAdapter = new NotesListAdapter(notesListViewModel.getNotesUiState(), new NotesListAdapter.NoteItemListener() {
            @Override
            public void onItemLongClick(int id) {
                Result<Boolean> result= notesListViewModel.deleteNote(id);
                if(result instanceof Result.Success){
                    Toast.makeText(getContext(), "deleted : "+id, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),  ((Result.Error) result).getError().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemClick(int id, String tittle, String body) {
                Log.d("", "onItemClick: "+id);
                notesListViewModel.getNoteUiState().postValue(new NoteUiState(tittle,body,id));
                getParentFragmentManager().beginTransaction().replace(R.id.notes_fragment_container, NoteFragment.class,null).addToBackStack(null).commit();
                notesListViewModel.getNavigatedState().postValue(true);
            }


        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(notesListAdapter);
//        final Observer<List<NoteUiState>> noteUiStateObserver =new Observer<List<NoteUiState>>() {
//            @Override
//            public void onChanged(List<NoteUiState> noteUiStates) {
//                for (NoteUiState noteUiState:
//                        noteUiStates ) {
//                    Log.d("states", "onChanged: "+noteUiState.getTitle());
//                }
//            }
//        };
//        notesListViewModel.getNotesUiState().observe(this,noteUiStateObserver);
        return binding.getRoot();
    }
}