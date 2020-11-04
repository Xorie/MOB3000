package com.example.mob3000;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.StudentViewHolder> {

    /***************** First member variables and the constructor *********/
    private List<Student> mStudent;
    private Context context;

    View.OnClickListener mOnItemClickListener;


    public DataAdapter(Context cont) {
        this.context = cont;
    }


    /************************ Second the view holder ************************/
    class StudentViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable for any view that will be set as you render a row
        TextView edStudentnr;
        TextView edPassword;
        TextView edFirstname;
        TextView edLastname;
        TextView edSchool;
        TextView edSubject;
        TextView edYear;

        public StudentViewHolder(View itemView) {
            super(itemView);
            // Viewholder gets the handles for each view items in a row
            edStudentnr = (TextView) itemView.findViewById(R.id.txtStudentnr);
            edPassword = (TextView) itemView.findViewById(R.id.txtPassword);
            edFirstname = (TextView) itemView.findViewById(R.id.txtFirstname);
            edLastname = (TextView) itemView.findViewById(R.id.txtLastname);
            edSchool = (TextView) itemView.findViewById(R.id.txtSchool);
            edSubject = (TextView) itemView.findViewById(R.id.txtSubject);
            edYear = (TextView) itemView.findViewById(R.id.txtYear);


            // for onCLicklistener
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }

        /***************** Third, the implementation methods*/

    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View row_view = inflater.inflate(R.layout.row_studentlayout, parent, false);
        StudentViewHolder studentviewholder = new StudentViewHolder(row_view);
        return studentviewholder;
    }

    public void onBindViewHolder(StudentViewHolder holder, int position) {

        Student currentStudent = mStudent.get(position);
        //  show the data in the views
        holder.edStudentnr.setText(currentStudent.getSid());
        holder.edPassword.setText(currentStudent.getPassword());
        holder.edFirstname.setText(currentStudent.getFirstname());
        holder.edLastname.setText(currentStudent.getLastname());
        holder.edSchool.setText(currentStudent.getSchoolid());
        holder.edSubject.setText(currentStudent.getSubject());
        holder.edYear.setText(currentStudent.getYear());
    }

    /**Returns the total number of items in the data set held by the adapter. */
    public int getItemCount() {
        if (mStudent == null) {
            return 0;
        }
        return mStudent.size();
    }

    //set data method sets the data from DB (collected by DAO) to the adapter
    public void setData(List<Student> studentList) {
        mStudent = studentList;
        notifyDataSetChanged();
    }

    public List<Student> getData() {
        return mStudent;
    }

    public void setCustomItemClickListener(View.OnClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }
}

