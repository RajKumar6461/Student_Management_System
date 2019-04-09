package com.example.resrrecycleview.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resrrecycleview.adapter.ViewPagerAdapter;
import com.example.resrrecycleview.asynctask.BackProcessGetData;
import com.example.resrrecycleview.comparator.SortByName;
import com.example.resrrecycleview.comparator.SortByRollNo;
import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.R;
import com.example.resrrecycleview.database.StudentDatabaseHelper;
import com.example.resrrecycleview.fragment.AddUpdateFragment;
import com.example.resrrecycleview.fragment.StudentListFragment;
import com.example.resrrecycleview.model.Student;
import com.example.resrrecycleview.adapter.StudentAdapter;
import com.example.resrrecycleview.util.CommunicationFragments;

import java.util.ArrayList;
import java.util.Collections;

/**
 *This is Launcher Activity
 * In this Activity Recycle View is used to show student Name and Roll No
 *1 Button is at the Bottom To Add the Student (Open another activity )
 * Open Dialog Box on every ItemClick in Recycle View
 *
 * Three choice In Dialog Box
 * 1st to View Data (Open another activity )
 * 2nd to Edit Data (Open another activity )
 * 3rd to Delete Data
 *
 * @variables 9
 *
 * @author Raj Kumar Soni
 */

public class LauncherActivity extends AppCompatActivity implements CommunicationFragments
{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        mTabLayout = findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * This method used to chage tab or fragment
     * check condition for current fragment
     */

    public void changeTab() {

        if(mViewPager.getCurrentItem()==Constants.LIST_FRAGMENT)
        {
           mViewPager.setCurrentItem(Constants.ADD_UPDATE_FRAGMENT);
        }

        else if(mViewPager.getCurrentItem() == Constants.ADD_UPDATE_FRAGMENT)
        {
            mViewPager.setCurrentItem(Constants.LIST_FRAGMENT);
        }



    }
    @Override
    public void communication(Bundle bundle) {
        if(mViewPager.getCurrentItem()==Constants.LIST_FRAGMENT){
            String tag = getString(R.string.android_switcher) + R.id.view_pager + ":" + Constants.ADD_UPDATE_FRAGMENT;
            AddUpdateFragment f = (AddUpdateFragment) getSupportFragmentManager().findFragmentByTag(tag);
            f.update(bundle);
            changeTab();
        }else if(mViewPager.getCurrentItem()==Constants.ADD_UPDATE_FRAGMENT){
            String tag = getString(R.string.android_switcher) + R.id.view_pager + ":" + Constants.LIST_FRAGMENT;
            StudentListFragment f = (StudentListFragment) getSupportFragmentManager().findFragmentByTag(tag);
            f.update(bundle);
            changeTab();
        }
    }

    @Override
    public void changeTabFromFragment() {
        changeTab();
    }
}

