package com.codingdojoangola.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FrameAdapterPager extends FragmentStatePagerAdapter {

    private  String[] subMenu ;


    //********************************** Construct *****************************************************
    public FrameAdapterPager(FragmentManager fm, Context context, String [] subMenu) {
        super(fm);

        this.subMenu = subMenu;
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
        return FrameFragmentPager.newInstance(subMenu, position);
    }

    //**********************************************************************************************
}
