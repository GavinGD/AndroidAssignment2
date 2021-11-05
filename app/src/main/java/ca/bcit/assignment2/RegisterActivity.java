package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText editEmail, editPassword, editFullName, editAge;
    Button btnRegister;
    //TextView tvLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editFullName = findViewById(R.id.fullName);
        editAge = findViewById(R.id.age);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);

        //Firebase authenticator
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

        //Checks if user is still logged in after backing out of app for some time
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        /***********EVENT HANDLERS**********/
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = editFullName.getText().toString().trim();
                String age = editAge.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    editFullName.setError("Full Name is required");
                    editFullName.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(age)) {
                    editAge.setError("Age is required");
                    editAge.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    editEmail.setError("Email is required");
                    editEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    editPassword.setError("Password is required");
                    editPassword.requestFocus();
                    return;
                }

                if (password.length() <6) {
                    editPassword.setError("Password must be >= 6 characters.");
                    editPassword.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register user
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "ERROR: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

//        tvLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//            }
//        });


    }
}