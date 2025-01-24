package com.example.notesapp.UI.Activities;

import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;

import com.example.notesapp.UI.UIStates.LoadingUiState;
import com.example.notesapp.UI.ViewModels.LoadingViewModal;
import com.example.notesapp.UI.ViewModels.factories.LoadingViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.notesapp.databinding.ActivityMainBinding;

import com.example.notesapp.R;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    private LoadingViewModal loadingViewModal;

    /*
        this activity here the display the sync process while showing a user a entry screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadingViewModal = new ViewModelProvider(this,new LoadingViewModelFactory(this)).get(LoadingViewModal.class);
        final Observer<LoadingUiState> loadingObserver = new Observer<LoadingUiState>() {
            @Override
            public void onChanged(LoadingUiState loadingUiState) {
                binding.loadingText.setText(loadingUiState.getStatusText());
                if(loadingUiState.getStatus() == LoadingUiState.StatusType.FINISHED){
                    //go to the next activity even on failure
                    Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        loadingViewModal.getLoadingState().observe(this,loadingObserver);
        loadingViewModal.sync();

    }
}