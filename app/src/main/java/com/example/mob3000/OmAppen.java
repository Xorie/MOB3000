package com.example.mob3000;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.Serializable;
import java.util.List;

public class OmAppen extends AppCompatActivity {
    EditText sok_id;
    private MyDatabase database;
    private Switch btndark;


    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.om_appen);
        database = MyDatabase.getDatabase(getApplicationContext());
        sok_id = findViewById(R.id.sok);

        btndark  = findViewById(R.id.switchdark);

        // Saving state of our app
        // using SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            btndark.setText("Dark Mode");
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            btndark.setText("Light Mode");
        }

        btndark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When user taps the enable/disable
                // dark mode button
                if (isDarkModeOn) {

                    // if dark mode is on it
                    // will turn it off
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    // it will set isDarkModeOn
                    // boolean to false
                    editor.putBoolean("isDarkModeOn", false);
                    editor.apply();

                    // change text of Button
                    btndark.setText("Dark Mode");
                } else {

                    // if dark mode is off
                    // it will turn it on
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    // it will set isDarkModeOn
                    // boolean to true
                    editor.putBoolean("isDarkModeOn", true);
                    editor.apply();

                    // change text of Button
                    btndark.setText("Light Mode");
                    }
                }
            });
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
