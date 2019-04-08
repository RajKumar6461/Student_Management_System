package com.example.resrrecycleview.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.util.SendCallBack;


public class StudentBroadcastReceiver extends BroadcastReceiver {
    private SendCallBack callBackSend;
    private SendCallBack callBackDelete;

    public StudentBroadcastReceiver() {
    }

    public void setCallBackSend(SendCallBack callBackSend) {
        this.callBackSend = callBackSend;
    }

    public void setCallBackDelete(SendCallBack callBackDelete) {
        this.callBackDelete = callBackDelete;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY).equals(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_DELETE)){
            callBackDelete.callBackAsync(intent.getStringExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY));
        }else {
            callBackSend.callBackAsync(intent.getStringExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY));
        }
    }
}
