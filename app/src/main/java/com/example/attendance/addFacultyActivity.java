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

public class addFacultyActivity extends AppCompatActivity {

    private static final String TAG = "addFacultyActivity";

    AutoCompleteTextView course_faculty;
    ImageView dropdown;
    EditText faculty_name, faculty_user, faculty_pass, typeofuser;
    Button submit;

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_COURSE = "course";
    private static final String KEY_TYPEUSER = "typeuser";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);
        submit = findViewById(R.id.faculty_submit);
        course_faculty = findViewById(R.id.course_faculty);
        dropdown = findViewById(R.id.arrow_down);
        faculty_name = findViewById(R.id.faculty_name);
        faculty_user = findViewById(R.id.faculty_user);
        faculty_pass = findViewById(R.id.faculty_pass);
        typeofuser = findViewById(R.id.typeuser_faculty);
        course_faculty.setThreshold(1);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, course);
        course_faculty.setAdapter(adapter);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course_faculty.showDropDown();
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
        submit.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(addFacultyActivity.this, R.style.AlertDialogLogin);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Authenticating...");
        progressDialog.setMessage("Loading");
        progressDialog.show();

        String name = faculty_name.getText().toString();
        String username = faculty_user.getText().toString();
        String password = faculty_pass.getText().toString();
        String course =  course_faculty.getText().toString();
        String typeuser = typeofuser.getText().toString();

        Map<String, Object> detail = new HashMap<>();
        detail.put(KEY_USERNAME,username);
        detail.put(KEY_PASSWORD, password);
        detail.put(KEY_COURSE, course);
        detail.put(KEY_TYPEUSER, typeuser);
        db.collection("Faculty").document(name).set(detail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(addFacultyActivity.this, "Faculty Added", Toast.LENGTH_SHORT).show();
                        onLoginSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addFacultyActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG,e.toString());
                    }
                });
    }

    public boolean validate() {
        boolean valid = true;

        String name = faculty_name.getText().toString().trim();
        String pass = faculty_pass.getText().toString().trim();
        String user = faculty_user.getText().toString().trim();
        String cour = course_faculty.getText().toString().trim();
        if (user.isEmpty()) {
            faculty_user.setError("Enter Username");
            valid = false;
        } else {
            faculty_user.setError(null);
        }

        if (pass.isEmpty()) {
            faculty_pass.setError("Enter Password");
            valid = false;
        } else {
            faculty_pass.setError(null);
        }
        if (name.isEmpty()) {
            faculty_name.setError("Enter Name");
            valid = false;
        }
        else {
            faculty_name.setError(null);
        }
        if (cour.isEmpty()) {
            course_faculty.setError("Select Course");
            valid = false;
        }
        else {
            course_faculty.setError(null);
        }
        return valid;
    }
    public void onLoginSuccess() {
        Toast.makeText(addFacultyActivity.this, "Faculty Added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(addFacultyActivity.this, adminLoginActivity.class);
        startActivity(intent);
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Adding Faculty Failed", Toast.LENGTH_LONG).show();
        submit.setEnabled(true);
    }
    private static final String[] course = new String[] {"CSE", "CE" , "EE", "ME"};

}