package com.example.mob3000;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

public class DialogInvite extends AppCompatActivity {
    TextView brukernavn;
    Spinner emner;
    Spinner grupper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_invite);
    }
}