package com.example.mob3000;


import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                student.setSid(mStudentId);
                mDb.getStudentDao().updateStudent(student);
                Looper.prepare();
                Toast.makeText(UpdateStudent.this, "Brukerinformasjon oppdatert!", Toast.LENGTH_LONG).show();
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

    //ikke ferdig
    public void goBackBruker (View view){
        Intent i = getIntent();
        final String bruker = studentid.getText().toString();
        Intent intent = (new Intent(UpdateStudent.this, BrukerProfil.class).putExtra("SID", bruker));
        startActivity(intent);
    }

}
