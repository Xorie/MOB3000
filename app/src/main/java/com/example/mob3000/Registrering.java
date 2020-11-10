package com.example.mob3000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Registrering  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText firstName,lastName,studentnr,
            subject,password, year;
    Spinner spin;
        String[] campuslist = { "Bø","Drammen","Kongsberg","Nettstudier","Porsgrunn","Rauland","Ringerike","Vestfold"};
    private MyDatabase db;
    private StudentDao sdao;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrering);
        spin = (Spinner) findViewById(R.id.campus);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, campuslist);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        studentnr = findViewById(R.id.studentnr);
        year = findViewById(R.id.year);
        subject = findViewById(R.id.subject);
        password = findViewById(R.id.password);
    }


    public void onClickSave(View view) {
        final String studpass = password.getText().toString();
        try {
            String kryptering = Kryptering.encrypt(studpass);
            final Student student = new Student();
            student.setSid(studentnr.getText().toString());
            student.setPassword(kryptering);
            student.setFirstname(firstName.getText().toString());
            student.setLastname(lastName.getText().toString());
            student.setCampus(spin.getSelectedItem().toString());
            student.setYear(year.getText().toString());
            student.setSubject(subject.getText().toString());

            if(validateInput(student)){
                db = MyDatabase.getDatabase(getApplicationContext());
                sdao = db.getStudentDao();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sdao.insertStudent(student);
                    }
                }).start();
                Intent intent = new Intent(Registrering.this, MainActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Fyll alle feltene!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //tester om alle feltene er fylt inn
    private Boolean validateInput(Student student1){
        if(student1.getSid().isEmpty() ||
                student1.getPassword().isEmpty() ||
                student1.getFirstname().isEmpty() ||
                student1.getLastname().isEmpty() ||
                student1.getCampus().isEmpty() ||
                student1.getSubject().isEmpty() ||
                student1.getYear().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),campuslist[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

