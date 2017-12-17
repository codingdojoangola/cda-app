package com.codingdojoangola.server.network;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigureFirebase {

    //:::::::::::: Constants
    private static DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth;

    //::::::::::::: Fields

    //**************************************** PUBLIC METHODS **************************************
    public static DatabaseReference getDatabaseReference(){

        if(databaseReference == null)
            databaseReference = FirebaseDatabase.getInstance().getReference();

        return databaseReference;
    }

    public static FirebaseAuth getFireBaseAuth(){

        if(firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance();

        return firebaseAuth;
    }
    //**********************************************************************************************
}

