package com.example.mob3000;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class OmAppen extends AppCompatActivity {
    EditText sok_id;
    private MyDatabase database;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.om_appen);
        database = MyDatabase.getDatabase(getApplicationContext());
        sok_id = findViewById(R.id.sok);
    }

    public void logout (View view){
        Intent intent = new Intent(OmAppen.this, MainActivity.class);
        startActivity(intent);
    }

    public void getSok (View view){
        final String sok_tekst = sok_id.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent i = getIntent();
                final List<Student> sok_liste = database.getStudentDao().sok(sok_tekst);
                final String bruker = i.getStringExtra("SID");
                Intent intent = (new Intent(OmAppen.this, SokeResultater.class).putExtra("LIST", (Serializable) sok_liste).putExtra("SID", bruker));
                startActivity(intent);
            }
        }).start();

    }

    public List<Student> soke_resultater() {
        final String sok_tekst = sok_id.getText().toString();
        final List<Student> sok_liste = database.getStudentDao().sok(sok_tekst);
        return sok_liste;
    }

    public void getAccount(View view) {
        Intent i = getIntent();
        final List<Student> bruker_liste = (List<Student>) i.getSerializableExtra("ID");
        final String bruker = i.getStringExtra("SID");
        Intent intent = (new Intent(this, BrukerProfil.class).putExtra("ID", (Serializable) bruker_liste).putExtra("SID", bruker));
        startActivity(intent);
    }
}
