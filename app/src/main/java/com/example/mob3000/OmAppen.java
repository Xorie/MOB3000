package com.example.mob3000;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
                final List<Student> sok_liste = database.getStudentDao().sok(sok_tekst);
                Intent intent = (new Intent(OmAppen.this, SokeResultater.class).putExtra("LIST", (Serializable) sok_liste));
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
        Intent intent = new Intent(this, BrukerProfil.class);
        startActivity(intent);
    }
}
