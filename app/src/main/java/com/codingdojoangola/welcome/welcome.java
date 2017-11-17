package com.codingdojoangola.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.codingdojoangola.R;
import com.codingdojoangola.app.Profile;

public class welcome extends AppCompatActivity {

    private int _boolean;

    private LinearLayout success, fail;
    private Button start, try_again;

    private Intent intent;

    //************************************* ON CREATE **************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        if(savedInstanceState != null) {
            _boolean = savedInstanceState.getInt("_boolean");

        }else{
            //---------- receive data
            Bundle extras = getIntent().getExtras();
            _boolean = extras.getInt("_boolean");
        }

        success = (LinearLayout)findViewById(R.id.success);
        fail = (LinearLayout)findViewById(R.id.fail);

        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(welcome.this, Profile.class);
                startActivity(intent);
            }
        });

        try_again = (Button)findViewById(R.id.try_again);
        try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(welcome.this, Login.class);
                startActivity(intent);
            }
        });

        Result();

    }

    //************************************ RESULT ****************************************
    public void Result(){

        if(_boolean == 1){
            success.setVisibility(View.VISIBLE);
            fail.setVisibility(View.INVISIBLE);
        }else{
            success.setVisibility(View.INVISIBLE);
            fail.setVisibility(View.VISIBLE);
        }
    }

    //**********************************************************************************************
}
