package com.example.notesapp.data.sources;

import com.example.notesapp.data.models.User;

public class UserLocalDataSource {
    //responsible for user that is saved locally

    /*
        TODO
            - only a hard coded user is present current (no login)
            -when requested get the hard coded user for merging the data
     */
    public User getUser(){
        return  new User(1,"Pasindu Gimhan");
    }

}
