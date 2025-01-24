package com.example.notesapp.UI.ViewModels.factories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapp.UI.ViewModels.LoadingViewModal;
import com.example.notesapp.UI.ViewModels.NotesListViewModel;
import com.example.notesapp.data.repositaries.NotesRepositary;
import com.example.notesapp.data.sources.ApplicationLocalDataSource;
import com.example.notesapp.data.sources.NotesLocalDataSource;
import com.example.notesapp.data.sources.NotesRemoteDataSource;

public class NotesListViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public NotesListViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if(modelClass.isAssignableFrom(NotesListViewModel.class)){
            return  (T) new NotesListViewModel(NotesRepositary.getInstance(new NotesLocalDataSource(),new NotesRemoteDataSource(),new ApplicationLocalDataSource(context)));
        }else {
            throw new IllegalArgumentException("unknown model class ; login View modal class should be able to safely cast to model class ");
        }
    }
}
