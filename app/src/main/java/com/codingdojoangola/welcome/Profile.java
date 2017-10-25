package com.codingdojoangola.welcome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.codingdojoangola.R;
import com.codingdojoangola.app.CDA;

public class Profile extends AppCompatActivity {

    private CDA cda;

    private TextView profile_username;
    //*********************************** ON CREATE ***********************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);

        cda = (CDA) getApplication();

        profile_username = (TextView)findViewById(R.id.profile_username);

        profile_username.setText(cda.getUsername());
    }
}
