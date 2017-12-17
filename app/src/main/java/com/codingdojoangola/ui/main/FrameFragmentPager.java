package com.codingdojoangola.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingdojoangola.R;
import com.codingdojoangola.app.CDA;

public class FrameFragmentPager extends Fragment {

    private static final String ARG_SUBMENU = "subMenu";
    private static final String ARG_POSITION = "position";

    private CDA app;
    private  String[] subMenu ;
    private int position;

    //************************************* Constructor ********************************************
    public static FrameFragmentPager newInstance(String [] subMenu, int position) {

        Bundle bpager = new Bundle();
        bpager.putStringArray(ARG_SUBMENU, subMenu);
        bpager.putInt(ARG_POSITION, position);

        FrameFragmentPager pager = new FrameFragmentPager();
        pager.setArguments(bpager);

        return pager;
    }

    //***********************************************************************************************
    private void readBundle(Bundle bundle){
        if(bundle!=null){
            app = (CDA)getContext().getApplicationContext();
            subMenu = getArguments().getStringArray(ARG_SUBMENU);
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    //********************************** ON CREATE VIEW ********************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);

        readBundle(getArguments());

        Log.e("FrameFragmentPager", "subMenu - "+subMenu[position]);

        TextView textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText(subMenu[position]);

        return rootView;
    }

    //*********************************************************************************************
}
