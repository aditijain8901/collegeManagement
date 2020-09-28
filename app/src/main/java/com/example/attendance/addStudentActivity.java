package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addStudentActivity extends AppCompatActivity {
    private static final String TAG = "addStudentActivity";
    
    AutoCompleteTextView course_student;
    ImageView dropdown;
    EditText studentname, studentuser, studentpass, typeofuser;
    Button submit;
    public static final String KEY_NAME = "name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_COURSE = "course";
    public static final String KEY_TYPEUSER = "typeuser";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        course_student = findViewById(R.id.course_student);
        dropdown = findViewById(R.id.arrow_dropdown);
        studentname = findViewById(R.id.student_name);
        studentpass = findViewById(R.id.student_pass);
        studentuser = findViewById(R.id.student_user);
        submit = findViewById(R.id.student_submit);
        typeofuser = findViewById(R.id.typeuser_student);
        course_student.setThreshold(1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, course);
        course_student.setAdapter(adapter);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course_student.showDropDown();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginfun();
            }
        });
    }

    public void loginfun() {
        if (!validate()) {
            onLoginFailed();
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(addStudentActivity.this, R.style.AlertDialogLogin);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Authenticating...");
        progressDialog.setMessage("Loading");
        progressDialog.show();

        String name = studentname.getText().toString();
        String username = studentuser.getText().toString();
        String password = studentpass.getText().toString();
        String course =  course_student.getText().toString();
        String typeuser = typeofuser.getText().toString();

        constants constants = new constants(name, course, username , password,typeuser);


        /*Map<String, Object> detail = new HashMap<>();
        detail.put(KEY_USERNAME,username);
        detail.put(KEY_PASSWORD, password);
        detail.put(KEY_COURSE, course);
        detail.put(KEY_TYPEUSER, typeuser);*/

        db.collection("Students").document(name).set(constants)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(addStudentActivity.this, "Student Added", Toast.LENGTH_SHORT).show();
                        onLoginSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addStudentActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,e.toString());
                    }
                });
        }



    public boolean validate() {
        boolean valid = true;

        String name = studentname.getText().toString().trim();
        String pass = studentpass.getText().toString().trim();
        String user = studentuser.getText().toString().trim();
        String cour = course_student.getText().toString().trim();
        if (user.isEmpty()) {
            studentuser.setError("Enter Username");
            valid = false;
        } else {
            studentuser.setError(null);
        }

        if (pass.isEmpty()) {
            studentpass.setError("Enter Password");
            valid = false;
        } else {
            studentpass.setError(null);
        }
        if (name.isEmpty()) {
            studentname.setError("Enter Name");
            valid = false;
        } else {
            studentname.setError(null);
        }
        if (cour.isEmpty()) {
            course_student.setError("Select Course");
            valid = false;
        } else {
            course_student.setError(null);
        }
        return valid;
    }

    public void onLoginSuccess() {
        Intent intent = new Intent(addStudentActivity.this, adminLoginActivity.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Adding Student Failed", Toast.LENGTH_LONG).show();
        submit.setEnabled(true);
    }
    private static final String[] course = new String[] {"CSE", "CE" , "EE", "ME"};

}