package com.example.resrrecycleview.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.database.StudentDatabaseHelper;

public class SetUpdateDbIntentService extends IntentService {

    private static final String SUPER_RETURN="BackgroundIntentService";
    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SetUpdateDbIntentService() {
        super(SUPER_RETURN);

    }

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        StudentDatabaseHelper studentDataBaseHelper=new StudentDatabaseHelper(this);

        if(intent.getStringExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY).equals(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD)){
            studentDataBaseHelper.addData(intent.getStringExtra(Constants.ROLL_NO),intent.getStringExtra(Constants.STUDENT_FULL_NAME));
        }else if(intent.getStringExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY).equals(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT)){
            studentDataBaseHelper.update_name(intent.getStringExtra(Constants.ROLL_NO),intent.getStringExtra(Constants.STUDENT_FULL_NAME));
        }

        intent.setAction(Constants.FILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }
}