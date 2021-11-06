package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MonthYearCase extends AppCompatActivity {
    private Spinner year;
    private Spinner month;
    private Button findBtn;
    private Button backBtn;

    //a) Requirements for ListView
    private ListView casesLv;
    private List<Case> caseList;
    private DatabaseReference databaseCOVIDCases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_year_case);

        //Creates a reference to COVID collections from database
        databaseCOVIDCases = FirebaseDatabase.getInstance().getReference();

        year = findViewById(R.id.yearSpinner);
        month = findViewById(R.id.monthSpinner);
        findBtn = findViewById(R.id.queryFindButton);

        //Adapter to bridge all Cases from DB to listView
        casesLv = findViewById(R.id.casesListView);
        caseList = new ArrayList<Case>();

        // Back Button
        backBtn = findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Overrided onStart method that submits a GET request for COVID data
     * within the DB.
     *
     * It will then populate the listView with the Cases from the DB.
     */
    @Override
    protected void onStart() {
        super.onStart();
        databaseCOVIDCases.addValueEventListener(new ValueEventListener() {

            //onDataChange triggers when any changes occur to the DB (CREATE/DELETE)
            //It updates the listView with most recent view of the DB for student collection
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                caseList.clear();
                for (DataSnapshot caseSnapshot : dataSnapshot.getChildren()) {
                    Case covidCase = caseSnapshot.getValue(Case.class);
                    caseList.add(covidCase);
                }

                //Populates student information to listView
                CaseListAdapter adapter = new CaseListAdapter(MonthYearCase.this, caseList);
                casesLv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}