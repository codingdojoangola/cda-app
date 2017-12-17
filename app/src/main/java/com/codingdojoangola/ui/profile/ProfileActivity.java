package com.codingdojoangola.ui.profile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.codingdojoangola.R;
import com.codingdojoangola.app.CDA;
import com.codingdojoangola.ui.main.MainActivity;

public class ProfileActivity extends AppCompatActivity {

    private CDA cda;

    private TextView username, profession;

    //*********************************** ON CREATE ***********************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        cda = (CDA) getApplication();

        username = (TextView)findViewById(R.id.profile_username);
        //username.setText(cda.getUsername());
        profession = (TextView) findViewById(R.id.profile_profession);
        //profession.setText(cda.getProfession());

        //::::::::::::::::::::::: Action Bar :::::::::::::::::::::::::::::::::
        createCustomActionBar();

    }

    //***********************
    public void createCustomActionBar (){
        //::::: Action Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        //setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(mainDrawer.get(typeSplit).getNavigationBarColor());
        }

        //CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
    }

    //**************** RETURN
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent(ProfileActivity.this, MainActivity.class);
        //setResult(RESULT_OK, intent);
        //moveTaskToBack(true);
        startActivity(returnIntent);
        finish();
    }
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle presses on the action bar items (Options)
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                //or
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
    //**********************************************************************************************
}
