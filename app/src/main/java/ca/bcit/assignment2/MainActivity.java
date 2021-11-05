package ca.bcit.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private TextView emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = findViewById(R.id.emailTv);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //Checks if user currently logged in on start of mainActivity
        if(mFirebaseUser != null) {
            //Gets user email and displays it to textView
            emailText.setText(mFirebaseUser.getDisplayName());
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void logout(View view) {
        mFirebaseAuth.signOut();
    }
}