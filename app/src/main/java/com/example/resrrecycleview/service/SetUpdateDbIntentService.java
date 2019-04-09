package com.example.resrrecycleview.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.database.StudentDatabaseHelper;

public class SetUpdateDbIntentService extends IntentService {

    public SetUpdateDbIntentService() {
        super(Constants.SUPER_RETURN);

    }

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     *
     * @param intent Used to name the worker thread, important only for debugging.
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        StudentDatabaseHelper studentDataBaseHelper=new StudentDatabaseHelper(this);
        switch (intent.getStringExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY)){
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD:
                studentDataBaseHelper.addData(intent.getStringExtra(Constants.ROLL_NO),intent.getStringExtra(Constants.STUDENT_FULL_NAME));
                intent.setAction(Constants.FILTER_ACTION_KEY);
                break;
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT:
                studentDataBaseHelper.update_name(intent.getStringExtra(Constants.ROLL_NO),intent.getStringExtra(Constants.STUDENT_FULL_NAME));
                intent.setAction(Constants.FILTER_ACTION_KEY);
                break;
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_DELETE:
                studentDataBaseHelper.deleteContact(intent.getStringExtra(Constants.ROLL_NO));
                intent.setAction(Constants.FILTER_ACTION_KEY_DELETE);
                break;
        }
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }
}