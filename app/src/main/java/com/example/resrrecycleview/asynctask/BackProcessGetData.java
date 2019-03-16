package com.example.resrrecycleview.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.resrrecycleview.database.StudentDatabaseHelper;
import com.example.resrrecycleview.model.Student;

import java.util.ArrayList;

public class BackProcessGetData  extends AsyncTask<String,Void, ArrayList<Student>> {
    private Context mContext;
    Callback callback;

    public BackProcessGetData(Context mContext,Callback callback) {
        this.mContext=mContext;
        this.callback=callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Student> out) {
//Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
        callback.getOutput(out);
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    @Override
    protected ArrayList<Student> doInBackground(String... params) {
        StudentDatabaseHelper dbHelper=new StudentDatabaseHelper(mContext);

        return dbHelper.getData();

    }
    public interface Callback{
        void getOutput(ArrayList<Student> out);
    }
}
