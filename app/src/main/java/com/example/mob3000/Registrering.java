package com.example.mob3000;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registrering extends Activity {
    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name= (EditText) findViewById(R.id.editName);
        Pass= (EditText) findViewById(R.id.editPass);
        updateold= (EditText) findViewById(R.id.editText3);
        updatenew= (EditText) findViewById(R.id.editText5);
        delete = (EditText) findViewById(R.id.editText6);

        helper = new myDbAdapter(this);
    }
    public void addUser(View view)
    {
        String t1 = Name.getText().toString();
        String t2 = Pass.getText().toString();
        if(t1.isEmpty() || t2.isEmpty())
        {
            Toast.makeText(this, "\"Enter Both Name and Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            long id = helper.insertData(t1,t2);
            if(id<=0)
            {
                Toast.makeText(this, "Insertion Unsuccessful", Toast.LENGTH_SHORT).show();
                Name.setText("");
                Pass.setText("");
            } else
            {
                Toast.makeText(this, "Insertion Successful", Toast.LENGTH_SHORT).show();
                Name.setText("");
                Pass.setText("");
            }
        }
    }
    public void viewdata(View view)
    {
        String data = helper.getData();
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

}
