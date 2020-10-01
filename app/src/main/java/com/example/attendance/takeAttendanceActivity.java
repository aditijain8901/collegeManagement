package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class takeAttendanceActivity extends AppCompatActivity {
    private static final String TAG = "takeAttendanceActivity";

    AutoCompleteTextView course;
    ImageView dropdown;
    Button mark;
    TextView show;
    EditText date;
    DatePickerDialog datePickerDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> mstudentlist;
    String student, students;

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
                        date.setText(dayOfMonth +"-"+(month+1)+"-"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    onLoginFailed();
                    return;
                }
                takeattendance();
            }
        });
    }
    public void takeattendance() {

        String coursename = course.getText().toString();
        String attendance_date = date.getText().toString();
        mstudentlist = new ArrayList<>();

        db.collection("Students").whereEqualTo("course", coursename).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot: snapshotList) {
                            student = snapshot.getString("name");
                            student.toString();
                            mstudentlist.add(student);
                        }
                        Log.d(TAG, "onSuccess: "+mstudentlist);
                        AlertDialog.Builder builder = new AlertDialog.Builder(takeAttendanceActivity.this);
                        String[] student_name = mstudentlist.toArray(new String[mstudentlist.size()]);
                        Log.d(TAG, "onSuccess: "+student_name);
                        final boolean[] presentarray = new boolean[] {
                                false,false,false, false,false,false,false,false,false
                        };
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
                                        show.setText(show.getText()+studentlist.get(i)+","+"\n");
                                    }
                                }
                                students = show.getText().toString();
                                attendconst cons = new attendconst(attendance_date,coursename,students);
                                db.collection("Attendance").document(attendance_date).set(cons)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(takeAttendanceActivity.this, "Attendance Added", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "onFailure: ",e );
                                    }
                                });
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
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e);
            }
        });
    }
    public boolean validate() {
        boolean valid = true;
        String coursename = course.getText().toString();
        String attendance_date = date.getText().toString();

        if (coursename.isEmpty()) {
            course.setError("Select Course");
            valid = false;
        } else {
            course.setError(null);
        }

        if (attendance_date.isEmpty()) {
            date.setError("Select Date");
            valid = false;
        } else {
            date.setError(null);
        }
        return valid;
    }

        public void onLoginFailed() {
        Toast.makeText(takeAttendanceActivity.this, "Error Submitting Attendance" , Toast.LENGTH_SHORT).show();
        }
    private static final String[] course1 = new String[] {"CSE", "CE" , "EE", "ME"};
}