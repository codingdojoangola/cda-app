package com.codingdojoangola.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import android.view.View.OnClickListener;

import com.codingdojoangola.R;
import com.codingdojoangola.app.MainCDA;

public class Begin extends AppCompatActivity {

    private Intent intent;

    //************************************* ON CREATE **********************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin);

        TextView begin_login = (TextView)findViewById(R.id.begin_login);
        begin_login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                intent = new Intent(Begin.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        TextView begin_skip = (TextView)findViewById(R.id.begin_skip);
        begin_skip.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                intent = new Intent(Begin.this, MainCDA.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //**********************************************************************************************
}
