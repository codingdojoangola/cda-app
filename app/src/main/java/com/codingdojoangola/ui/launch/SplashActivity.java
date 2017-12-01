package com.codingdojoangola.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.codingdojoangola.R;
import com.codingdojoangola.app.CDA;
import com.codingdojoangola.ui.main.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashActivity extends AppCompatActivity {

    //:::::::::::: Constants


    //::::::::::::: Fields
    private CDA app;
    private ProgressBar progressBarInfy, progressBarFinite;
    private FirebaseUser currentUser;
    View partialSplash1, partialSplash2;

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

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        partialSplash1 = findViewById(R.id.activation);
        partialSplash2 = findViewById(R.id.active);


        if (currentUser == null){
            partialSplash1.setVisibility(View.VISIBLE);
            partialSplash2.setVisibility(View.GONE);
        } else {
            openMainActivity();
        }




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
    private void openMainActivity(){
        // wait for 2 seconds
        final Handler handler = new Handler();
        handler.postDelayed(() -> startActivity(new Intent(SplashActivity.this, MainActivity.class)), 2000);
    }

    //:::::::::::::::::::::::::::: Loading Class



    //***************************** INNER CLASSES OT INTERFACES ************************************



    //**********************************************************************************************
}
