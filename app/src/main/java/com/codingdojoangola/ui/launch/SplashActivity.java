package com.codingdojoangola.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.codingdojoangola.R;
import com.codingdojoangola.app.CDA;
import com.codingdojoangola.data.sharedpreferences.UserSharedPreferences;
import com.codingdojoangola.ui.main.MainActivity;


public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    //:::::::::::: Constants


    //::::::::::::: Fields
    private CDA app;
    private View partialSplash1, partialSplash2;
    private UserSharedPreferences mUserSharedPreferences;
    private TextView mLoginButton, mSkipButton;
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

        // create login view
        mLoginButton = findViewById(R.id.begin_login);
        mLoginButton.setOnClickListener(this);

        // create skip views
        mSkipButton = findViewById(R.id.begin_skip);
        mSkipButton.setOnClickListener(this);

        mUserSharedPreferences = new UserSharedPreferences(this);

        partialSplash1 = findViewById(R.id.activation);
        partialSplash2 = findViewById(R.id.active);

        // Check if user has email saved
        if (mUserSharedPreferences.getEmail().isEmpty()){
            partialSplash1.setVisibility(View.VISIBLE);
            partialSplash2.setVisibility(View.GONE);
        } else {
            // wait for 2 seconds
            final Handler handler = new Handler();
            handler.postDelayed(this::openMainActivity, 2000);
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
    // Open main activity if user exists
    private void openMainActivity(){
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    // open login activity
    private void openLoginActivity(){
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    // click listeners for login and skip text views
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.begin_login:
                openLoginActivity();
                break;
            case R.id.begin_skip:
                break;
        }
    }

    //:::::::::::::::::::::::::::: Loading Class



    //***************************** INNER CLASSES OT INTERFACES ************************************



    //**********************************************************************************************
}
