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
    private Button logOut;
    private Button caseMonthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        //a) View components
        emailText = findViewById(R.id.emailTv);

        caseMonthYear = findViewById(R.id.caseMonthYear);
        caseMonthYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MonthYearCase.class));
            }
        });

        //a) Logout button and its onClick event
        logOut = findViewById(R.id.btn_logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        //Checks if user currently logged in on start of mainActivity
        if(mFirebaseUser != null) {
            //Gets user email and displays it to textView
            emailText.setText(mFirebaseUser.getEmail());
        } else {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        }
    }

}