//SOURCES USED:
//https://stackoverflow.com/questions/5608720/android-preventing-double-click-on-a-button
//https://stackoverflow.com/questions/58908242/firebase-get-data-from-startdate-to-enddate
package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MonthYearCase extends AppCompatActivity {
    private static final String JANUARY = "January";
    private static final String FEBRUARY = "February";
    private static final String MARCH = "March";
    private static final String APRIL = "April";
    private static final String MAY = "May";
    private static final String JUNE = "June";
    private static final String JULY = "July";
    private static final String AUGUST = "August";
    private static final String SEPTEMBER = "September";
    private static final String OCTOBER = "October";
    private static final String NOVEMBER = "November";
    private static final String DECEMBER = "December";
    private static final String FIRST_DAY_OF_THE_MONTH = "-01";
    private static final String LAST_DAY_OF_THE_MONTH = "-31";

    private Spinner yearSpinner;
    private Spinner monthSpinner;
    private Button findBtn;
    private Button backBtn;
    private long mLastClickTime = 0;

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

        //Adapter to bridge all Cases from DB to listView
        casesLv = findViewById(R.id.casesListView);
        caseList = new ArrayList<Case>();

        yearSpinner = findViewById(R.id.yearSpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        findBtn = findViewById(R.id.queryFindButton);

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
//        databaseCOVIDCases.addValueEventListener(new ValueEventListener() {
//
//            //onDataChange triggers when any changes occur to the DB (CREATE/DELETE)
//            //It updates the listView with most recent view of the DB.
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                caseList.clear();
//
//                //SQL EQUIVALENT: SELECT* FROM covidCases
//                for (DataSnapshot caseSnapshot : dataSnapshot.getChildren()) {
//                    Case covidCase = caseSnapshot.getValue(Case.class);
//                    caseList.add(covidCase);
//                }
//
//                //Populates covidCases information to listView
//                CaseListAdapter adapter = new CaseListAdapter(MonthYearCase.this, caseList);
//                casesLv.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

    /**
     * An onClick method that queries the Database according to the Spinner values.
     *
     * @param v
     *      The current view.
     */
    public void onClickFindQuery(View v) {
        Toast.makeText(MonthYearCase.this, "Fetching data, please wait...", Toast.LENGTH_LONG).show();

        //Mis-clicking prevention, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        //SQL EQUIVALENT: SELECT* FROM covidCases
        //                WHERE Reported_Date BETWEEN '1996-07-01' AND '1996-07-31'
        String year = String.valueOf(yearSpinner.getSelectedItem());
        String month = convertMonth(String.valueOf(monthSpinner.getSelectedItem()));

        String formattedStartDate = year + month + FIRST_DAY_OF_THE_MONTH;
        String formattedEndDate = year + month + LAST_DAY_OF_THE_MONTH;

        System.out.println(formattedEndDate);

        Query searchQuery = FirebaseDatabase.getInstance().getReference().orderByChild("Reported_Date")
                            .startAt(formattedStartDate).endAt(formattedEndDate);

        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            //onDataChange triggers when any changes occur to the DB (CREATE/DELETE)
            //It updates the listView with most recent view of the DB.
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                caseList.clear();

                //SQL EQUIVALENT: SELECT* FROM covidCases
                for (DataSnapshot caseSnapshot : dataSnapshot.getChildren()) {
                    Case covidCase = caseSnapshot.getValue(Case.class);
                    caseList.add(covidCase);
                }

                //Populates covidCases information to listView
                CaseListAdapter adapter = new CaseListAdapter(MonthYearCase.this, caseList);
                casesLv.setAdapter(adapter);
                Toast.makeText(MonthYearCase.this, "Data has been successfully loaded!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MonthYearCase.this, "Data failed to load!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String convertMonth(String inputMonth) {
        switch (inputMonth) {
            case JANUARY:
                return "-01";
            case FEBRUARY:
                return "-02";
            case MARCH:
                return "-03";
            case APRIL:
                return "-04";
            case MAY:
                return "-05";
            case JUNE:
                return "-06";
            case JULY:
                return "-07";
            case AUGUST:
                return "-08";
            case SEPTEMBER:
                return "-09";
            case OCTOBER:
                return "-10";
            case NOVEMBER:
                return "-11";
            case DECEMBER:
                return "-12";
        }
        return null;
    }

}