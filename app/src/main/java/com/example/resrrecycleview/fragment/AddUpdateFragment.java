package com.example.resrrecycleview.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.resrrecycleview.R;
import com.example.resrrecycleview.activity.StudentDataActivity;
import com.example.resrrecycleview.asynctask.BackSetUpdateData;
import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.database.StudentDatabaseHelper;
import com.example.resrrecycleview.model.Student;
import com.example.resrrecycleview.service.SetUpdateDbIntentService;
import com.example.resrrecycleview.service.SetUpdateService;
import com.example.resrrecycleview.util.CommunicationFragments;
import com.example.resrrecycleview.util.ValidUtil;
import java.util.ArrayList;


public class AddUpdateFragment extends Fragment {



    public static final int REQUEST_CODE_ADD=2;
    public static final int REQUEST_CODE_EDIT=1;
    public static final int ASYNC_TASK=0;
    public static final int SERVICE=1;
    public static final int INTENT_SERVICE=2;
    public final static String[] ITEM_DAILOG={"AsyncTask" , "Service" , "Intent Service"};
    private EditText mEditTextFirstName;
    private EditText mEditTextLastName;
    private EditText mEditTextId;
    private Button mButtonAdd;
    private int selectButtonOperation=2;
    private String typeAction;
    private Bundle bundle;
    private Student editStudentDetail;
    private boolean errorHandling;
    private int select;
    private StudentDatabaseHelper studentDatabaseHelper;
    private CommunicationFragments mListener;
    private ArrayList<Student> studentList=new ArrayList<>();

    private View view;
    private Context mContext;

    public AddUpdateFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_add_update, container, false);
        studentDatabaseHelper=new StudentDatabaseHelper(mContext);
        bundle=new Bundle();
        bundle.putString(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD);

        initValues();

        if(getArguments()!=null){
            bundle=getArguments();
            viewPlease(bundle);
        }
        //set click listener to button
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorHandling=true;
                switch (selectButtonOperation){
                    case REQUEST_CODE_EDIT:
                        editButtonOnClick();
                        break;
                    case REQUEST_CODE_ADD:
                        addButtonOnClick();
                        break;
                }
            }
        });
        return view;
    }

    private void initValues(){
        mEditTextFirstName=(EditText)view.findViewById(R.id.editFirstName);
        mEditTextLastName=(EditText)view.findViewById(R.id.editLastName);
        mEditTextId=(EditText)view.findViewById(R.id.edit_Id);
        mButtonAdd=(Button)view.findViewById(R.id.add);
    }


    /**
     * This function provide functionalities of add button
     *get data from edittext then validate
     */
    private void addButtonOnClick(){

        String fName = mEditTextFirstName.getText().toString().trim();
        String lName = mEditTextLastName.getText().toString().trim();
        String sRollNo = mEditTextId.getText().toString().trim();

        // validation for first name check used set error to edit text
        if (!ValidUtil.isValidName(fName)) {
            mEditTextFirstName.setError(getString(R.string.FirstNameError));
            errorHandling = false;
        }
        // validation for last name check used set error to edit text
        if (!ValidUtil.isValidName(lName)) {
            mEditTextLastName.setError(getString(R.string.LastNameError));
            errorHandling = false;
        }
        // validation for Roll No check used set error to edit text
        if (!ValidUtil.isValidId(sRollNo)) {
            mEditTextId.setError(getString(R.string.RollNoError));
            errorHandling = false;
        }
        //check duplicte Roll No
       else if (ValidUtil.isCheckValidId(studentList,sRollNo)) {
            mEditTextId.setError(getString(R.string.DifferentRollNo));
            errorHandling = false;
        }
        //check if error is present or not
        if (errorHandling) {

            Student student = new Student(fName.toUpperCase(),lName.toUpperCase(),sRollNo);
            bundle.putSerializable(Constants.STUDENT_DATA,student);
            generateAlertDialog(sRollNo,fName+" "+lName,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD);

        }
    }

    /**
     * This function provide functionalities for Updation
     *get data from edittext then validate
     */
    private void editButtonOnClick(){
        String fName = mEditTextFirstName.getText().toString().trim();
        String lName = mEditTextLastName.getText().toString().trim();

        // validation for first name check used set error to edit text
        if (!ValidUtil.isValidName(fName)) {
            mEditTextFirstName.setError(getString(R.string.LastNameError));
            errorHandling = false;
        }
        // validation for last name check used set error to edit text
        if (!ValidUtil.isValidName(lName)) {
            mEditTextLastName.setError(getString(R.string.LastNameError));
            errorHandling = false;
        }
        //check if error is present or not
        if (errorHandling) {
            bundle.putString(Constants.FIRST_NAME,fName);
            bundle.putString(Constants.LAST_NAME,lName);
            generateAlertDialog(editStudentDetail.getmId(),fName+" "+lName,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT);
        }
    }



    private void setTextOfEditText(Student student){
        mEditTextFirstName.setText(student.getmFirstName().toUpperCase());
        mEditTextLastName.setText(student.getmLastName().toUpperCase());
        mEditTextId.setText(student.getmId().toUpperCase());
    }

    /**
     * This method used to generate Dialog Box having 3 choose for Background thread
     *
     * @param rollNo of String type
     * @param fullName of String type
     * @param typeOperation of String type used to check operation
     */

    private void generateAlertDialog(final String rollNo, final String fullName, final String typeOperation){

        final AlertDialog.Builder mBuilder=new AlertDialog.Builder(mContext);
        if(selectButtonOperation==REQUEST_CODE_EDIT)
            mBuilder.setTitle(R.string.UpdateDialogBoxTitle);
        else
            mBuilder.setTitle(R.string.AddDialogBoxTitle);

        //setting SingleChoiceItem onClick
        mBuilder.setSingleChoiceItems(ITEM_DAILOG, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //set which choice is selected
                select=which;
                dialog.dismiss();

                switch (select){
                    case ASYNC_TASK:
                       // (new BackSetUpdateData(mContext)).execute(typeOperation,rollNo,fullName);
                        (new BackSetUpdateData(mContext)).execute(typeOperation,rollNo,fullName);
                        break;
                    case SERVICE:
                        Intent service=new Intent(mContext, SetUpdateService.class);
                        startServiceWork(service,rollNo,fullName,typeOperation);
                        break;
                    case INTENT_SERVICE:
                       Intent intentForService=new Intent(mContext, SetUpdateDbIntentService.class);
                        startServiceWork(intentForService,rollNo,fullName,typeOperation);
                        break;
                }
                mListener.communication(bundle);

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
    private void startServiceWork(Intent service,final String rollNo, final String fullName, final String typeOperation){
        service.putExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,typeOperation);
        service.putExtra(Constants.ROLL_NO,rollNo);
        service.putExtra(Constants.STUDENT_FULL_NAME,fullName);
        mContext.startService(service);
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

    public void update(Bundle bundleNew){
        bundle=bundleNew;
            typeAction=bundle.getString(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY);
            switch (typeAction){
                case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD:
                    selectButtonOperation=REQUEST_CODE_ADD;
                    studentList=(ArrayList<Student>) bundle.getSerializable(Constants.STUDENT_DATA_List);
                    mButtonAdd.setText(getString(R.string.btn_add_text));
                    mEditTextId.setEnabled(true);

                    break;
                case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT:
                    selectButtonOperation=REQUEST_CODE_EDIT;
                    mButtonAdd.setText(Constants.BTN_CHANGE_TEXT_UPDATE);
                    editStudentDetail=(Student) bundle.getSerializable(Constants.STUDENT_DATA);
                    mEditTextFirstName.setText(editStudentDetail.getmFirstName().toUpperCase());
                    mEditTextLastName.setText(editStudentDetail.getmLastName().toUpperCase());
                    mEditTextId.setText(editStudentDetail.getmId().toUpperCase());
                    mEditTextId.setEnabled(false);
                    //mButtonAdd.setVisibility(View.VISIBLE);
                    break;

            }
    }
    private void viewPlease(Bundle b){
        editStudentDetail=(Student) b.getSerializable(Constants.STUDENT_DATA);
        mEditTextFirstName.setText(editStudentDetail.getmFirstName().toUpperCase());
        mEditTextLastName.setText(editStudentDetail.getmLastName().toUpperCase());
        mEditTextId.setText(editStudentDetail.getmId().toUpperCase());
        mEditTextId.setEnabled(false);
        mEditTextFirstName.setEnabled(false);
        mEditTextLastName.setEnabled(false);
        mButtonAdd.setVisibility(View.GONE);
    }

}
