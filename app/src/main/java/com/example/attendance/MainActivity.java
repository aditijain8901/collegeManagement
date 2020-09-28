package com.example.attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    LinearLayout linearLayout;
    AutoCompleteTextView loginas;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_pass);
        login = (Button) findViewById(R.id.btn_login);
        loginas = findViewById(R.id.input_loginas);
        ImageView image = findViewById(R.id.image);
        loginas.setThreshold(1);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, user);
        loginas.setAdapter(adapter);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginas.showDropDown();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
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
        login.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.AlertDialogLogin);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Authenticating...");
        progressDialog.setMessage("Loading");
      //  progressDialog.show();

        String user = username.getText().toString();
        String pass = password.getText().toString();


        if (loginas.getText().toString().equals("Admin")) {
            db.collection("Admin").document("admin").get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document!=null) {
                                    if (document.exists()) {
                                        String user_check = document.getString("username");
                                      //  constants constants = document.toObject(com.example.attendance.constants.class);
                                        if (user.equals(user_check)) {
                                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            onLoginSuccess();
                                        }
                                        else {
                                            Toast.makeText(MainActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                                         //   progressDialog.dismiss();
                                            onLoginFailed();
                                        }
                                    }else {
                                        Toast.makeText(MainActivity.this, "This User is not registered", Toast.LENGTH_SHORT).show();
                                     //   progressDialog.dismiss();
                                        onLoginFailed();
                                    }
                                }
                            }
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Toast.makeText(MainActivity.this, "Check Internet Connection ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    onLoginFailed();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Try Again Later", Toast.LENGTH_SHORT).show();
                  //  progressDialog.dismiss();
                    onLoginFailed();
                }
            });
        }
        else if (loginas.getText().toString().equals("Faculty")) {
            db.collection("Admin").document().get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document!=null) {
                                    if (document.exists()) {
                                        String uer_check = document.getString("username");
                                        //  constants constants = document.toObject(com.example.attendance.constants.class);
                                        if (user.equals(uer_check)) {
                                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            onLoginSuccess();
                                        }
                                        else {
                                            Toast.makeText(MainActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                                            //   progressDialog.dismiss();
                                            onLoginFailed();
                                        }
                                    }else {
                                        Toast.makeText(MainActivity.this, "This User is not registered", Toast.LENGTH_SHORT).show();
                                        //   progressDialog.dismiss();
                                        onLoginFailed();
                                    }
                                }
                            }
                        }
                    }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Toast.makeText(MainActivity.this, "Check Internet Connection ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    onLoginFailed();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Try Again Later", Toast.LENGTH_SHORT).show();
                    //  progressDialog.dismiss();
                    onLoginFailed();
                }
            });
        }

    }

    public void onLoginSuccess() {
        if (loginas.getText().toString().equals("Admin")) {
            Toast.makeText(MainActivity.this, "Logged in as Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, adminLoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if (loginas.getText().toString().equals("Faculty")) {
            Toast.makeText(MainActivity.this, "Logged in as Faculty", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, facultyLoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if (loginas.getText().toString().equals("Student")) {
            Toast.makeText(MainActivity.this, "Logged in as Student", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, studentLoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Please choose a option", Toast.LENGTH_SHORT).show();

        }
    }
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
        login.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String log = loginas.getText().toString().trim();
        if (user.isEmpty()) {
            username.setError("Enter Username");
            valid = false;
        } else {
            username.setError(null);
        }

        if (pass.isEmpty()) {
            password.setError("Enter Password");
            valid = false;
        } else {
            password.setError(null);
        }
        if (log.isEmpty()) {
            loginas.setError("Select Type of User");
            valid = false;
        }
        else {
            loginas.setError(null);
        }
        return valid;
    }
    private static final String[] user = new String[] {"Admin" , "Faculty" , "Student"};
}