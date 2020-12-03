package com.example.mob3000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Notification extends AppCompatActivity {
    BrukerProfil bp;
    TextView intro;
    TextView desc;
    Button decline, accept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        decline = findViewById(R.id.decline);
        accept = findViewById(R.id.accept);
        intro = findViewById(R.id.introduction);
        desc = findViewById(R.id.description);
        intro.setText(bp.getValgt_emne());
        desc.setText(bp.getKommentar());
    }
}