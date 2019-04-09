package com.example.resrrecycleview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.fragment.AddUpdateFragment;
import com.example.resrrecycleview.fragment.StudentListFragment;

public class ViewPagerAdapter  extends FragmentPagerAdapter {
    




    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case Constants
                    .ViewPagerAdapterMember.LIST_FRAGMENT:
                return new StudentListFragment(); //ChildFragment1 at position 0
            case Constants.ViewPagerAdapterMember.ADD_UPDATE_FRAGMENT:
                return new AddUpdateFragment(); //ChildFragment2 at position 1
        }
        return null;
    }

    @Override
    public int getCount() {
        return Constants.ViewPagerAdapterMember.TOTAL_FRAGMENT; //two fragments
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case Constants
                    .ViewPagerAdapterMember.LIST_FRAGMENT:
                return Constants.ViewPagerAdapterMember.STUDENT_LIST_TAB_TITLE;
            case Constants.ViewPagerAdapterMember.ADD_UPDATE_FRAGMENT:
                return Constants.ViewPagerAdapterMember.ADD_UPDATE_TAB_TITLE;
        }
        return null;
    }

}