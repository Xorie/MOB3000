package com.example.mob3000;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registrering extends AppCompatActivity {
    EditText firstName,lastName,studentnr,school,campus,
            subject,password, year;
    private MyDatabase db;
    private StudentDao sdao;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrering);
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        studentnr = findViewById(R.id.studentnr);
        school = findViewById(R.id.school);
        campus = findViewById(R.id.campus);
        year = findViewById(R.id.year);
        subject = findViewById(R.id.subject);
        password = findViewById(R.id.password);
    }


    public void onClickSave(View view) {
        final Student student1 = new Student();
                student1.setSid(studentnr.getText().toString());
                student1.setPassword(password.getText().toString());
                student1.setFirstname(firstName.getText().toString());
                student1.setLastname(lastName.getText().toString());
                student1.setSchoolid(school.getText().toString());
                student1.setYear(year.getText().toString());
                student1.setSubject(subject.getText().toString());

        if(validateInput(student1)){
            db = MyDatabase.getDatabase(getApplicationContext());
            sdao = db.getStudentDao();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sdao.insertStudent(student1);
                }
            }).start();
        }
        else {
            Toast.makeText(getApplicationContext(), "Fyll alle feltene!", Toast.LENGTH_LONG).show();
        }
    }

    //tester om alle feltene er fylt inn
    private Boolean validateInput(Student student1){
        if(student1.getSid().isEmpty() ||
                student1.getPassword().isEmpty() ||
                student1.getFirstname().isEmpty() ||
                student1.getLastname().isEmpty() ||
                student1.getSchoolid().isEmpty() ||
                student1.getSubject().isEmpty() ||
                student1.getYear().isEmpty()){
            return false;
        }
        return true;
    }
}

