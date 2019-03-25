package com.example.resrrecycleview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.resrrecycleview.R;
import com.example.resrrecycleview.model.Student;
import java.util.ArrayList;

/**
 *This is Adapter class for RecycleView
 *
 */

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Student> name;
    private OnItemClickListener mListener;

    //Interface for on Item click of Recycleview
    public interface OnItemClickListener{
        public void onItemClick(int pos);
    }

    //set Onclicklistener of this class
    public void setOnClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    //set Arraylist with help of constructor
   public  StudentAdapter(final ArrayList<Student> name){
        this.name=name;
    }

    /**
     * This method used to create view and inflate xml file and return StudentViewHolder Object
     *
     * @param viewGroup
     * @param i
     * @return studentViewHolder
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_list_adapter,viewGroup,false);
       StudentViewHolder studentViewHolder = new StudentViewHolder(view)
;       return studentViewHolder;
    }

    /**
     * This method used to bind the view with data
     *
     * @param viewHolder
     * @param i
     */

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

       Student std=name.get(i);
       String title=std.getmId();
        ((StudentViewHolder) viewHolder).mTextViewRollNo.setText(title);
        ((StudentViewHolder) viewHolder).mTextViewName.setText(std.getmFirstName()+" "+std.getmLastName());

    }


    @Override
    public int getItemCount() {

        return name.size();
    }

    /**
     * inner class for viewHolder
     * used set id to textview and also used set Onclicklistener
     */

    class StudentViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewRollNo;
        private TextView mTextViewName;
        RelativeLayout relativeLayout;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            //set ids to both textview
            mTextViewName=itemView.findViewById(R.id.textName);
            mTextViewRollNo=itemView.findViewById(R.id.text);

            //setting OnclickListener to item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int po=getAdapterPosition();
                        if(po!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(po);
                        }
                    }
                }
            });
        }
    }
}
