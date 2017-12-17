package com.codingdojoangola.ui.launch;

//:::::::::::::::: Android imports
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//:::::::::::::::: Import from third parties (com, junit, net, org)

//:::::::::::::::: Java and javax

//:::::::::::::::: Same project import
import com.codingdojoangola.R;

public class RegisterFragment extends Fragment {

    //:::::::::::: Constants


    //::::::::::::: Fields


    //*********************************** CONSTRUCTORS *********************************************


    //*********************************** ON CREATE *********************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    //*********************** Override Methods and Callbacks (public and Private) ******************


    //*************************************** PUBLIC METHODS ***************************************



    //*************************************** PRIVATE METHODS **************************************



    //***************************** INNER CLASSES OT INTERFACES ************************************



    //**********************************************************************************************
}
