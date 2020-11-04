package com.example.mob3000;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.room.Room;

public class OmAppen extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.om_appen);
    }

    public void logout (View view){
        Intent intent = new Intent(OmAppen.this, MainActivity.class);
        startActivity(intent);
    }

}
