package com.example.mob3000;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registrering extends Activity {
    //EditText Name, Pass , updateold, updatenew, delete;
    TextView firstName, lastName, studentnr,school,campus,education,year,subject,link,password;
    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstName= (TextView) findViewById(R.id.firstname);
        lastName= (TextView) findViewById(R.id.lastname);
        studentnr= (TextView) findViewById(R.id.studentnr);
        school= (TextView) findViewById(R.id.school);
        campus= (TextView) findViewById(R.id.campus);
        education= (TextView) findViewById(R.id.education);
        year= (EditText) findViewById(R.id.year);
        subject= (TextView) findViewById(R.id.subject);
        link= (TextView) findViewById(R.id.link);
        password= (TextView) findViewById(R.id.password);

        helper = new myDbAdapter(this);
    }
    public void addUser(View view){

    }
    public void viewdata(View view)
    {
        String data = helper.getData();
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

}
