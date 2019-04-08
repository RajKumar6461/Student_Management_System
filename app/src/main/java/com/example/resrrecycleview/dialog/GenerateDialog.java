package com.example.resrrecycleview.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.resrrecycleview.R;
import com.example.resrrecycleview.asynctask.BackSetUpdateData;
import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.service.SetUpdateDbIntentService;
import com.example.resrrecycleview.service.SetUpdateService;
import com.example.resrrecycleview.util.SendCallBack;

public class GenerateDialog {


    private Context mContext;
    private SendCallBack mFragmentContext;
    private int select;

    public GenerateDialog(Context mContext, SendCallBack mFragmentContext) {
        this.mContext = mContext;
        this.mFragmentContext=mFragmentContext;
    }

    /**
     *
     * @param rollNo
     * @param fullName
     * @param typeOperation
     */
    public void generateAlertDialog(final String rollNo, final String fullName, final String typeOperation){
        Log.d("generate", "generateAlertDialog: "+"1");
        final AlertDialog.Builder mBuilder=new AlertDialog.Builder(mContext);
        switch (typeOperation){
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD:
                mBuilder.setTitle(R.string.AddDialogBoxTitle);
                break;
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT:
                mBuilder.setTitle(R.string.UpdateDialogBoxTitle);
                break;
            case Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY_DELETE:
                mBuilder.setTitle(R.string.UpdateDialogBoxTitle);
                break;
        }
        mBuilder.setSingleChoiceItems(Constants.AddUpdateFragmentMember.ITEM_DAILOG, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {

                select=which;
                Log.d("generate", "generateAlertDialog: "+"2");

                dialog.dismiss();

                switch (select){
                    case Constants.AddUpdateFragmentMember.ASYNC_TASK:
                        new BackSetUpdateData(mContext,mFragmentContext).execute(typeOperation,rollNo,fullName);
                        break;
                    case Constants.AddUpdateFragmentMember.SERVICE:
                        Intent service=new Intent(mContext, SetUpdateService.class);
                        startServiceWork(service,rollNo,fullName,typeOperation);
                        break;
                    case Constants.AddUpdateFragmentMember.INTENT_SERVICE:
                        Log.d("generate", "generateAlertDialog: "+"3");

                        Intent intentForService=new Intent(mContext, SetUpdateDbIntentService.class);
                        startServiceWork(intentForService,rollNo,fullName,typeOperation);
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
    private void startServiceWork(final Intent service,final String rollNo, final String fullName, final String typeOperation){
        service.putExtra(Constants.TYPE_ACTION_FROM_MAIN_ACTIVITY,typeOperation);
        service.putExtra(Constants.ROLL_NO,rollNo);
        service.putExtra(Constants.STUDENT_FULL_NAME,fullName);
        Log.d("generate", "generateAlertDialog: "+"4");

        mContext.startService(service);
    }
}
