package com.example.resrrecycleview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.R;
import com.example.resrrecycleview.model.Student;
import com.example.resrrecycleview.util.ValidUtil;

import java.util.ArrayList;

/**
 * This Activity is Used to take Details from user to Add
 * Also view Details in this activity
 * Also used to Edit saved Data
 *
 * 3 Edittext fields
 * 1 button to perform functions on data entered in edittext
 *
 */
public class StudentDataActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD=2;
    public static final int REQUEST_CODE_EDIT=1;
    private EditText mEditTextFirstName;
    private EditText mEditTextLastName;
    private EditText mEditTextId;
    private Button mButtonAdd;
    private int selectButtonOperation=0;
    private String typeAction;
    private Intent mIntentForOtherActivity;
    private Bundle bundle;
    private Student editStudentDetail;
    private boolean errorHandling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //setting id to edittext and button
        initValues();

        //get data from intent from another activity
        bundle=getIntent().getExtras();
        typeAction=bundle.getString(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY);

        //check for type action if add then set button operation and get data
        if(typeAction.equals(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD)){
            selectButtonOperation=REQUEST_CODE_ADD;
        }
        //check for type action if view then set get data from bundle and set to edittext and remove button
        else if(typeAction.equals(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_VIEW))
        {

            Student viewStudentDetail=(Student) bundle.getSerializable(Constants.STUDENT_DATA);
            mEditTextFirstName.setText(viewStudentDetail.getmFirstName().toUpperCase());
            mEditTextLastName.setText(viewStudentDetail.getmLastName().toUpperCase());
            mEditTextId.setText(viewStudentDetail.getmId().toUpperCase());
            mEditTextFirstName.setEnabled(false);
            mEditTextLastName.setEnabled(false);
            mEditTextId.setEnabled(false);
            mButtonAdd.setVisibility(View.GONE);
        }

        //check for type action if Edit then set button operation and get data set the edit text
        else if(typeAction.equals(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT))
        {
            selectButtonOperation=REQUEST_CODE_EDIT;
            mButtonAdd.setText(Constants.BTN_CHANGE_TEXT_UPDATE);
            editStudentDetail=(Student) bundle.getSerializable(Constants.STUDENT_DATA);
            mEditTextFirstName.setText(editStudentDetail.getmFirstName().toUpperCase());
            mEditTextLastName.setText(editStudentDetail.getmLastName().toUpperCase());
            mEditTextId.setText(editStudentDetail.getmId().toUpperCase());
            mEditTextId.setEnabled(false);
        }

        //set click listener to button
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //This is used for validation purpose if it remains true then only data is added and update
                errorHandling=true;
                //check for button operation if 1 then operation of add
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

    }

    private void initValues(){
        mEditTextFirstName=(EditText) findViewById(R.id.editFirstName);
        mEditTextLastName=(EditText) findViewById(R.id.editLastName);
        mEditTextId=(EditText)findViewById(R.id.edit_Id);
        mButtonAdd=(Button) findViewById(R.id.add);
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
                mEditTextFirstName.setError("Enter Valid Name");
                errorHandling = false;
            }
            // validation for last name check used set error to edit text
            if (!ValidUtil.isValidName(lName)) {
                mEditTextLastName.setError("Enter Valid Name");
                errorHandling = false;
            }
            // validation for Roll No check used set error to edit text
            if (!ValidUtil.isValidId(sRollNo)) {
                mEditTextId.setError("Enter Valid Roll_No");
                errorHandling = false;
            }
            //check duplicte Roll No
            else if (ValidUtil.isCheckValidId((ArrayList<Student>) bundle.getSerializable(Constants.STUDENT_DATA),sRollNo)) {
                mEditTextId.setError("Enter Different Roll_NO");
                errorHandling = false;
            }
            //check if error is present or not
            if (errorHandling) {

                Student student = new Student();
                student.setFirstName(fName.toUpperCase());
                student.setLastName(lName.toUpperCase());
                student.setId(sRollNo);
                mIntentForOtherActivity=createIntent(MainActivity.class);
                mIntentForOtherActivity.putExtra(Constants.STUDENT_DATA, student);
                setResult(RESULT_OK, mIntentForOtherActivity);
                finish();
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
            mEditTextFirstName.setError("Enter Valid Name");
            errorHandling = false;
        }
        // validation for last name check used set error to edit text
        if (!ValidUtil.isValidName(lName)) {
            mEditTextLastName.setError("Enter Valid Name");
            errorHandling = false;
        }
        //check if error is present or not
        if (errorHandling) {
            mIntentForOtherActivity=createIntent(MainActivity.class);
            mIntentForOtherActivity.putExtra(Constants.FIRST_NAME,fName.toUpperCase());
            mIntentForOtherActivity.putExtra(Constants.LAST_NAME,lName.toUpperCase());
            setResult(RESULT_OK,mIntentForOtherActivity);
            finish();
        }
    }

    private Intent createIntent(Class<?> studentDataActivityClass){
        Intent intent=new Intent(this,studentDataActivityClass);
        return intent;
    }

    private void setTextOfEditText(Student student){
        mEditTextFirstName.setText(student.getmFirstName().toUpperCase());
        mEditTextLastName.setText(student.getmLastName().toUpperCase());
        mEditTextId.setText(student.getmId().toUpperCase());
    }

}
