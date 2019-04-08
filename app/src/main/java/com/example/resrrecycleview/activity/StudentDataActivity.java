package com.example.resrrecycleview.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.resrrecycleview.R;
import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.fragment.AddUpdateFragment;
import com.example.resrrecycleview.util.CommunicationFragments;

/**
 * This Activity is Used to take Details from user to Add
 * Also view Details in this activity
 * Also used to Edit saved Data
 *
 * 3 Edittext fields
 * 1 button to perform functions on data entered in edittext
 *
 */
public class StudentDataActivity extends AppCompatActivity implements CommunicationFragments {
    private AddUpdateFragment mAddUpdateFragment;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bundle=getIntent().getExtras();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        mAddUpdateFragment=new AddUpdateFragment();
        fragmentTransaction.add(R.id.frag_container,mAddUpdateFragment, Constants.MESSAGE_TRANSACTION);
        fragmentTransaction.commit();
        mAddUpdateFragment.setArguments(bundle);
    }

    @Override
    public void communication(Bundle bundle) {

    }

    @Override
    public void changeTabFromFragment() {

    }
}
