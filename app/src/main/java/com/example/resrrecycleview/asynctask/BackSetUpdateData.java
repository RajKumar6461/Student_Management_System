package com.example.resrrecycleview.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.database.StudentDatabaseHelper;
import com.example.resrrecycleview.model.Student;

import java.util.ArrayList;

public class BackSetUpdateData extends AsyncTask<String,Void, String> {
    private Context mContext;


    public BackSetUpdateData(Context mContext) {
        this.mContext=mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String out) {
   Toast.makeText(mContext,out,Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    @Override
    protected String doInBackground(String... params) {
        StudentDatabaseHelper dbHelper=new StudentDatabaseHelper(mContext);
        String method=params[0];
        String roll_no=params[1];
        String full_name=params[2];
        switch (method){
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD:
                dbHelper.addData(roll_no,full_name);
                return Constants.ADD_TOAST;
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT:
                dbHelper.update_name(roll_no,full_name);
                return Constants.UPDATE_TOAST;
        }
        return null;

    }

}

