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

public class MainActivity extends AppCompatActivity implements CommunicationFragments
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

    public void changeTab() {

        if(mViewPager.getCurrentItem()==0)
        {
           mViewPager.setCurrentItem(1);
        }

        else if(mViewPager.getCurrentItem() == 1)
        {
            mViewPager.setCurrentItem(0);
        }



    }
    @Override
    public void communication(Bundle bundle) {
        if(mViewPager.getCurrentItem()==0){
            String tag = "android:switcher:" + R.id.view_pager + ":" + 1;
            AddUpdateFragment f = (AddUpdateFragment) getSupportFragmentManager().findFragmentByTag(tag);
            f.update(bundle);
            changeTab();
        }else if(mViewPager.getCurrentItem()==1){
            String tag = "android:switcher:" + R.id.view_pager + ":" + 0;
            StudentListFragment f = (StudentListFragment) getSupportFragmentManager().findFragmentByTag(tag);
            f.update(bundle);
            changeTab();
        }
    }
}
/*
    public static final int VIEW=0;
    public static final int EDIT=1;
    public static final int DELETE=2;
    public static final int REQUEST_CODE_ADD=2;
    public static final int REQUEST_CODE_EDIT=1;
    public final static String[] itemDialog={"VIEW" , "EDIT" , "DELETE"};
    private static final String NO_DATA="NO STUDENT ADDED";
    private ArrayList<Student> mStudent=new ArrayList<>();
    private Button mButtonAddCuurent;
    private StudentAdapter mAdapter;
    private TextView mTextView;
    private Intent mIntentForStudentDetailActivity;
    private int selectItem=-1;
    private int positionEditStudentData;
    private RecyclerView mRecyclerView;
    private boolean toogleLayout=false;
    private StudentDatabaseHelper mStudentDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // mStudent=mStudentDatabaseHelper.getData();
        //For initializing values and set up Adapter
        initValues();
        (new BackProcessGetData(this,this)).execute();


        //set Onclick on Add btn

        mButtonAddCuurent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // do something when the corky2 is clicked

                    mIntentForStudentDetailActivity=createIntent(StudentDataActivity.class);
                  //  Intent  = new Intent(MainActivity.this, StudentDataActivity.class);

                    //ArrayList is send for Validation Purpose
                    mIntentForStudentDetailActivity.putExtra(Constants.STUDENT_DATA,mStudent);

                    //set Action Performed
                    mIntentForStudentDetailActivity.putExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD);
                    startActivityForResult(mIntentForStudentDetailActivity, REQUEST_CODE_ADD);
                }
            });
    }

    /**
     * Override method of startActivityForResult
     *
     * In this method result of 2 startActivityForResult
     * one for Adding new object in ArrayList data from another activity send from Add btn
     * second for Update the existing Object data from Student ArrayList
     *
     * @param requestCode of int type describe the from which startActivityForResult belongs to
     * @param resultCode of int type describe the result state
     * @param data of Intent type used to get data from another activity
     */
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch(requestCode){
                case REQUEST_CODE_ADD:
                    if(resultCode==RESULT_OK) {

                        //Add Object in Student ArrayList at possition
                        int possitionInsertStudent = 0;
                        //get data from another Activity
                        Student sudStudent = (Student) data.getSerializableExtra(Constants.STUDENT_DATA);
                        //mStudentDatabaseHelper.addData(sudStudent.getmId(),sudStudent.getmFirstName()+" "+sudStudent.getmLastName());
                        mStudent.add(possitionInsertStudent, sudStudent);

                        //As size of ArrayList>0 setting visibility of back textView to gone
                        if (mTextView.getVisibility() == View.VISIBLE) mTextView.setVisibility(View.GONE);
                        mAdapter.notifyItemInserted(possitionInsertStudent);
                        Toast.makeText(this,Constants.ADD_TOAST,Toast.LENGTH_SHORT).show();
                    }
                    break;
                case REQUEST_CODE_EDIT:
                    if(resultCode==RESULT_OK){
                        String fName=data.getStringExtra(Constants.FIRST_NAME);
                        String lName=data.getStringExtra(Constants.LAST_NAME);

                        //get Object from itemClicked in RecycleView
                        Student suStudent=mStudent.get(positionEditStudentData);
                        suStudent.setFirstName(fName);
                        suStudent.setLastName(lName);
                        mAdapter.notifyItemChanged(positionEditStudentData);
                        //mStudentDatabaseHelper.update_name(fName+" "+lName,suStudent.getmId());
                        Toast.makeText(this,Constants.UPDATE_TOAST,Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }


    /**
     * this Overriden method create the menu on toolbar and infalte the xml file
     *
     * @param menu of Menu type
     * @return true after infalte the xml file
     */
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    /**
     * This method provide the fuctionalities after Menuitem selected
     *
     * @param item
     * @return
     */
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            //This case used to Sort Student ArrayList by Name using Compatrator class
            case R.id.ByName:
                Toast.makeText(MainActivity.this,Constants.SORT_BY_NAME_TOAST,Toast.LENGTH_LONG).show();
                Collections.sort(mStudent,new SortByName());
                mAdapter.notifyDataSetChanged();
                return true;

            //This case used to Sort Student ArrayList by Roll No using Compatrator class
            case R.id.ByRollNo:
                Toast.makeText(MainActivity.this,Constants.SORT_BY_ROLL_NO_TOAST,Toast.LENGTH_LONG).show();
                Collections.sort(mStudent,new SortByRollNo());
                mAdapter.notifyDataSetChanged();
                return true;

            //This case used to change Linear Layout to Grid or vice versa of RecycleView
            case R.id.layoutSet:
                if(!toogleLayout) {
                    toogleLayout=true;
                    item.setIcon(R.drawable.ic_grid_on_white_24dp);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
                    Toast.makeText(MainActivity.this,Constants.GRID_LAYOUT_RECYCLER_VIEW,Toast.LENGTH_LONG).show();
                }else {
                    toogleLayout=false;
                    item.setIcon(R.drawable.ic_grid_off_white_24dp);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    Toast.makeText(MainActivity.this,Constants.LINEAR_LAYOUT_RECYCLER_VIEW,Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClickOfAdapter(final int pos){

        // String Array for Alert Dialog Choice


        final AlertDialog.Builder mBuilder=new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle(Constants.CHOOSE_ALERT_DIALOG_TITLE);

        //setting SingleChoiceItem onClick
        mBuilder.setSingleChoiceItems(itemDialog, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //set which choice is selected
                selectItem=which;

                dialog.dismiss();

                switch (selectItem){
                    case VIEW:
                        mIntentForStudentDetailActivity=createIntent(StudentDataActivity.class);

                        //sending Student Object on which Item is Clicked
                        mIntentForStudentDetailActivity.putExtra(Constants.STUDENT_DATA,mStudent.get(pos));
                        //sending Action Performed
                        mIntentForStudentDetailActivity.putExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_VIEW);
                        startActivity(mIntentForStudentDetailActivity);
                        break;
                    case EDIT:
                        //set adapter position of item Clicked
                        positionEditStudentData=pos;
                        mIntentForStudentDetailActivity=createIntent(StudentDataActivity.class);
                       // Intent mIntentForEdit=new Intent(MainActivity.this, StudentDataActivity.class);

                        //sending Student Object on which Item is Clicked
                        mIntentForStudentDetailActivity.putExtra(Constants.STUDENT_DATA,mStudent.get(pos));
                        //sending Action Performed
                        mIntentForStudentDetailActivity.putExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT);
                        startActivityForResult(mIntentForStudentDetailActivity,REQUEST_CODE_EDIT);

                        break;
                    case DELETE:
                        deleteDialog(pos);
                        break;
                }
                //condition for View if view choice is selected


            }
        });
        mBuilder.setNeutralButton(R.string.CencelNeuteralButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog mDialog=mBuilder.create();
        mDialog.show();
    }

    private void initValues(){
        mTextView = (TextView) findViewById(R.id.no_data);
        mButtonAddCuurent = findViewById(R.id.add_data);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // setting ArrayList to Adapter of RecycleView

    }

    private void deleteDialog(final int pos){
        //setting new Dialog box for asking ok/cencel
        AlertDialog.Builder builderForAlert=new AlertDialog.Builder(MainActivity.this);
        builderForAlert.setTitle(Constants.DELETE_ALERT_DIALOG_TITLE);
        builderForAlert.setNeutralButton(R.string.CencelNeuteralButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builderForAlert.setPositiveButton(R.string.OkButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Object is Deleted when pressed ok from Student ArrayList where item is clicked
                 mStudentDatabaseHelper=new StudentDatabaseHelper(MainActivity.this);
            mStudentDatabaseHelper.deleteContact(mStudent.get(pos).getmId());
                mStudent.remove(pos);
                mAdapter.notifyDataSetChanged();

                //condition for checking ArrayList size is 0 if yes then setting back textView to Vissible
                if(mStudent.size()==Constants.CHECK_ARRAYLIST_SIZE_ZERO && mTextView.getVisibility()==View.GONE){
                    mTextView.setVisibility(View.VISIBLE);
                }
                Toast.makeText(MainActivity.this,Constants.DELETE_TOAST,Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog newDialog1=builderForAlert.create();
        newDialog1.show();
    }

    private Intent createIntent(Class<?> studentDataActivityClass){
        Intent intent=new Intent(this,studentDataActivityClass);
        return intent;
    }

    /**
     * This method used to Communicate with UI thread
     * @param out ArrayList of student class from AsyncTask get list from DB
     */
/*
    @Override
    public void getOutput(ArrayList<Student> out) {
        mStudent=out;
        if (mStudent.size() == Constants.CHECK_ARRAYLIST_SIZE_ZERO||mStudent==null) {
            mTextView.setText(NO_DATA);
        }

        mAdapter = new StudentAdapter(mStudent);
        mRecyclerView.setAdapter(mAdapter);

        // settting the onclick to Adapter
        mAdapter.setOnClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {

                //
                onClickOfAdapter(position);
            }
        });

    }

    */

