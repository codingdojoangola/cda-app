package com.codingdojoangola.ui.main;

//:::::::::::::::: Android imports
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


//:::::::::::::::: Import from third parties (com, junit, net, org)

//:::::::::::::::: Java and javax

//:::::::::::::::: Same project import


public class MainFrameFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private  String[] subMenu ;
    private String mChoiceType;


    //********************************** Construct *****************************************************
    public MainFrameFragmentPagerAdapter(FragmentManager fm, Context context, String [] subMenu, String choiceType) {
        super(fm);

        this.subMenu = subMenu;
        this.mChoiceType = choiceType;
    }

    //******************************************************************
    @Override
    public int getCount() {
        return subMenu.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return subMenu[position % subMenu.length];
    }

    //*********************************************************************
    @Override
    public Fragment getItem(int position) {

        //Log.e("FrameAdapterPager", "SubMenu -> "+subMenu[position]);
        return MainFramePagerFragment.newInstance(subMenu, position, mChoiceType);
    }

    //**********************************************************************************************
}
