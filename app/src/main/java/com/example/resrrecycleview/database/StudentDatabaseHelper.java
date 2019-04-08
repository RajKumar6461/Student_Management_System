package com.example.resrrecycleview.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.resrrecycleview.constant.Constants;
import com.example.resrrecycleview.model.Student;

import java.util.ArrayList;

/**
 * This id DataBase helper calss to use database facilities
 */

public class StudentDatabaseHelper extends SQLiteOpenHelper {


    /**
     * Constructer to assign context to super class
     * @param context
     */
    public StudentDatabaseHelper(Context context) {
        super(context, Constants.DataBaseMembers.DATABASE_NAME, null, Constants.DataBaseMembers.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //execute query for create table
        db.execSQL(Constants.DataBaseMembers.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DataBaseMembers.DROP_TABLE);
        onCreate(db);
    }

    /**
     * This method used to insert data to table
     *
     * @param roll_no of string type from ui
     * @param student_name of string type from ui
     */

    public void addData(String roll_no,String student_name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.DataBaseMembers.COL_ROLL,Integer.parseInt(roll_no));
        contentValues.put(Constants.DataBaseMembers.COL_NAME,student_name);
        db.insert(Constants.DataBaseMembers.TABLE_NAME,null,contentValues);
    }

    /**
     * this method used to get whole data in ArrayList form
     *
     * @return ArrayList of student class type for set recycle view
     */

    public ArrayList<Student> getData(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(Constants.DataBaseMembers.SELECT_ALL_DATA,null);
        ArrayList<Student> arrayOfstore=new ArrayList<Student>();
        String roll_no;
        String fullName;
        while(cursor.moveToNext()){
            roll_no=cursor.getString(cursor.getColumnIndex(Constants.DataBaseMembers.COL_ROLL));
            fullName=cursor.getString(cursor.getColumnIndex(Constants.DataBaseMembers.COL_NAME));
            String name_divided[]=fullName.split(Constants.DataBaseMembers.REGEX_FOR_SPLIT);
            Student studentDetails=new Student(name_divided[Constants.DataBaseMembers.FIRST_NAME],name_divided[Constants.DataBaseMembers.LAST_NAME],roll_no);
            arrayOfstore.add(studentDetails);
        }
        return arrayOfstore;
    }

    /**
     *This method used to delete row of matched rollNo
     * @param rollNo of String type used to check
     */

    public void deleteContact(String rollNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.DataBaseMembers.TABLE_NAME, Constants.DataBaseMembers.COL_ROLL + " = ?", new String[] { String.valueOf(rollNo) });
        db.close();
    }

    /**
     *
     * @param rollNo of string type for reference purpose
     * @param name of string type to update
     */

    public void update_name(String rollNo,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.DataBaseMembers.COL_NAME,name);
        db.update(Constants.DataBaseMembers.TABLE_NAME, cv, Constants.DataBaseMembers.COL_ROLL+" = ?", new String[]{rollNo});
        db.close();
    }
}
