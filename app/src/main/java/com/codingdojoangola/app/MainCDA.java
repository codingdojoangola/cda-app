package com.codingdojoangola.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codingdojoangola.R;


public class MainCDA extends AppCompatActivity {

    private int gPositionSplit, chPositionSplit;

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    //************************ OneCreate ****************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        //:::FRAGMENT
        if (savedInstanceState == null) {
            gPositionSplit = 0;
            chPositionSplit = 0;

        }

        selectFragment(gPositionSplit, chPositionSplit);

    }
    public void selectFragment(int groupPositionSplit, int childPositionSplit) {
        Intent nextLayout;
        String nameTitle;

        // Transaction start
        transaction = fragmentManager.beginTransaction();
    }
    //**********************************************************************************************
}
