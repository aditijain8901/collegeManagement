package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.SyncStateContract;
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

public class studentListActivity extends AppCompatActivity {

    private static final String TAG = "studentListActivity";

    TextView show;
    Button view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference studentref = db.collection("Students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        show = findViewById(R.id.showstudent);
        view = findViewById(R.id.btn_view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdata();
            }
        });
    }

    public void showdata() {
        studentref.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            constants showlist = documentSnapshot.toObject(com.example.attendance.constants.class);

                            String name = showlist.getName();
                            String course = showlist.getCourse();
                            data += "Name: " + name +"\nCourse: " + course + "\n\n";
                            Toast.makeText(studentListActivity.this ,"Success" , Toast.LENGTH_SHORT).show();
                        }
                        show.setText(data);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(studentListActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG,e.toString());
            }
        });
    }
}