package com.example.mob3000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        String name = getIntent().getStringExtra("Navn");
        String emne = getIntent().getStringExtra("Emne");
        String gruppe = getIntent().getStringExtra("Gruppe");
        String kommentar = getIntent().getStringExtra("Kommentar");
        TextView intro = findViewById(R.id.introduction);
        TextView desc = findViewById(R.id.description);
        intro.setText(name + " ønsker å invitere deg til " + gruppe + " i emnet " + emne);
        desc.setText(kommentar);

    }

    // Hvis bruker aksepterer invitasjon, så skal det opprettes en gruppe
    public void onAccept(View view){
        Intent intent = new Intent(Notification.this, BrukerProfil.class);
        startActivity(intent);
    }

    // Sender bruker tilbake til brukerprofil
    public void onDecline(View view){
        Intent intent = new Intent(Notification.this, BrukerProfil.class);
        startActivity(intent);
    }
}