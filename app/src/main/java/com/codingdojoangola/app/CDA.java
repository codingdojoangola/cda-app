package com.codingdojoangola.app;

import android.app.Application;

import com.codingdojoangola.server.NetworkServer;
import com.codingdojoangola.split.MainDrawer;


public class CDA extends Application {

    private  String username;

    //::::::::::::::::::::::::: CACHE :::::::::::::::::::::::::::


    //************************ onCREATE ******************************
    @Override
    public void onCreate() {
        super.onCreate();

        //Preferences

        //Get a Handle to a SharedPreferences

        // Initialize the singletons so their instances
        // are bound to the application process.
        initSingletons();
    }

    //************************ Loading Class **************************
    protected void initSingletons() {

        NetworkServer.initInstance(this);
        MainDrawer.initInstance(this);
    }

    //************************* GET AND SET **************************
    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
