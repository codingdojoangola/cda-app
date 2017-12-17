package com.codingdojoangola.ui.launch;

//:::::::::::::::: Android imports
import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

//:::::::::::::::: Import from third parties (com, junit, net, org)
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//:::::::::::::::: Java and javax
import java.lang.ref.WeakReference;

//:::::::::::::::: Same project import
import com.codingdojoangola.R;
import com.codingdojoangola.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    //:::::::::::: Constants

    //::::::::::::: Fields
    //private CDA app;
    //private UserSharedPreferences mUserSharedPreferences;
    //private FirebaseUser mCurrentUser;

    //*********************** Override Methods and Callbacks (public and Private) ******************
    //::::::::::::::::::::::::::::::::::: OnCreate ::::::::::::::::::::::
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* create a full screen window */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //app = (CDA) getApplication();

        //user currently logged in with firebase
        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        // create login view
        TextView mLoginButton = findViewById(R.id.begin_login);
        mLoginButton.setOnClickListener(this);

        // create skip views
        TextView mSkipButton = findViewById(R.id.begin_skip);
        mSkipButton.setOnClickListener(this);

        //mUserSharedPreferences = new UserSharedPreferences(this);

        // Check if user has email saved
        if (mCurrentUser == null){
            // wait for 3 seconds
            new MyTask(this).execute();

        } else {
            // wait for 3 seconds
            final Handler handler = new Handler();
            handler.postDelayed(this::openMainActivity, 3000);
        }
    }
    //************************************* PUBLIC METHODS *****************************************


    //************************************* PRIVATE METHODS ****************************************
    //::::::::: Open main activity if user exists
    private void openMainActivity(){
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    //::::::::: open login activity
    private void openLoginActivity(){
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }

    //:::::::::: click listeners for login and skip text views
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.begin_login:
                openLoginActivity();
                break;
            case R.id.begin_skip:
                openMainActivity();
                break;
        }
    }

    //***************************** INNER CLASSES OT INTERFACES ************************************
    private static class MyTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<SplashActivity> activityReference;

        //:::::: only retain a weak reference to the activity
        MyTask(SplashActivity context) {
            activityReference = new WeakReference<>(context);
        }
        //:::::: Override Methods and Callbacks
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //sPartialSplash1.setVisibility(View.INVISIBLE);
            //sPartialSplash2.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // get a reference to the activity if it is still there
            SplashActivity activity = activityReference.get();

            View partialSplash1 = activity.findViewById(R.id.activation);
            View partialSplash2 = activity.findViewById(R.id.active);

            // modify the activity's UI
            partialSplash1.setVisibility(View.VISIBLE);
            partialSplash2.setVisibility(View.GONE);
        }
    }
    //**********************************************************************************************
}
