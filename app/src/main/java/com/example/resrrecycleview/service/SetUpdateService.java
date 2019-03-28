package com.example.resrrecycleview.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.database.StudentDatabaseHelper;

public class SetUpdateService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StudentDatabaseHelper studentDataBaseHelper=new StudentDatabaseHelper(this);

        if(intent.getStringExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY).equals(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD)){
            studentDataBaseHelper.addData(intent.getStringExtra(Constants.ROLL_NO),intent.getStringExtra(Constants.STUDENT_FULL_NAME));
        }else if(intent.getStringExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY).equals(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT)){
            studentDataBaseHelper.update_name(intent.getStringExtra(Constants.ROLL_NO),intent.getStringExtra(Constants.STUDENT_FULL_NAME));
        }

        intent.setAction(Constants.FILTER_ACTION_KEY);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        stopSelf();
        return START_NOT_STICKY;
    }
}
