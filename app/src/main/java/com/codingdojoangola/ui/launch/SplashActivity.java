package com.codingdojoangola.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.codingdojoangola.R;
import com.codingdojoangola.app.CDA;
import com.codingdojoangola.ui.main.MainActivity;


public class SplashActivity extends AppCompatActivity {

    //:::::::::::: Constants


    //::::::::::::: Fields
    private CDA app;

    private ProgressBar progressBarInfy, progressBarFinite;

    //*********************************** CONSTRUCTORS *********************************************


    //*********************** Override Methods and Callbacks (public and Private) ******************
    //::::::::::::::::::::::::::::::::::: OnCreate ::::::::::::::::::::::
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* create a full screen window */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        app = (CDA) getApplication();

        // sleep for 1 seconds
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        startActivity(new Intent(this, LoginActivity.class));

        /*
        //:::::::::::::: Loading :::::::::::::::
        progressBarInfy = (ProgressBar) findViewById(R.id.splash_progressBarInfy);
        progressBarFinite = (ProgressBar) findViewById(R.id.splash_progressBarFinite);

        //update database -  Downloads Events
        new Loading(this).execute();
        */
    }

    //**************************** PUBLIC METHODS ****************************



    //**************************** PRIVATE METHODS ****************************
    //:::::::::::::::::::::::::::: Loading Class



    //***************************** INNER CLASSES OT INTERFACES ************************************



    //**********************************************************************************************
}
