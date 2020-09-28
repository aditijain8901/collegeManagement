package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class studentLoginActivity extends AppCompatActivity {
    TextView welcome_text;
    CardView student_attendance, student_studentlist , student_courselist;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutus:
                Intent in = new Intent(studentLoginActivity.this, aboutActivity.class);
                startActivity(in);
                return true;
            case R.id.logout:
                final AlertDialog.Builder alert = new AlertDialog.Builder(studentLoginActivity.this);
                alert.setTitle("Log Out");
                alert.setMessage("Dou You Want to log out?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final ProgressDialog dialog = new ProgressDialog(studentLoginActivity.this);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setIndeterminate(true);
                        dialog.setMessage("Logging Out...");
                        dialog.show();
                        Intent intent = new Intent(studentLoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        welcome_text = findViewById(R.id.welcome_text_s);
        student_attendance = findViewById(R.id.student_attendance);
        student_studentlist = findViewById(R.id.student_studentlist);
        student_courselist = findViewById(R.id.student_courselist);

        student_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(studentLoginActivity.this, viewAttendanceActivity.class);
                startActivity(intent);
            }
        });
        student_studentlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(studentLoginActivity.this, studentListActivity.class);
                startActivity(intent);
            }
        });
        student_courselist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(studentLoginActivity.this, courseListActivity.class);
                startActivity(intent);

                }
        });
    }
}