package com.example.mob3000;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudent extends AppCompatActivity {
    EditText studentid, password, firstname, lastname, campus, year, subject;
    Button button;
    String mStudentId;
    Intent intent;
    private MyDatabase mDb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatestudent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        mDb = MyDatabase.getDatabase(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Student_Id)) {
            //button.setText("Update");

            mStudentId = intent.getStringExtra(Constants.UPDATE_Student_Id);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Student student = mDb.getStudentDao().loadStudentById(mStudentId);
                    populateUI(student);
                }
            });


        }

    }

    private void populateUI(Student student) {

        if (student == null) {
            return;
        }
        studentid.setText(student.getSid());
        password.setText(student.getPassword());
        firstname.setText(student.getFirstname());
        lastname.setText(student.getLastname());
        campus.setText(student.getCampus());
        year.setText(student.getYear());
        subject.setText(student.getSubject());
    }

    private void initViews() {
        studentid = findViewById(R.id.editTextStudentid);
        password = findViewById(R.id.editTextPassword);
        firstname = findViewById(R.id.editTextFirstName);
        lastname = findViewById(R.id.editTextLastName);
        campus = findViewById(R.id.editTextCampus);
        year = findViewById(R.id.editTextYear);
        subject = findViewById(R.id.editTextSubject);
        button = findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        final String sid = studentid.getText().toString();
        final String pass = password.getText().toString();
        final String fname = firstname.getText().toString();
        final String lname = lastname.getText().toString();
        final String camp = campus.getText().toString();
        final String y = year.getText().toString();
        final String sub = subject.getText().toString();
        final Student student = new Student();
        student.setSid(sid);
        student.setPassword(pass);
        student.setFirstname(fname);
        student.setLastname(lname);
        student.setCampus(camp);
        student.setYear(y);
        student.setSubject(sub);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Student_Id)) {
                    mDb.getStudentDao().insertStudent(student);
                } else {
                    student.setSid(mStudentId);
                    mDb.getStudentDao().updateStudent(student);
                }
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    public void updateStudent(View view) {
        final String id = edStudentnr.getText().toString();
        final String studpass = edPassword.getText().toString();
        final String firstname = edFirstname.getText().toString();
        final String lastname = edLastname.getText().toString();
        final String campus = edCampus.getText().toString();
        final String schoolyear = edYear.getText().toString();
        final String sub = edSubject.getText().toString();
        mDb = MyDatabase.getDatabase(getApplicationContext());
        final StudentDao sdao = mDb.getStudentDao();
        try {
            String kryptering = Kryptering.encrypt(studpass);
            final Student student = new Student();
            student.setSid(id);
            student.setPassword(kryptering);
            student.setFirstname(firstname);
            student.setLastname(lastname);
            student.setCampus(campus);
            student.setYear(schoolyear);
            student.setSubject(sub);

            if (validateInput(student)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sdao.updateStudent(student);
                    }
                }).start();
            } else {
                Toast.makeText(getApplicationContext(), "Fyll alle feltene!", Toast.LENGTH_LONG).show();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //tester om alle feltene er fylt inn
    private Boolean validateInput(Student student){
        if(student.getSid().isEmpty() ||
                student.getPassword().isEmpty() ||
                student.getFirstname().isEmpty() ||
                student.getLastname().isEmpty() ||
                student.getCampus().isEmpty() ||
                student.getSubject().isEmpty() ||
                student.getYear().isEmpty()){
            return false;
        }
        return true;
    }*/
}