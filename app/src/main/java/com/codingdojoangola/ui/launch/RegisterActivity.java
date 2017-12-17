package com.codingdojoangola.ui.launch;

//:::::::::::::::: Android imports
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.Window;
import android.view.WindowManager;

//:::::::::::::::: Import from third parties (com, junit, net, org)

//:::::::::::::::: Java and javax

//:::::::::::::::: Same project import
import com.codingdojoangola.R;

public class RegisterActivity extends AppCompatActivity{

    //:::::::::::: Constants


    //::::::::::::: Fields



    //*********************************** ON CREATE *********************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /* create a full screen window */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);

    }

    //*********************** Override Methods and Callbacks (public and Private) ******************


    //************************************* PUBLIC METHODS ****************************************



    //************************************* PRIVATE METHODS ***************************************




    //**********************************************************************************************
}
