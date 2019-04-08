package com.example.resrrecycleview.constant;

public interface Constants {


    public static final String STUDENT_DATA="student_data";
    public static final String STUDENT_DATA_List="student_data_List";
    public static final String TYPE_ACTION_FROM_MAIN_ACTIVITY="Action Performed";
    public static final String TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD="Add";
    public static final String TYPE_ACTION_FROM_MAIN_ACTIVITY_VIEW="View";
    public static final String TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT="Edit";
    public static final String FIRST_NAME="fname";
    public static final String STUDENT_FULL_NAME="Studentname";
    public static final String LAST_NAME="lname";
    public static final String ROLL_NO="Roll_no";
    public static final String BTN_CHANGE_TEXT_UPDATE="Update";
    public static final String DELETE_ALERT_DIALOG_TITLE=" Do you want to Delete ";
    public static final String CHOOSE_ALERT_DIALOG_TITLE=" Choose Action ";
    public static final int CHECK_ARRAYLIST_SIZE_ZERO=0;
    public static final String DELETE_TOAST="Deleted Successfully";
    public static final String UPDATE_TOAST="Update Successfully";
    public static final String ADD_TOAST="Added Successfully";
    public static final String SORT_BY_NAME_TOAST="Sort By Name";
    public static final String SORT_BY_ROLL_NO_TOAST="Sort By Roll No";
    public static final String GRID_LAYOUT_RECYCLER_VIEW="Grid is Selected";
    public static final String LINEAR_LAYOUT_RECYCLER_VIEW="Linear is Selected";
    public static final String FILTER_ACTION_KEY = "interstellar";
    public static final int VIBRATE_MILI_SECOND=500;
    public static final int LIST_FRAGMENT=0;
    public static final int ADD_UPDATE_FRAGMENT=1;
    public static final String MESSAGE_TRANSACTION="Added";
    String TYPE_ACTION_FROM_MAIN_ACTIVITY_DELETE ="Delete" ;
    String FILTER_ACTION_KEY_DELETE = "Delete_key";


    interface DataBaseMembers{
        public static final String DATABASE_NAME="studentManagement.db";
        public static final int DATABASE_VERSION=1;
        public static final String TABLE_NAME="student";
        public static final String COL_NAME="Name";
        public static final String COL_ROLL="Roll_number";
        public static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+COL_ROLL+" TEXT PRIMARY KEY,"+COL_NAME+" TEXT)";
        public static final String DROP_TABLE=" DROP TABLE IF EXISTS "+TABLE_NAME;
        public static final String SELECT_ALL_DATA="SELECT * FROM "+TABLE_NAME;
        public static final String REGEX_FOR_SPLIT=" ";
        public static final int FIRST_NAME=0;
        public static final int LAST_NAME=1;
    }


    interface AddUpdateFragmentMember{
        public static final int REQUEST_CODE_ADD=2;
        public static final int REQUEST_CODE_EDIT=1;
        public static final int ASYNC_TASK=0;
        public static final int SERVICE=1;
        public static final int INTENT_SERVICE=2;
        public final static String[] ITEM_DAILOG={"AsyncTask" , "Service" , "Intent Service"};
    }


    interface StudentListFragmentMamber{
        public static final int GRID_SPAN=2;
        public static final int VIEW=0;
        public static final int EDIT=1;
        public static final int DELETE=2;
        public final static String[] itemDialog={"VIEW" , "EDIT" , "DELETE"};
        public static final String NO_DATA="NO STUDENT ADDED";
    }
}
