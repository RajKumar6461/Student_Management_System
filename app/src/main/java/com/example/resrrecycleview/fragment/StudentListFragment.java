package com.example.resrrecycleview.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resrrecycleview.R;
import com.example.resrrecycleview.activity.MainActivity;
import com.example.resrrecycleview.activity.StudentDataActivity;
import com.example.resrrecycleview.adapter.StudentAdapter;
import com.example.resrrecycleview.asynctask.BackProcessGetData;
import com.example.resrrecycleview.comparator.SortByName;
import com.example.resrrecycleview.comparator.SortByRollNo;
import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.database.StudentDatabaseHelper;
import com.example.resrrecycleview.model.Student;
import com.example.resrrecycleview.util.CommunicationFragments;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class StudentListFragment extends Fragment  {

    public static final int GRID_SPAN=2;
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

    private CommunicationFragments mListener;
    private View view;
    private Context mContext;
    private int possitionInsertStudent = 0;

    public StudentListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStudentDatabaseHelper=new StudentDatabaseHelper(mContext);
        mStudent=mStudentDatabaseHelper.getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_student_list, container, false);
        // mStudent=mStudentDatabaseHelper.getData();
        //For initializing values and set up Adapter
        initValues();
        //(new BackProcessGetData(mContext,mContext)).execute();


        //set Onclick on Add btn

        setHasOptionsMenu(true);
        mButtonAddCuurent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something when the corky2 is clicked
                Bundle bundle=new Bundle();
                bundle.putSerializable(Constants.STUDENT_DATA_List,mStudent);
                bundle.putString(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD);
                mListener.communication(bundle);
            }
        });
        return view;
    }


    private void onClickOfAdapter(final int pos){

        // String Array for Alert Dialog Choice


        final AlertDialog.Builder mBuilder=new AlertDialog.Builder(mContext);
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
                        positionEditStudentData=pos;
                        viewSendAnotherActivity();
                        break;
                    case EDIT:
                        Bundle bundle=new Bundle();
                        bundle.putString(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT);
                        bundle.putSerializable(Constants.STUDENT_DATA,mStudent.get(pos));
                        positionEditStudentData=pos;
                        mListener.communication(bundle);
                        //set adapter position of item Clicked
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
        mTextView = (TextView) view.findViewById(R.id.no_data);
        mButtonAddCuurent = view.findViewById(R.id.add_data);
        mRecyclerView = (RecyclerView)view. findViewById(R.id.recycle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        // setting ArrayList to Adapter of RecycleView
        mAdapter = new StudentAdapter(mStudent);
        mRecyclerView.setAdapter(mAdapter);
        if (mStudent.size() == Constants.CHECK_ARRAYLIST_SIZE_ZERO||mStudent==null) {
            mTextView.setText(NO_DATA);
        }

        // settting the onclick to Adapter
        mAdapter.setOnClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {

                onClickOfAdapter(position);
            }
        });

    }


    private void deleteDialog(final int pos){
        //setting new Dialog box for asking ok/cencel
        AlertDialog.Builder builderForAlert=new AlertDialog.Builder(mContext);
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
               // mStudentDatabaseHelper=new StudentDatabaseHelper(mContext);
               // mStudentDatabaseHelper.deleteContact(mStudent.get(pos).getmId());
                mStudentDatabaseHelper.deleteContact(mStudent.get(pos).getmId());
                mStudent.remove(pos);
                mAdapter.notifyDataSetChanged();

                //condition for checking ArrayList size is 0 if yes then setting back textView to Vissible
                if(mStudent.size()==Constants.CHECK_ARRAYLIST_SIZE_ZERO && mTextView.getVisibility()==View.GONE||mStudent==null){
                    mTextView.setVisibility(View.VISIBLE);
                }
                Toast.makeText(mContext,Constants.DELETE_TOAST,Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog newDialog1=builderForAlert.create();
        newDialog1.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        if (context instanceof CommunicationFragments) {
            mListener = (CommunicationFragments) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getString(R.string.interface_implement_message));
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }

    public void update(Bundle bundleFrom2Fragment){

        switch(bundleFrom2Fragment.getString(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY)){
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD:

                    Student sudStudent = (Student) bundleFrom2Fragment.getSerializable(Constants.STUDENT_DATA);
                    //mStudentDatabaseHelper.addData(sudStudent.getmId(),sudStudent.getmFirstName()+" "+sudStudent.getmLastName());
                    mStudent.add(possitionInsertStudent, sudStudent);
                   // mStudentDatabaseHelper.addData(sudStudent.getmId(),sudStudent.getmFirstName()+" "+sudStudent.getmLastName());

                    //As size of ArrayList>0 setting visibility of back textView to gone
                    if (mTextView.getVisibility() == View.VISIBLE) mTextView.setVisibility(View.GONE);
                    mAdapter.notifyItemInserted(possitionInsertStudent);
                    Toast.makeText(mContext,Constants.ADD_TOAST,Toast.LENGTH_SHORT).show();

                break;
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT:

                    String fName=bundleFrom2Fragment.getString(Constants.FIRST_NAME);
                    String lName=bundleFrom2Fragment.getString(Constants.LAST_NAME);

                    //get Object from itemClicked in RecycleView
                    Student suStudent=mStudent.get(positionEditStudentData);
                    suStudent.setFirstName(fName);
                    suStudent.setLastName(lName);
                    mAdapter.notifyItemChanged(positionEditStudentData);
                   // mStudentDatabaseHelper.update_name(suStudent.getmId(),fName+" "+lName);
                    //mStudentDatabaseHelper.update_name(fName+" "+lName,suStudent.getmId());
                    Toast.makeText(mContext,Constants.UPDATE_TOAST,Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

/**
 * This method provide the fuctionalities after Menuitem selected
 *
 * @param item
 * @return
 */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            //This case used to Sort Student ArrayList by Name using Compatrator class
            case R.id.ByName:
                Toast.makeText(mContext,Constants.SORT_BY_NAME_TOAST,Toast.LENGTH_LONG).show();
                Collections.sort(mStudent,new SortByName());
                mAdapter.notifyDataSetChanged();
                return true;

            //This case used to Sort Student ArrayList by Roll No using Compatrator class
            case R.id.ByRollNo:
                Toast.makeText(mContext,Constants.SORT_BY_ROLL_NO_TOAST,Toast.LENGTH_LONG).show();
                Collections.sort(mStudent,new SortByRollNo());
                mAdapter.notifyDataSetChanged();
                return true;

            //This case used to change Linear Layout to Grid or vice versa of RecycleView
            case R.id.layoutSet:
                if(!toogleLayout) {
                    toogleLayout=true;
                    item.setIcon(R.drawable.ic_grid_on_white_24dp);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,GRID_SPAN));
                    Toast.makeText(mContext,Constants.GRID_LAYOUT_RECYCLER_VIEW,Toast.LENGTH_LONG).show();
                }else {
                    toogleLayout=false;
                    item.setIcon(R.drawable.ic_grid_off_white_24dp);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                    Toast.makeText(mContext,Constants.LINEAR_LAYOUT_RECYCLER_VIEW,Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void viewSendAnotherActivity(){
        Intent intent=new Intent(mContext,StudentDataActivity.class);
        intent.putExtra(Constants.STUDENT_DATA,mStudent.get(positionEditStudentData));
        intent.putExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_VIEW);
        mContext.startActivity(intent);
    }


}
