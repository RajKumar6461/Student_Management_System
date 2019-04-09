package com.example.resrrecycleview.fragment;


import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.resrrecycleview.R;
import com.example.resrrecycleview.broadcast.StudentBroadcastReceiver;
import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.dialog.GenerateDialog;
import com.example.resrrecycleview.model.Student;
import com.example.resrrecycleview.util.CommunicationFragments;
import com.example.resrrecycleview.util.SendCallBack;
import com.example.resrrecycleview.util.ValidUtil;
import java.util.ArrayList;


public class AddUpdateFragment extends Fragment implements SendCallBack {

    private EditText mEditTextFirstName;
    private EditText mEditTextLastName;
    private EditText mEditTextId;
    private Button mButtonAdd;
    private int selectButtonOperation=2;
    private String typeAction;
    private Bundle bundle;
    private Student editStudentDetail;
    private boolean errorHandling;
    private CommunicationFragments mListener;
    private ArrayList<Student> studentList=new ArrayList<>();
    private StudentBroadcastReceiver studentBroadcastReceiver;
    private View view;
    private Context mContext;
    private GenerateDialog generateDialog;

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
        view= inflater.inflate(R.layout.fragment_add_update, container, false);
        studentBroadcastReceiver = new StudentBroadcastReceiver();
        studentBroadcastReceiver.setCallBackSend((SendCallBack)this);
        bundle=new Bundle();
        bundle.putString(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD);
        generateDialog=new GenerateDialog(mContext,(SendCallBack)this);
        initValues();

        if(getArguments()!=null){
            bundle=getArguments();
            viewPlease(bundle);
        }
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorHandling=true;
                switch (selectButtonOperation){
                    case Constants.AddUpdateFragmentMember.REQUEST_CODE_EDIT:
                        editButtonOnClick();
                        break;
                    case Constants.AddUpdateFragmentMember.REQUEST_CODE_ADD:
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

        if (!ValidUtil.isValidName(fName)) {
            mEditTextFirstName.setError(getString(R.string.FirstNameError));
            errorHandling = false;
        }

        if (!ValidUtil.isValidName(lName)) {
            mEditTextLastName.setError(getString(R.string.LastNameError));
            errorHandling = false;
        }

        if (!ValidUtil.isValidId(sRollNo)) {
            mEditTextId.setError(getString(R.string.RollNoError));
            errorHandling = false;
        }

       else if (ValidUtil.isCheckValidId(studentList,sRollNo)) {
            mEditTextId.setError(getString(R.string.DifferentRollNo));
            errorHandling = false;
        }

        if (errorHandling) {

            Student student = new Student(fName.toUpperCase(),lName.toUpperCase(),sRollNo);
            bundle.putSerializable(Constants.STUDENT_DATA,student);
            generateDialog.generateAlertDialog(sRollNo,fName+" "+lName,Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD);
        }
    }

    /**
     * This function provide functionalities for Updation
     *get data from edittext then validate
     */
    private void editButtonOnClick(){
        if(mEditTextId.getText().toString().equals("")){
            Toast.makeText(mContext,"Data Not present",Toast.LENGTH_LONG).show();
            clearAllEditText();
            mListener.changeTabFromFragment();
        }
        else {
            String fName = mEditTextFirstName.getText().toString().trim();
            String lName = mEditTextLastName.getText().toString().trim();

            if (!ValidUtil.isValidName(fName)) {
                mEditTextFirstName.setError(getString(R.string.LastNameError));
                errorHandling = false;
            }

            if (!ValidUtil.isValidName(lName)) {
                mEditTextLastName.setError(getString(R.string.LastNameError));
                errorHandling = false;
            }

            if (errorHandling) {
                bundle.putString(Constants.FIRST_NAME, fName);
                bundle.putString(Constants.LAST_NAME, lName);
                generateDialog.generateAlertDialog(editStudentDetail.getmId(), fName + " " + lName, Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT);
            }
        }
    }

    /**
     * This method used to clear Edit Text data
     */

    private void clearAllEditText(){
        mEditTextFirstName.getText().clear();
        mEditTextLastName.setText("");
        mEditTextId.setText("");
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
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(Constants.FILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(studentBroadcastReceiver,intentFilter);

    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(studentBroadcastReceiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }

    /**
     *This method is called from launcher activity for cummunication and data send
     * Check the action from launcher and set button on click operation
     *
     * @param bundleNew of bundle type having data from fragment
     */
    public void update(Bundle bundleNew){
        bundle=bundleNew;
            typeAction=bundle.getString(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY);
            switch (typeAction){
                case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD:
                    selectButtonOperation=Constants.AddUpdateFragmentMember.REQUEST_CODE_ADD;
                    studentList=(ArrayList<Student>) bundle.getSerializable(Constants.STUDENT_DATA_List);
                    mButtonAdd.setText(getString(R.string.btn_add_text));
                    mEditTextId.setEnabled(true);
                    break;

                case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT:
                    selectButtonOperation=Constants.AddUpdateFragmentMember.REQUEST_CODE_EDIT;
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
     * This method used to view data of Student
     * @param b of bundle type get data from 1st activity for view
     */
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

    @Override
    public void callBackAsync(String toastData) {
        Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(Constants.VIBRATE_MILI_SECOND);
        Toast.makeText(mContext,toastData,Toast.LENGTH_LONG).show();
        clearAllEditText();
        mListener.communication(bundle);
    }

}
