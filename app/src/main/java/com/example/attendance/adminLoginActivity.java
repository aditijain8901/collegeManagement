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

public class adminLoginActivity extends AppCompatActivity {

    TextView welcome_text;
    CardView add_student, add_faculty, view_studentlist, view_attendance, view_course;

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
                Intent in = new Intent(adminLoginActivity.this, aboutActivity.class);
                startActivity(in);
                return true;
            case R.id.logout:
                final AlertDialog.Builder alert = new AlertDialog.Builder(adminLoginActivity.this);
                alert.setTitle("Log Out");
                alert.setMessage("Dou You Want to log out?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final ProgressDialog dialog = new ProgressDialog(adminLoginActivity.this);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setIndeterminate(true);
                        dialog.setMessage("Logging Out...");
                        dialog.show();
                        Intent intent = new Intent(adminLoginActivity.this, MainActivity.class);
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
        setContentView(R.layout.activity_admin_login);
        welcome_text = findViewById(R.id.welcome_text_a);
        add_student = findViewById(R.id.add_student);
        add_faculty = findViewById(R.id.add_faculty);
        view_studentlist= findViewById(R.id.admin_studentlist);
        view_attendance = findViewById(R.id.view_attendance);
        view_course = findViewById(R.id.view_course);

        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminLoginActivity.this, addStudentActivity.class);
                startActivity(intent);
            }
        });
        add_faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminLoginActivity.this, addFacultyActivity.class);
                startActivity(intent);
            }
        });
        view_studentlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminLoginActivity.this, studentListActivity.class);
                startActivity(intent);
            }
        });
        view_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminLoginActivity.this, viewAttendanceActivity.class);
                startActivity(intent);
            }
        });
        view_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminLoginActivity.this, courseListActivity.class);
                startActivity(intent);
            }
        });
    }
}