package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class viewAttendanceActivity extends AppCompatActivity {
    private static final String TAG = "viewAttendanceActivity";

    Button show;
    TextView showattendance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference attendanceref = db.collection("Attendance");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        show = findViewById(R.id.btn_show);
        showattendance = findViewById(R.id.showattendance);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onshow();
            }
        });
    }
    public void onshow() {
        attendanceref.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot: queryDocumentSnapshots) {
                            attendconst showdata = documentSnapshot.toObject(attendconst.class);

                            String date = showdata.getDateofattendance();
                            String course = showdata.getCourseofattendace();
                            String students = showdata.getStudents();
                            data += "Date: " + date +"\nCourse: " + course + "\n"+students+ "\n\n";
                            Toast.makeText(viewAttendanceActivity.this ,"Success" , Toast.LENGTH_SHORT).show();
                        }
                        showattendance.setText(data);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(viewAttendanceActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG,e.toString());
            }
        });
    }
}