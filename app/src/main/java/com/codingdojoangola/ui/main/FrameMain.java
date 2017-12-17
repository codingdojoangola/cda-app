package com.codingdojoangola.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingdojoangola.R;

public class FrameMain extends Fragment {

    private static final String ARG_CHOICE = "choiceType";

    protected PageIndicator mIndicator;
    protected ViewPager mViewPager;
    protected FrameAdapterPager frameAdapterPager;

    private String choiceType;

    //******************************* CONSTRUCT ************************************************************
    public FrameMain(){}

    public static FrameMain newInstance(String choiceType) {

        Bundle bpager = new Bundle();
        bpager.putString(ARG_CHOICE, choiceType);

        FrameMain frameMain = new FrameMain();
        frameMain.setArguments(bpager);

        return frameMain;
    }

    //************************************************************************************************
    private void readBundle(Bundle bundle){
        if(bundle!=null){
            choiceType = getArguments().getString(ARG_CHOICE);
        }
    }

    //********************************* ON CREATE VIEW *********************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        readBundle(getArguments());
        //:::::::::::::::::::::::::::: SUBMENUS ::::::::::::::::::::::::::::
        String [] subMenu = null;
        switch (choiceType){
            case ""+R.string.project:
                subMenu = getResources().getStringArray(R.array.subMenuProject);		            //PROJECT
                break;

            case ""+R.string.member:
                subMenu = getResources().getStringArray(R.array.subMenuMember);		        //INFORMATION
                break;

            case ""+R.string.cda:
                subMenu = getResources().getStringArray(R.array.subMenuCDA);		                //CDA
                break;

        }

        //---------- ListView by Sub-Menu -------------------------
        frameAdapterPager = new FrameAdapterPager(getFragmentManager(), inflater.getContext(), subMenu);
        frameAdapterPager.notifyDataSetChanged();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager)rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(frameAdapterPager);

        //----------------- Sub-Menu Music ------------------------------
        mIndicator = (TitlePageIndicator)getActivity().findViewById(R.id.indicator);
        mIndicator.setViewPager(mViewPager, R.color.white);


        return rootView;
    }
    //**********************************************************************************************
}
