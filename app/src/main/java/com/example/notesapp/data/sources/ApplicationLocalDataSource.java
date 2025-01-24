package com.example.notesapp.data.sources;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notesapp.data.Result;
import com.example.notesapp.data.models.ApplicationData;

import java.io.IOException;
import java.time.LocalDateTime;

public class ApplicationLocalDataSource {

    private final Context context;
    public ApplicationLocalDataSource(Context context){
        this.context=context;
    }
    public Result<ApplicationData> getData(){
        try{
            SharedPreferences sharedPreferences = this.context.getSharedPreferences("config",Context.MODE_PRIVATE);
            String syncedTimeString=sharedPreferences.getString("syncedTime",null);
            LocalDateTime syncedTime=null;
            if( syncedTimeString!= null){
                syncedTime = LocalDateTime.parse(syncedTimeString);
            }
            ApplicationData data = new ApplicationData(sharedPreferences.getBoolean("synced",false),syncedTime,sharedPreferences.getInt("userId",-1));
            return  new Result.Success<>(data);
        }catch (Exception e){
            return new Result.Error(new IOException("fetch_Application_Data_Error",e));
        }
    }

    public void store(ApplicationData data){
            SharedPreferences sharedPreferences = this.context.getSharedPreferences("config",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("synced",data.isSynced());
            editor.putString("syncedTime",data.getSyncedTime().toString());
            editor.putInt("userId", data.getUserId());
            editor.apply();
    }
}
