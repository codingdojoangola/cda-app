package com.codingdojoangola.ui.main;

//:::::::::::::::: Android imports
import android.os.Bundle;
import android.os.Build;
import android.content.Intent;
import android.content.res.Configuration;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

//:::::::::::::::: Import from third parties (com, junit, net, org)


//:::::::::::::::: Java and javax
import java.util.List;

//:::::::::::::::: Same project import
import com.codingdojoangola.models.split.ItemDrawer;
import com.codingdojoangola.ui.chat.ChatUserActivity;
import com.codingdojoangola.ui.notifications.QuitApplication;
import com.codingdojoangola.ui.profile.ProfileActivity;
import com.codingdojoangola.ui.settings.SettingsActivity;
import com.codingdojoangola.ui.split.MainDrawer;
import com.codingdojoangola.ui.split.MenuAdapter;

import com.codingdojoangola.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //:::::::::::: Constants


    //::::::::::::: Fields
    private List<ItemDrawer> mainDrawer;

    private int positionSplit;

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    //::::: Main Drawer
    private LinearLayout navigation_split_profile;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private MenuAdapter menuAdapter;

    //::::: Action Bar
    private Toolbar toolbar ;

    private Intent nextLayout;

    //************************************* ON CREATE **********************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //::::: Action Bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //::::::: Action Split profile
        navigation_split_profile = (LinearLayout) findViewById(R.id.navigation_split_profile );
        navigation_split_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextLayout = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(nextLayout);
                finish();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);                                    //to add a listener and

        // Split Menu - (open split)
        MenuSplit();

        //:::FRAGMENT
        if (savedInstanceState == null)
            positionSplit = 0;

        selectFragment(positionSplit);
    }

    //************************************** PUBLIC METHODS ****************************************
    //::::::::::::: DRAWER ACTION BAR - NOT SAME FOR FRAGMENT (CHANGE) :::::::
    public void createCustomActionBar (CharSequence name){

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitle(name);
    }

    //:::::::::::: Activity Exit ::::::::::::::::
    @Override
    public void onBackPressed() {
        // Close drawer
        for(int i=0; i<mainDrawer.size(); ++i) {
            //selectFragment(mainDrawer.get(10).getIdType(), 0);
            if(mainDrawer.get(i).getNameType().equalsIgnoreCase(getResources().getString(R.string.exit))) {
                selectFragment(i);
                break;
            }
        }
    }

    //:::::::::::::::::::: NEW FRAGMENT ACTION ::::::::::::::::::::
    public void selectFragment(int positionSplit) {
        // Transaction start
        transaction = fragmentManager.beginTransaction();

        switch (mainDrawer.get(positionSplit).getChoiceType()){

            case ""+R.string.chat:
                Intent intent =  new Intent(MainActivity.this, ChatUserActivity.class);
                startActivity(intent);
                break;
            case ""+R.string.settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;

            case ""+R.string.about:
                break;

            case ""+R.string.exit:
                // Exit App
                FirebaseAuth.getInstance().signOut();
                QuitApplication.newInstance(this).show(transaction, "Alert_Dialog" );
                break;

            default:
                createCustomActionBar(mainDrawer.get(positionSplit).getNameType());
                menuAdapter.setSelectedPosition(positionSplit);
                menuAdapter.notifyDataSetChanged();

                //update Fragment
                new TransactionFragment(MainActivity.this,
                        transaction,
                        mainDrawer.get(positionSplit).getChoiceType(),
                        false
                ).execute();

        }
        drawer.closeDrawer(GravityCompat.START);
    }

    //************************************* PRIVATE METHODS ****************************************
    //::::::::::::::::::: SPLIT MENU :::::::::::::::::::
    private void MenuSplit(){
        // Setup the data source
        mainDrawer = MainDrawer.getInstance().getMainDrawer();

        // instantiate the custom list adapter
        menuAdapter = new MenuAdapter(this, mainDrawer);

        // Set the MenuListAdapter to the ListView
        ListView itemsListView  = (ListView) findViewById(R.id.listView_splitMenu);
        itemsListView.setAdapter(menuAdapter);

        itemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectFragment(position);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    //**********************************************************************************************
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Pass any configuration change to the drawer toggles
        toggle.onConfigurationChanged(newConfig);
    }
    //**********************************************************************************************
}
