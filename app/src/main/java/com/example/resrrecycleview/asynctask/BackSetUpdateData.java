package com.example.resrrecycleview.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.database.StudentDatabaseHelper;
import com.example.resrrecycleview.util.SendCallBack;

public class BackSetUpdateData extends AsyncTask<String,Void, String> {
    private Context mContext;
    private SendCallBack sendCallbackAsynk;
    public BackSetUpdateData(Context mContext,SendCallBack sendCallbackAsynk) {
        this.mContext=mContext;
        this.sendCallbackAsynk=sendCallbackAsynk;
    }

    @Override
    protected void onPreExecute()  {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String out) {
        sendCallbackAsynk.callBackAsync(out);
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
        String returnToast=null;
        Log.d("in", "doInBackground: ");
        switch (method){
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD:
                dbHelper.addData(roll_no,full_name);
                returnToast=Constants.ADD_TOAST;
                break;
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT:
                dbHelper.update_name(roll_no,full_name);
                returnToast=Constants.UPDATE_TOAST;
                break;
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_DELETE:
                dbHelper.deleteContact(roll_no);
                returnToast=Constants.DELETE_TOAST;
                break;
        }
        return returnToast;

    }
}

