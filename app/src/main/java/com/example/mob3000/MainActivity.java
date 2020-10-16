package com.example.mob3000;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    private RecyclerView recView;
    private DataAdapter mDb;
    private Student student;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void login(View view) {
        Toast.makeText(this, "Hei", Toast.LENGTH_SHORT).show();
        Editable name_key;
        String curPass;
        name_key = username.getText();
        curPass = password.getText().toString();


        String name_stored = student.getSid();
        String pass_stored = student.getPassword();
        Toast.makeText(this, "HALLA", Toast.LENGTH_SHORT).show();

        //if test for å sjekke feltene
        if (!name_key.equals(name_stored)) {
            Toast.makeText(this, "Brukernavn er feil", Toast.LENGTH_LONG).show();
        } else if (!curPass.equals(pass_stored)) {
            Toast.makeText(this, "Passordet er feil", Toast.LENGTH_LONG).show();
        } else if (name_key.equals(name_stored) && curPass.equals(pass_stored)) {
            Toast.makeText(this, "Velkommen " + name_key, Toast.LENGTH_LONG).show();
        }
    }

    //for å gå til registering
    public void getSecond(View view) {
        Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, Registrering.class);
        startActivity(intent);
    }
    public void getThird(View view) {
        Toast.makeText(this,"Hei der!",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, BrukerProfil.class);
        startActivity(intent);
    }
}


