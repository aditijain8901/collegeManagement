package com.example.attendance;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class takeAttendanceActivity extends AppCompatActivity {
    AutoCompleteTextView course;
    ImageView dropdown;
    Button mark;
    TextView show;
    EditText date;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        course = findViewById(R.id.course_attendance);
        dropdown = findViewById(R.id.arrow_down1);
        course.setThreshold(1);
        mark = findViewById(R.id.mark_attendance);
        show = findViewById(R.id.show_attendance);
        date = findViewById(R.id.date);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, course1);
        course.setAdapter(adapter);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course.showDropDown();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(takeAttendanceActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(day +"-"+(month+1)+"-"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(takeAttendanceActivity.this);
                String[] student_name  = new  String[] {"Aditi" , "Pihu" , "Vrati", "Tejasvini", "ABC","XYZ", "PQR"};
                final  boolean[] presentarray = new boolean[]
                        {
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,false};
                final List<String> studentlist = Arrays.asList(student_name);
                builder.setTitle("Select Present Students");
                builder.setMultiChoiceItems(student_name, presentarray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        presentarray[which] = isChecked;
                        String currentitem = studentlist.get(which);
                        Toast.makeText(takeAttendanceActivity.this, currentitem+""+isChecked, Toast.LENGTH_SHORT);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        show.setText("Present Students: \n");
                        for (int i =0; i<presentarray.length;i++){
                            boolean checked = presentarray[i];
                            if(checked){
                                show.setText(show.getText()+studentlist.get(i)+"\n");
                            }
                        }
                        Toast.makeText(takeAttendanceActivity.this, "Attendance Submitted", Toast.LENGTH_SHORT);
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    private static final String[] course1 = new String[] {"CSE", "CE" , "EE", "ME"};
}