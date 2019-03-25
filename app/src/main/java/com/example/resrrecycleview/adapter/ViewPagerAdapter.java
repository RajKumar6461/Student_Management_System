package com.example.resrrecycleview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.resrrecycleview.R;
import com.example.resrrecycleview.fragment.AddUpdateFragment;
import com.example.resrrecycleview.fragment.StudentListFragment;

public class ViewPagerAdapter  extends FragmentPagerAdapter {
    
    private final static int LIST_FRAGMENT=0;
    private final static int ADD_UPDATE_FRAGMENT=1;
    private final static int TOTAL_FRAGMENT=2;



    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case LIST_FRAGMENT:
                return new StudentListFragment(); //ChildFragment1 at position 0
            case ADD_UPDATE_FRAGMENT:
                return new AddUpdateFragment(); //ChildFragment2 at position 1
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_FRAGMENT; //two fragments
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case LIST_FRAGMENT:
                return ("Student List");
            case ADD_UPDATE_FRAGMENT:
                return "Add/Update";
        }
        return null;
    }

}