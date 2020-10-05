package com.example.mob3000;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class BrukerProfil extends Activity {
    public void update(View view) {
        String u1 = updateold.getText().toString();
        String u2 = updatenew.getText().toString();
        if (u1.isEmpty() || u2.isEmpty()) {
            Toast.makeText(this, "Enter data", Toast.LENGTH_SHORT).show();
        } else {
            int a = helper.updateName(u1, u2);
            if (a <= 0) {
                Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                updateold.setText("");
                updatenew.setText("");
            } else {
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                updateold.setText("");
                updatenew.setText("");
            }
        }
    }

    public void delete(View view) {
        String uname = delete.getText().toString();
        if (uname.isEmpty()) {
            Toast.makeText(this, "Enter Data", Toast.LENGTH_SHORT).show();
        } else {
            int a = helper.delete(uname);
            if (a <= 0) {
                Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                delete.setText("");
            } else {
                Toast.makeText(this, "DELETED", Toast.LENGTH_SHORT).show();
                delete.setText("");
            }
        }
    }
}
