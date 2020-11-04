package com.example.mob3000;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BrukerProfil extends AppCompatActivity {
    private MyDatabase mDb;
    private DataAdapter mAdapter;

    private RecyclerView recView;

    private  View.OnClickListener onItemClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            final int position = viewHolder.getAdapterPosition();

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (mAdapter.getItemCount()>=1){
                        //int position=0;
                        List<Student> studentList = mAdapter.getData();
                        mDb.getStudentDao().deleteStudent(studentList.get(position));
                        showListData();
                    }
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bruker_profil);

        //get the database
        mDb = MyDatabase.getDatabase(getApplicationContext());

        //get the recview handle attach to XML
        recView = findViewById(R.id.student_recview);
        //set the layoutManager
        recView.setLayoutManager(new LinearLayoutManager(this));

        //********************* Adapter***********
        mAdapter = new DataAdapter(this);
        //atttach adatper to rec view
        recView.setAdapter(mAdapter);
        mAdapter.setCustomItemClickListener(onItemClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showListData();
    }

    private void showListData(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Student> studentList = mDb.getStudentDao().loadAllStudent();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(studentList);
                    }
                });
            }
        });
    }

/* om bruker ønsker å slette profilen sin
    public void onClickDelete(View view) {

        AppExecutors.getInstance().diskIO().execute(new Runnable() {

            int position = 1;
            @Override
            public void run() {
                if (mAdapter.getItemCount()>=1){
                    //int position=0;
                    List<Drink> drinkList = mAdapter.getData();
                    mDb.mydrinksDao().deleteDrink(drinkList.get(position));
                    showListData();
                }
            }
        });

    }*/
}
