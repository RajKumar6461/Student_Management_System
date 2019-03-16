package com.example.resrrecycleview.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.resrrecycleview.model.Student;

import java.util.ArrayList;

/**
 * This id DataBase helper calss to use database facilities
 */

public class StudentDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="studentManagement.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="student";
    private static final String COL_NAME="Name";
    private static final String COL_ROLL="Roll_number";
    private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+COL_ROLL+" TEXT PRIMARY KEY,"+COL_NAME+" TEXT)";

    /**
     * Constructer to assign context to super class
     * @param context
     */
    public StudentDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //execute query for create table
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
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
        contentValues.put(COL_ROLL,Integer.parseInt(roll_no));
        contentValues.put(COL_NAME,student_name);
        db.insert(TABLE_NAME,null,contentValues);
    }

    /**
     * this method used to get whole data in ArrayList form
     *
     * @return ArrayList of student class type for set recycle view
     */

    public ArrayList<Student> getData(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        ArrayList<Student> arrayOfstore=new ArrayList<Student>();
        String roll_no;
        String fullName;
        while(cursor.moveToNext()){
            roll_no=cursor.getString(cursor.getColumnIndex(COL_ROLL));
            fullName=cursor.getString(cursor.getColumnIndex(COL_NAME));
            String arr[]=fullName.split(" ");
            Student studentDetails=new Student(arr[0],arr[1],roll_no);
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
        db.delete(TABLE_NAME, COL_ROLL + " = ?", new String[] { String.valueOf(rollNo) });
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
        cv.put(COL_NAME,name);
        db.update(TABLE_NAME, cv, COL_ROLL+" = ?", new String[]{rollNo});
        db.close();
    }
}
