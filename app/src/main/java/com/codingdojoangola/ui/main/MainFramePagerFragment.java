package com.codingdojoangola.ui.main;

//:::::::::::::::: Android imports
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//:::::::::::::::: Import from third parties (com, junit, net, org)

//:::::::::::::::: Java and javax

//:::::::::::::::: Same project import
import com.codingdojoangola.R;
import com.codingdojoangola.app.CDA;
import com.codingdojoangola.ui.members.MembersFragment;

public class MainFramePagerFragment extends Fragment {

    private static final String ARG_SUBMENU = "subMenu";
    private static final String ARG_POSITION = "position";
    private static final String ARG_CHOICE = "choice";

    private CDA app;
    private  String[] subMenu ;
    private int position;
    private String mChoiceType;

    //************************************* Constructor ********************************************
    public static MainFramePagerFragment newInstance(String [] subMenu, int position, String choiceType) {

        Bundle bpager = new Bundle();
        bpager.putStringArray(ARG_SUBMENU, subMenu);
        bpager.putInt(ARG_POSITION, position);
        bpager.putString(ARG_CHOICE, choiceType);

        MainFramePagerFragment pager = new MainFramePagerFragment();
        pager.setArguments(bpager);

        return pager;
    }

    //***********************************************************************************************
    private void readBundle(Bundle bundle){
        if(bundle!=null){
            app = (CDA)getContext().getApplicationContext();
            subMenu = getArguments().getStringArray(ARG_SUBMENU);
            position = getArguments().getInt(ARG_POSITION);
            mChoiceType = getArguments().getString(ARG_CHOICE);
        }
    }

    //********************************** ON CREATE VIEW ********************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;
        readBundle(getArguments());

        switch (mChoiceType) {
            case ""+R.string.project:
                break;

            case ""+R.string.member:
                switch (position) {
                    case 0:
                        rootView = inflater.inflate(R.layout.fragment_members, container, false);
                        new MembersFragment(rootView, getContext());
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                break;

            case ""+R.string.cda:
                break;
        }

        return rootView;
    }

    //*********************************************************************************************
}
