package com.example.mob3000;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Registrering reg;
    private Button btnlogin, btnreg;
    private EditText etName, etPass;

    Registrering reg_bruker = new Registrering();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The activity is being created.
    }


    //sharedpref
    private SharedPreferences mSharedpref;
    private String sharedPrfFilename = "com.example.sharedprefclass";

    //konstrultør
    public MainActivity(Registrering reg, Button btnlogin, Button btnreg, EditText etName, EditText etPass) {
        this.reg = reg;
        this.btnlogin = btnlogin;
        this.btnreg = btnreg;
        this.etName = etName;
        this.etPass = etPass;
    }


    public MainActivity(int contentLayoutId, Registrering reg, Button btnlogin, Button btnreg, EditText etName, EditText etPass) {
        super(contentLayoutId);
        this.reg = reg;
        this.btnlogin = btnlogin;
        this.btnreg = btnreg;
        this.etName = etName;
        this.etPass = etPass;
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                String name_key, pass_key, curPass;
                name_key = etName.getText().toString();
                curPass = etPass.getText().toString();
                pass_key = name_key + "pass";

                String name_stored = mSharedpref.getString(name_key, "Brukernavn ikke funnet");
                String pass_stored = mSharedpref.getString(pass_key, "Passord ikke funnet");
                //if test for å sjekke feltene
                if (!name_key.equals(name_stored)) {
                    Toast.makeText(this, "Brukernavn er feil", Toast.LENGTH_LONG).show();
                } else if (!curPass.equals(pass_stored)) {
                    Toast.makeText(this, "Passordet er feil", Toast.LENGTH_LONG).show();
                } else if (name_key.equals(name_stored) && curPass.equals(pass_stored)) {
                    Toast.makeText(this, "Du kan logge inn", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.registration:
                reg_bruker.addUser(View view);
                break;
        }

/*
        public void onLogin (View view){
            String name_key, pass_key, curPass;
            name_key = etName.getText().toString();
            curPass = etPass.getText().toString();
            pass_key = name_key + "pass";

            String name_stored = mSharedpref.getString(name_key, "Name not found");
            String pass_stored = mSharedpref.getString(pass_key, "Password not found");

            if (!name_key.equals(name_stored)) {
                Toast.makeText(this, "Name is wrong", Toast.LENGTH_LONG).show();
            } else if (!curPass.equals(pass_stored)) {
                Toast.makeText(this, "Password is wrong", Toast.LENGTH_LONG).show();
            } else if (name_key.equals(name_stored) && curPass.equals(pass_stored)) {
                Toast.makeText(this, "You can login", Toast.LENGTH_LONG).show();

            }

        }*/
    }