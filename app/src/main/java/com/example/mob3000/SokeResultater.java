package com.example.mob3000;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SokeResultater extends AppCompatActivity {
    private MyDatabase mDb;
    private SearchDataAdapter mAdapter;
    private OmAppen oa = new OmAppen();

    private RecyclerView recView;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            final int position = viewHolder.getAdapterPosition();

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (mAdapter.getItemCount() >= 1) {
                        //int position=0;
                        List<Student> soke_liste = mAdapter.getData();
                        showListData();
                    }
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soke_resultater);

        //get the database
        mDb = MyDatabase.getDatabase(getApplicationContext());

        //get the recview handle attach to XML
        recView = findViewById(R.id.sok_recview);
        //set the layoutManager
        recView.setLayoutManager(new LinearLayoutManager(this));

        //********************* Adapter***********
        mAdapter = new SearchDataAdapter(this);
        //attach adapter to rec view
        recView.setAdapter(mAdapter);
        mAdapter.setCustomItemClickListener(onItemClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showListData();
    }

    private void showListData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Intent i = getIntent();
                final List<Student> soke_liste = (List<Student>) i.getSerializableExtra("LIST");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(soke_liste);
                    }
                });
            }
        });
    }

    public void back(View view) {
        Intent intent = new Intent(SokeResultater.this, OmAppen.class);
        startActivity(intent);
    }

    public void getAccount(View view) {
        Intent intent = new Intent(this, BrukerProfil.class);
        startActivity(intent);
    }
}
