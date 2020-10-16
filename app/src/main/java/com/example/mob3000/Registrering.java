package com.example.mob3000;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Registrering extends AppCompatActivity {
    EditText firstName,lastName,studentnr,school,campus,
            education,subject,password, year;
    private MyDatabase mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrering);
        mDb = MyDatabase.getDatabase(getApplicationContext());
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        studentnr = findViewById(R.id.studentnr);
        school = findViewById(R.id.school);
        campus = findViewById(R.id.campus);
        education = findViewById(R.id.education);
        year = findViewById(R.id.year);
        subject = findViewById(R.id.subject);
        password = findViewById(R.id.password);
    }


    public void onClickSave(View view) {
        final Student student1 = new Student(
                studentnr.getText().toString(),
                password.getText().toString(),
                firstName.getText().toString(),
                lastName.getText().toString(),
                Integer.parseInt(school.getText().toString()),
                education.getText().toString(),
                year.getText().toString()
        );
        // now run in another thread
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.StudentDao().insertStudent(student1);
                finish();
            }
        });
    }
}

