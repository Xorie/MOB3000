package com.example.mob3000;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    private Context context;
    private List<Student> mStudentList;

    public StudentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.sid.setText(mStudentList.get(i).getSid());
        myViewHolder.fname.setText(mStudentList.get(i).getFirstname());
        myViewHolder.lname.setText(mStudentList.get(i).getLastname());
        myViewHolder.camp.setText(mStudentList.get(i).getCampus());
        myViewHolder.y.setText(mStudentList.get(i).getYear());
        myViewHolder.sub.setText(mStudentList.get(i).getSubject());
    }

    @Override
    public int getItemCount() {
        if (mStudentList == null) {
            return 0;
        }
        return mStudentList.size();

    }

    public void setData(List<Student> personList) {
        mStudentList = personList;
        notifyDataSetChanged();
    }

    public List<Student> getData() {
        return mStudentList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView sid, pass, fname, lname, camp, y, sub;
        ImageView editImage;
        MyDatabase mDb;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = MyDatabase.getDatabase(context);
            sid = itemView.findViewById(R.id.student_studentid);
            fname = itemView.findViewById(R.id.student_firstname);
            lname = itemView.findViewById(R.id.student_lastname);
            camp = itemView.findViewById(R.id.student_campus);
            y = itemView.findViewById(R.id.student_year);
            sub = itemView.findViewById(R.id.student_subject);
            editImage = itemView.findViewById(R.id.edit_Image);
            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String elementId = mStudentList.get(getAdapterPosition()).getSid();
                    Intent i = new Intent(context, UpdateStudent.class);
                    i.putExtra(Constants.UPDATE_Student_Id, elementId);
                    context.startActivity(i);
                }
            });
        }
    }
}
