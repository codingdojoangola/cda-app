package com.codingdojoangola.app;

import android.app.Application;


public class CDA extends Application {

    private  String username;

    //************************ onCREATE ******************************
    @Override
    public void onCreate() {
        super.onCreate();

    }

    //************************* GET AND SET **************************
    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
