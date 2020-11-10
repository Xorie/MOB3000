package com.example.mob3000;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchDataAdapter extends RecyclerView.Adapter<SearchDataAdapter.SearchViewHolder> {

    /***************** First member variables and the constructor *********/
    private List<Student> mStudent;
    private Context context;

    View.OnClickListener mOnItemClickListener;


    public SearchDataAdapter(Context cont) {
        this.context = cont;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable for any view that will be set as you render a row
        TextView edFirstname;
        TextView edLastname;
        TextView edCampus;
        TextView edSubject;

        public SearchViewHolder(View itemView) {
            super(itemView);
            // Viewholder gets the handles for each view items in a row
            edFirstname = (TextView) itemView.findViewById(R.id.srFirstname);
            edLastname = (TextView) itemView.findViewById(R.id.srLastname);
            edCampus = (TextView) itemView.findViewById(R.id.srSchool);
            edSubject = (TextView) itemView.findViewById(R.id.srSubject);


            // for onCLicklistener
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }

    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View row_view2 = inflater.inflate(R.layout.row_searchlayout, parent, false);
        SearchViewHolder searchviewholder = new SearchViewHolder(row_view2);
        return searchviewholder;
    }

    public void onBindViewHolder(SearchViewHolder holder, int position) {

        Student currentStudent = mStudent.get(position);
        //  show the data in the views
        holder.edFirstname.setText(currentStudent.getFirstname());
        holder.edLastname.setText(currentStudent.getLastname());
        holder.edCampus.setText(currentStudent.getCampus());
        holder.edSubject.setText(currentStudent.getSubject());
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

    public void setCustomItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }
}
