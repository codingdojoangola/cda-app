package com.codingdojoangola.app;

//:::::::::::::::: Android imports
import android.app.Application;

//:::::::::::::::: Import from third parties (com, junit, net, org)


//:::::::::::::::: Java and javax


//:::::::::::::::: Same project import
import com.codingdojoangola.server.network.NetworkServer;
import com.codingdojoangola.ui.split.MainDrawer;
import com.google.firebase.database.FirebaseDatabase;

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

    //**********************************************************************************************
}
