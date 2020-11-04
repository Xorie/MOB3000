package com.example.mob3000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextid, editTextPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextid = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
    }

    public void login(View view) {
        final String id = editTextid.getText().toString();
        final String password2 = editTextPassword.getText().toString();

        if (id.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "Du må fylle inn alle feltene!", Toast.LENGTH_LONG).show();
        } else {
            //fra databasen
            StudentDao studentdao;
            MyDatabase database = MyDatabase.getDatabase(getApplicationContext());
            final StudentDao sdao = database.getStudentDao();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Student stud = sdao.login(id, password2);
                    if (stud == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Feil brukernavn/passord", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Intent intent = new Intent(MainActivity.this, OmAppen.class);
                        startActivity(intent);
                    }
                }
                }).start();
            }
        }

            //for å gå til registering
            public void getSecond (View view){
                Intent intent = new Intent(MainActivity.this, Registrering.class);
                startActivity(intent);
            }

            public void getThird (View view){
                Intent intent = new Intent(MainActivity.this, BrukerProfil.class);
                startActivity(intent);
            }
        }



