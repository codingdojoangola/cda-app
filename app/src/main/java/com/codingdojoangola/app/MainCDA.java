package com.codingdojoangola.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.codingdojoangola.R;
import com.codingdojoangola.fragment.TransactionFragment;
import com.codingdojoangola.notify.QuitApplication;
import com.codingdojoangola.model.ItemDrawer;
import com.codingdojoangola.split.MainDrawer;
import com.codingdojoangola.split.MenuAdapter;

import java.util.List;

public class MainCDA extends AppCompatActivity {

    private List<ItemDrawer> mainDrawer;

    private int positionSplit;

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    //::::: Main Drawer
    private RelativeLayout nav_header_profile;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private MenuAdapter menuAdapter;

    //::::: Action Bar
    private Toolbar toolbar ;

    private Intent nextLayout;

    //************************ OneCreate ****************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //::::: Action Bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //::::::: Action MenuSplit
        nav_header_profile = (RelativeLayout) findViewById(R.id.nave_header );
        nav_header_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextLayout = new Intent(MainCDA.this, Profile.class);
                startActivity(nextLayout);
                finish();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);                                    //to add a listener and

        // Menu Home - (open split)
        MenuSplit();

        //:::FRAGMENT
        if (savedInstanceState == null)
            positionSplit = 1;

        selectFragment(positionSplit);

    }

    //************************************** SPLIT *************************************************
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

    //*************** DRAWER ACTION BAR - NOT SAME FOR FRAGMENT (CHANGE) ***************************
    public void createCustomActionBar (CharSequence name){

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitle(name);
    }

    //************************************** CREATE MENU  ******************************************
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_qevents, menu);
        return true;
    }
    */
    //***************************************** Activity Exit **************************************
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
    //********************************* NEW FRAGMENT ACTION ****************************************
    public void selectFragment(int positionSplit) {
        // Transaction start
        transaction = fragmentManager.beginTransaction();

        switch (mainDrawer.get(positionSplit).getChoiceType()){

            case ""+R.string.settings:
                break;

            case ""+R.string.about:
                break;

            case ""+R.string.exit:
                // Exit App
                QuitApplication.newInstance(this).show(transaction, "Alert_Dialog" );
                break;

            default:
                createCustomActionBar(mainDrawer.get(positionSplit).getNameType());
                menuAdapter.setSelectedPosition(positionSplit);
                menuAdapter.notifyDataSetChanged();

                //update Fragment
                new TransactionFragment(MainCDA.this,
                        transaction,
                        mainDrawer.get(positionSplit).getChoiceType(),
                        false
                ).execute();

        }
        drawer.closeDrawer(GravityCompat.START);
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
