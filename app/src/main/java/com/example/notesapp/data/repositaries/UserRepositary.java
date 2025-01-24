package com.example.notesapp.data.repositaries;

import com.example.notesapp.data.models.User;
import com.example.notesapp.data.sources.UserLocalDataSource;

public class UserRepositary {

    private static volatile UserRepositary instance;
    private  UserLocalDataSource dataSource;

    private UserRepositary(UserLocalDataSource dataSource){
        this.dataSource=dataSource;
    }

    public static UserRepositary getInstance(UserLocalDataSource dataSource){
        if(instance==null){
            instance = new UserRepositary(dataSource);
        }
        return  instance;
    }

    public User getCurrentUser(){
        return  dataSource.getUser();
    }


}
