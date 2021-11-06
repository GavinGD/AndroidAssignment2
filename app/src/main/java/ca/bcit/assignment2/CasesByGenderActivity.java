package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CasesByGenderActivity extends AppCompatActivity {

    private TextView maleCases;
    private TextView femaleCases;
    private Button backBtn;
    private ProgressBar progressBar;

    // Count for male and female cases
    private static long MALE_CASES;
    private static long FEMALE_CASES;

    private DatabaseReference databaseCOVIDCases;
    private List<Case> caseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cases_by_gender);

        caseList = new ArrayList<>();
        maleCases = findViewById(R.id.maleCases);
        femaleCases = findViewById(R.id.femaleCases);
        backBtn = findViewById(R.id.backButton);
        progressBar  = findViewById(R.id.indeterminateBar);
        databaseCOVIDCases = FirebaseDatabase.getInstance().getReference();

        /* Male Count Query */
        Query maleQuery = databaseCOVIDCases.orderByChild("Sex").equalTo("M");
        maleQuery.addListenerForSingleValueEvent(eventListenerMaleCases);

        /* Female Count Query */
        Query femaleQuery = databaseCOVIDCases.orderByChild("Sex").equalTo("F");
        femaleQuery.addListenerForSingleValueEvent(eventListenerFemaleCases);

        backBtn.setOnClickListener(view -> {
            finish();
        });

    }

    ValueEventListener eventListenerMaleCases = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            caseList.clear();
            MALE_CASES = snapshot.getChildrenCount();
            String maleCount = getString(R.string.maleCasesHeader, Long.toString(MALE_CASES));
            maleCases.setText(maleCount);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {}
    };

    ValueEventListener eventListenerFemaleCases = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            caseList.clear();
            FEMALE_CASES = snapshot.getChildrenCount();
            String femaleCount = getString(R.string.femaleCasesHeader, Long.toString(FEMALE_CASES));
            femaleCases.setText(femaleCount);

            /* Last data base query here so set visibility to GONE here */
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {}
    };


}