package com.example.resrrecycleview.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.resrrecycleview.asynctask.BackSetUpdateData;
import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.R;
import com.example.resrrecycleview.model.Student;
import com.example.resrrecycleview.service.SetUpdateDbIntentService;
import com.example.resrrecycleview.service.SetUpdateService;
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
public class StudentDataActivity extends AppCompatActivity implements BackSetUpdateData.SendData {

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
    private int selectButtonOperation=0;
    private String typeAction;
    private Intent mIntentForOtherActivity;
    private Bundle bundle;
    private Student editStudentDetail;
    private boolean errorHandling;
    private int select;
    private StudentBroadcastReceiver studentBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        studentBroadcastReceiver=new StudentBroadcastReceiver();

        //setting id to edittext and button
        initValues();

        //get data from intent from another activity
        bundle=getIntent().getExtras();
        typeAction=bundle.getString(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY);
        setButtonOperation();


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

    private void setButtonOperation(){
        //check for type action if add then set button operation and get data
        switch (typeAction){
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD:
                selectButtonOperation=REQUEST_CODE_ADD;
                break;
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_VIEW:
                Student viewStudentDetail=(Student) bundle.getSerializable(Constants.STUDENT_DATA);
                mEditTextFirstName.setText(viewStudentDetail.getmFirstName().toUpperCase());
                mEditTextLastName.setText(viewStudentDetail.getmLastName().toUpperCase());
                mEditTextId.setText(viewStudentDetail.getmId().toUpperCase());
                mEditTextFirstName.setEnabled(false);
                mEditTextLastName.setEnabled(false);
                mEditTextId.setEnabled(false);
                mButtonAdd.setVisibility(View.GONE);
                break;
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT:
                selectButtonOperation=REQUEST_CODE_EDIT;
                mButtonAdd.setText(Constants.BTN_CHANGE_TEXT_UPDATE);
                editStudentDetail=(Student) bundle.getSerializable(Constants.STUDENT_DATA);
                mEditTextFirstName.setText(editStudentDetail.getmFirstName().toUpperCase());
                mEditTextLastName.setText(editStudentDetail.getmLastName().toUpperCase());
                mEditTextId.setText(editStudentDetail.getmId().toUpperCase());
                mEditTextId.setEnabled(false);
                break;
        }
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
            else if (ValidUtil.isCheckValidId((ArrayList<Student>) bundle.getSerializable(Constants.STUDENT_DATA),sRollNo)) {
                mEditTextId.setError(getString(R.string.DifferentRollNo));
                errorHandling = false;
            }
            //check if error is present or not
            if (errorHandling) {

                Student student = new Student(fName.toUpperCase(),lName.toUpperCase(),sRollNo);

                mIntentForOtherActivity=createIntent(MainActivity.class);
                mIntentForOtherActivity.putExtra(Constants.STUDENT_DATA, student);
                setResult(RESULT_OK, mIntentForOtherActivity);
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
            mIntentForOtherActivity=createIntent(MainActivity.class);
            mIntentForOtherActivity.putExtra(Constants.FIRST_NAME,fName);
            mIntentForOtherActivity.putExtra(Constants.LAST_NAME,lName);
            setResult(RESULT_OK,mIntentForOtherActivity);
            generateAlertDialog(editStudentDetail.getmId(),fName+" "+lName,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT);
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

    /**
     * This method used to generate Dialog Box having 3 choose for Background thread
     *
     * @param rollNo of String type
     * @param fullName of String type
     * @param typeOperation of String type used to check operation
     */

    private void generateAlertDialog(final String rollNo, final String fullName, final String typeOperation){

        final AlertDialog.Builder mBuilder=new AlertDialog.Builder(StudentDataActivity.this);
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
                       (new BackSetUpdateData(StudentDataActivity.this,StudentDataActivity.this)).execute(typeOperation,rollNo,fullName);
                        break;
                    case SERVICE:
                        Intent service=new Intent(StudentDataActivity.this, SetUpdateService.class);
                        startServiceFromDialog(service,rollNo,fullName,typeOperation);

                        break;
                    case INTENT_SERVICE:
                        Intent intentForService=new Intent(StudentDataActivity.this, SetUpdateDbIntentService.class);
                        startServiceFromDialog(intentForService,rollNo,fullName,typeOperation);


                        break;
                }
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
    private void startServiceFromDialog(Intent service,final String rollNo, final String fullName, final String typeOperation){
        service.putExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,typeOperation);
        service.putExtra(Constants.ROLL_NO,rollNo);
        service.putExtra(Constants.STUDENT_FULL_NAME,fullName);
        startService(service);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Constants.FILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(this).registerReceiver(studentBroadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(studentBroadcastReceiver);
    }

    @Override
    public void callBack(String str) {
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
        finish();
    }

    public class StudentBroadcastReceiver extends BroadcastReceiver {
        private Bundle sendBundleFromThis;

        @Override
        public void onReceive(Context context, Intent intent) {

            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(Constants.VIBRATE_MILI_SECOND);
            Toast.makeText(context, intent.getStringExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY), Toast.LENGTH_SHORT).show();
            finish();

        }

    }
}
