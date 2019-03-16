package com.example.resrrecycleview.model;

import java.io.Serializable;

/**
 * This is model class used to make object of student
 */
public class Student implements Serializable {


    private String mFirstName;
    private String mLastName;
    private String mId;

    public Student() {
    }

    public Student(String mFirstName, String mLastName, String mId) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mId = mId;
    }

    public void setFirstName(final String firstname){
        this.mFirstName=firstname;
    }

    public void setLastName(final String lastname){
        this.mLastName=lastname;
    }

    public void setId(final String id){
        this.mId=id;
    }

    public String getmFirstName(){
        return mFirstName;
    }

    public String getmLastName(){
        return mLastName;
    }

    public String getmId(){
        return mId;
    }

}
