package com.example.mob3000;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SokeResultater extends AppCompatActivity {
    private MyDatabase mDb;
    private SearchDataAdapter mAdapter;

    private RecyclerView recView;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            final int position = viewHolder.getAdapterPosition();

            AppExecutors.getInstance().diskIO().execute(() -> {
                if (mAdapter.getItemCount() >= 1) {
                    List<Student> soke_liste = mAdapter.getData();
                    List<Student> bruker_liste = new ArrayList<>();
                    Student student = soke_liste.get(position);
                    bruker_liste.add(student);
                    String sjekk = "sjekker";
                    Intent intent = (new Intent(SokeResultater.this, BrukerProfil.class).putExtra("LIST", (Serializable) bruker_liste).putExtra("CHECK", sjekk));
                    startActivity(intent);
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

                if(soke_liste.isEmpty()) {
                    if (Looper.myLooper() == null)
                    {
                        Looper.prepare();
                    }
                    Toast.makeText(SokeResultater.this, "Ingen resultater!", Toast.LENGTH_LONG).show();
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setData(soke_liste);
                        }
                    });
                }
            }
        });
    }

    public void back(View view) {
        Intent i = getIntent();
        final String bruker = i.getStringExtra("SID");
        Intent intent = (new Intent(SokeResultater.this, OmAppen.class).putExtra("SID", bruker));
        startActivity(intent);
    }

    public void getAccount(View view) {
        Intent i = getIntent();
        final String bruker = i.getStringExtra("SID");
        Intent intent = (new Intent(this, BrukerProfil.class).putExtra("SID", bruker));
        startActivity(intent);
    }
}
