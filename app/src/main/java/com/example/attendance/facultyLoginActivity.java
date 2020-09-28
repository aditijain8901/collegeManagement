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

public class facultyLoginActivity extends AppCompatActivity {

        TextView welcome_text;
        CardView take_attendance, faculty_attendance, faculty_studentlist, faculty_courselist;

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
                Intent in = new Intent(facultyLoginActivity.this, aboutActivity.class);
                startActivity(in);
                return true;
            case R.id.logout:
                final AlertDialog.Builder alert = new AlertDialog.Builder(facultyLoginActivity.this);
                alert.setTitle("Log Out");
                alert.setMessage("Dou You Want to log out?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final ProgressDialog dialog = new ProgressDialog(facultyLoginActivity.this);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setIndeterminate(true);
                        dialog.setMessage("Logging Out...");
                        dialog.show();
                        Intent intent = new Intent(facultyLoginActivity.this, MainActivity.class);
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
        setContentView(R.layout.activity_faculty_login);

        welcome_text = findViewById(R.id.welcome_text_f);
        take_attendance = findViewById(R.id.take_attendance);
        faculty_attendance = findViewById(R.id.faculty_attendance);
        faculty_studentlist = findViewById(R.id.faculty_studentlist);
        faculty_courselist = findViewById(R.id.faculty_courselist);

        take_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(facultyLoginActivity.this, takeAttendanceActivity.class);
                startActivity(intent);
            }
        });
        faculty_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(facultyLoginActivity.this, viewAttendanceActivity.class);
                startActivity(intent);
            }
        });
        faculty_studentlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(facultyLoginActivity.this, studentListActivity.class);
                startActivity(intent);
            }
        });
        faculty_courselist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(facultyLoginActivity.this, courseListActivity.class);
                startActivity(intent);
            }
        });
    }
}