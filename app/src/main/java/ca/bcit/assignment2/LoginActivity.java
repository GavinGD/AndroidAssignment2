package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    //a) Private Variables for Firebase Login Initializer
    private FirebaseUser user;
    private FirebaseAuth mAuth; //Connects to Authorization DB.
    private DatabaseReference reference;
    String UID; //Every user has a unique User UID in Auth Database

    EditText editEmail;
    EditText editPassword;
    ProgressBar progressBar;
    Button btnLogin;
    TextView tvCreateNewAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //a) Getting credentials for validation
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        //b) mAuth gets the Firebase Authenticator object for validating user input credentials
        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);

        //**************************//LOGIN BUTTON//****************************//

        //c) Mapping login button with onClick Auth Validator with database (no need for android:onclick)
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }

            //Separate private helper for onClick event above (if not, crashes b/c simultaneous threads)
            private void userLogin() {
                //Getters for Email + Password fields
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if(email.isEmpty()) {
                    editEmail.setError("Email is required!");
                    editEmail.requestFocus();
                    return;
                }

                //Checks if email address matches correct format (Ex: @mail.com)
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editEmail.setError("Please enter a valid email address");
                    editEmail.requestFocus();
                    return;
                }

                if(password.isEmpty()) {
                    editPassword.setError("Password is required!");
                    editPassword.requestFocus();
                    return;
                }

                //NOTE: Has to check if password length is GREATER than 6 because
                //      all firebase passwords has length greater than 6.
                if(password.length() < 6) {
                    editPassword.setError("Min. Password must be >= 6 characters.");
                    editPassword.requestFocus();
                    return;
                }

                //Set progress bar to be seen
                progressBar.setVisibility(View.VISIBLE);

                //IMPORTANT TASK BELOW: Uses mAuth for Validation with Firebase Auth DB
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If user has successfully logged in and sends them to MainActivity
                        if(task.isSuccessful()) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed! Please enter credentials again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //********************//END OF LOGIN BUTTON//***************************//


        //**************************//REGISTER ACCOUNT BUTTON//****************************//

        //d) When user clicks on Create New Account -> RegisterActivity
        tvCreateNewAccount = findViewById(R.id.tvCreateNewAccount);
        tvCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        //**************************//END OF REGISTER ACCOUNT BUTTON//****************************//
    }
}