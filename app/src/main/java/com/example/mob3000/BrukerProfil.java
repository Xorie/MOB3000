package com.example.mob3000;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.io.Serializable;
import java.util.List;

public class BrukerProfil extends AppCompatActivity {
    private MyDatabase mDb;
    private StudentAdapter mAdapter;
    private Button invite;
    private RecyclerView recView;

    // For notifikasjonsklokka:
    private TextView num;
    private final int MAX_NUM = 99;
    private int notification_num_count = 1;

    /*
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
                        showListData();
                    }
                }
            });
        }
    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bruker_profil);
        mDb = MyDatabase.getDatabase(getApplicationContext());
        recView = findViewById(R.id.student_recview);
        recView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StudentAdapter(this);
        recView.setAdapter(mAdapter);

        num = findViewById(R.id.notification_num);
        invite = findViewById(R.id.invite);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BrukerProfil.this);
                View myView = BrukerProfil.this.getLayoutInflater().inflate(R.layout.dialog_invite, null);

                final Spinner emner = myView.findViewById(R.id.listeEmner);
                final Spinner grupper = myView.findViewById(R.id.listeGrupper);
                final EditText user_input = myView.findViewById(R.id.userinput);

                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(BrukerProfil.this,
                        android.R.layout.simple_spinner_item,
                        BrukerProfil.this.getResources().getStringArray(R.array.course_list));
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(BrukerProfil.this,
                        android.R.layout.simple_spinner_item,
                        BrukerProfil.this.getResources().getStringArray(R.array.group_list));
                emner.setAdapter(adapter1);
                grupper.setAdapter(adapter2);

                // Knapp i dialog-boksen:
                builder.setPositiveButton("Send invitasjon", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(BrukerProfil.this, "Invitasjon sendt",
                                Toast.LENGTH_SHORT).show();

                        // Foreløpig sendes det bare en push-varsel til brukeren selv.
                        // Skal også vise notifikasjonsvarsel eller øke varsleren per invitasjon...
                        increaseNum();
                        pushNotification(); // WIP


                        // Her må verdiene (som bruker har valgt) sendes videre til den brukeren
                        // som får invitasjon.

                    }
                });

                builder.setView(myView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
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
                Intent i = getIntent();
                final List<Student> bruker_liste = mDb.getStudentDao().loadAllStudent(i.getStringExtra("SID"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {mAdapter.setData(bruker_liste);}
                });
            }
        });
    }

    // Metode for å øke antallet på notifikasjonsklokka
    public void increaseNum(){
        notification_num_count++;
        if(notification_num_count > MAX_NUM){
            Log.d("Counter", "Maks antall nådd!");
        }
        else {
            num.setText(String.valueOf(notification_num_count));
        }
    }

    // Metode for push-varsel
    public void pushNotification(){
        String melding = "Du har blitt invitert av [] til gruppe [], MOB3000";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                BrukerProfil.this
        )
                .setSmallIcon(R.drawable.ic_notification_message)
                .setContentTitle("New notification")
                .setContentText(melding)
                .setAutoCancel(true);

        Intent intent = new Intent(BrukerProfil.this,
                Notification.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Melding:", melding);

        PendingIntent pendingIntent = PendingIntent.getActivity(BrukerProfil.this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE
        );

        notificationManager.notify(0, builder.build());

    }


    // om bruker ønsker å slette profilen sin
    public void onClickDelete(View view) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final MyDatabase database = MyDatabase.getDatabase(getApplicationContext());
                    final StudentDao sdao = database.getStudentDao();
                    Intent i = getIntent();
                    final String bruker = i.getStringExtra("SID");
                    System.out.println(bruker);
                    sdao.deleteById(bruker);
                    Intent intent = new Intent(BrukerProfil.this, MainActivity.class);
                    startActivity(intent);
                }
        }).start();

    }

    public void onClickUpdate(View view) {
        Intent i = getIntent();
        final List<Student> bruker_liste = (List<Student>) i.getSerializableExtra("ID");
        Intent intent = (new Intent(BrukerProfil.this, UpdateStudent.class).putExtra("ID", (Serializable) bruker_liste));
        startActivity(intent);
    }

    public void onClickBack(View view) {
        Intent i = getIntent();
        final List<Student> bruker_liste = (List<Student>) i.getSerializableExtra("ID");
        final String bruker = i.getStringExtra("SID");
        Intent intent = (new Intent(this, OmAppen.class).putExtra("ID", (Serializable) bruker_liste).putExtra("SID", bruker));
        startActivity(intent);
    }
}
