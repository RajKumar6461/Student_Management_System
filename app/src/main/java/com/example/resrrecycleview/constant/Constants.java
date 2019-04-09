package com.example.resrrecycleview.constant;

public interface Constants {


    String STUDENT_DATA="student_data";
    String STUDENT_DATA_List="student_data_List";
    String TYPE_ACTION_FROM_MAIN_ACTIVITY="Action Performed";
    String TYPE_ACTION_FROM_MAIN_ACTIVITY_ADD="Add";
    String TYPE_ACTION_FROM_MAIN_ACTIVITY_VIEW="View";
    String TYPE_ACTION_FROM_MAIN_ACTIVITY_EDIT="Edit";
    String FIRST_NAME="fname";
    String STUDENT_FULL_NAME="Studentname";
    String LAST_NAME="lname";
    String ROLL_NO="Roll_no";
    String BTN_CHANGE_TEXT_UPDATE="Update";
    String DELETE_ALERT_DIALOG_TITLE=" Do you want to Delete ";
    String CHOOSE_ALERT_DIALOG_TITLE=" Choose Action ";
    int CHECK_ARRAYLIST_SIZE_ZERO=0;
    String DELETE_TOAST="Deleted Successfully";
    String UPDATE_TOAST="Update Successfully";
    String ADD_TOAST="Added Successfully";
    String SORT_BY_NAME_TOAST="Sort By Name";
    String SORT_BY_ROLL_NO_TOAST="Sort By Roll No";
    String GRID_LAYOUT_RECYCLER_VIEW="Grid is Selected";
    String LINEAR_LAYOUT_RECYCLER_VIEW="Linear is Selected";
    String FILTER_ACTION_KEY = "interstellar";
    int VIBRATE_MILI_SECOND=500;
    int LIST_FRAGMENT=0;
    int ADD_UPDATE_FRAGMENT=1;
    String MESSAGE_TRANSACTION="Added";
    String TYPE_ACTION_FROM_MAIN_ACTIVITY_DELETE ="Delete" ;
    String FILTER_ACTION_KEY_DELETE = "Delete_key";
    String SUPER_RETURN="BackgroundIntentService";


    interface DataBaseMembers{
        String DATABASE_NAME="studentManagement.db";
        int DATABASE_VERSION=1;
        String TABLE_NAME="student";
        String COL_NAME="Name";
        String COL_ROLL="Roll_number";
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"("+COL_ROLL+" TEXT PRIMARY KEY,"+COL_NAME+" TEXT)";
        String DROP_TABLE=" DROP TABLE IF EXISTS "+TABLE_NAME;
        String SELECT_ALL_DATA="SELECT * FROM "+TABLE_NAME;
        String REGEX_FOR_SPLIT=" ";
        int FIRST_NAME=0;
        int LAST_NAME=1;
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
        int GRID_SPAN=2;
        int VIEW=0;
        int EDIT=1;
        int DELETE=2;
        String[] itemDialog={"VIEW" , "EDIT" , "DELETE"};
        String NO_DATA="NO STUDENT ADDED";
    }

    interface ViewPagerAdapterMember{
        int LIST_FRAGMENT=0;
        int ADD_UPDATE_FRAGMENT=1;
        int TOTAL_FRAGMENT=2;
        String STUDENT_LIST_TAB_TITLE = "Student List";
        String ADD_UPDATE_TAB_TITLE = "Add/Update";
    }
}
