package com.example.mob3000;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.Serializable;
import java.util.List;

public class BrukerProfil extends AppCompatActivity {
    private MyDatabase mDb;
    private StudentAdapter mAdapter;
    private RecyclerView recView;
    private Button invite;
    private TextView num;
    private final int MAX_NUM = 99;
    private int notification_num_count = 0;
    private CardView notification_dot;
    private ImageView notification_bell;

    // wip
    private String valgt_emne;
    private String valgt_gruppe;
    private String kommentar;
    public String getEmne() {
        return valgt_emne;
    }
    public String getGruppe() {
        return valgt_gruppe;
    }
    public String getKommentar() {
        return kommentar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bruker_profil);
        mDb = MyDatabase.getDatabase(getApplicationContext());
        recView = findViewById(R.id.student_recview);
        recView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new StudentAdapter(this);
        recView.setAdapter(mAdapter);

        createNotificationChannel();
        NotificationCompat.Builder build = new NotificationCompat.Builder(this, "wtf")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Studentbay")
                .setContentText("du har fått en invitasjon")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notification_dot = findViewById(R.id.notification_dot);
        notification_bell = findViewById(R.id.notification_bell);

        if(notification_num_count == 0){
            notification_dot.setVisibility(View.INVISIBLE);
        }

        notification_bell.setOnClickListener(v -> {
            notification_num_count = 0;
            notification_dot.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(BrukerProfil.this);
            builder.setTitle("Invitasjon");
            View myView = getLayoutInflater().inflate(R.layout.activity_notification, null);
            builder.setNegativeButton("Avslå", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(BrukerProfil.this, "Avslått",
                            Toast.LENGTH_SHORT).show();
                }
            });

            builder.setPositiveButton("Godta", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(BrukerProfil.this, "Godtatt",
                            Toast.LENGTH_SHORT).show();
                }
            });

            builder.setView(myView);
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        num = findViewById(R.id.notification_num);
        invite = findViewById(R.id.invite);
        invite.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(BrukerProfil.this);
            View myView = getLayoutInflater().inflate(R.layout.dialog_invite, null);

            final Spinner emner = myView.findViewById(R.id.listeEmner);
            final Spinner grupper = myView.findViewById(R.id.listeGrupper);
            final EditText user_input = myView.findViewById(R.id.userinput);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(BrukerProfil.this,
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.course_list));
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(BrukerProfil.this,
                    android.R.layout.simple_spinner_item,
                    getResources().getStringArray(R.array.group_list));
            emner.setAdapter(adapter1);
            grupper.setAdapter(adapter2);

            builder.setPositiveButton("Send invitasjon", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(BrukerProfil.this, "Invitasjon sendt",
                            Toast.LENGTH_SHORT).show();
                    increaseNum();
                    notificationManager.notify(100, build.build());
                    // wip
                    valgt_emne = emner.getSelectedItem().toString();
                    valgt_gruppe = grupper.getSelectedItem().toString();
                    kommentar = user_input.getText().toString();
                    //
                }
            });
            builder.setView(myView);
            AlertDialog dialog = builder.create();
            dialog.show();
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
                String studentid = i.getStringExtra("SID");
                final List<Student> bruker_liste;
                if(studentid != null) {
                    bruker_liste = mDb.getStudentDao().loadAllStudent(studentid);
                }
                else {
                    bruker_liste = (List<Student>) i.getSerializableExtra("LIST");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {mAdapter.setData(bruker_liste);}
                });
            }
        });
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
                    Looper.prepare();
                    Toast.makeText(BrukerProfil.this, "Bruker slettet!", Toast.LENGTH_LONG).show();
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

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= 26) {
            CharSequence name = "testChannel";
            String desc = "Testing description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("wtf", name, importance);
            channel.setDescription(desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void increaseNum(){
        notification_num_count++;
        if(notification_num_count > 0){ notification_dot.setVisibility(View.VISIBLE); }
        if(notification_num_count > MAX_NUM){ Log.d("Counter", "Maks antall nådd!"); }
        else num.setText(String.valueOf(notification_num_count));
    }
}
