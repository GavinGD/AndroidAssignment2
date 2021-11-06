package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AgeGroupCaseActivity extends AppCompatActivity {
    private TextView lessTen;
    private TextView tenTo19;
    private TextView twentyTo29;
    private TextView thirtyTo39;
    private TextView fortyTo49;
    private TextView fiftyTo59;
    private TextView sixtyTo69;
    private TextView seventyTo79;
    private TextView eightyTo89;
    private TextView ninetyPlus;
    private Button backButton;

    //Database object for querying
    private DatabaseReference databaseCOVIDCases;

    //Statics for age group case count
    private static long LESS_THAN_TEN_COUNT;
    private static long TEN_TO_19_COUNT;
    private static long TWENTY_TO_29_COUNT;
    private static long THIRTY_TO_39_COUNT;
    private static long FORTY_TO_49_COUNT;
    private static long FIFTY_TO_59_COUNT;
    private static long SIXTY_TO_69_COUNT;
    private static long SEVENTY_TO_79_COUNT;
    private static long EIGHTY_TO_89_COUNT;
    private static long NINENTY_PLUS_COUNT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_group_case);
        Toast.makeText(AgeGroupCaseActivity.this, "Fetching data, please wait...", Toast.LENGTH_LONG).show();

        //a) All textView components
        lessTen = findViewById(R.id.lessThanTen);
        tenTo19 = findViewById(R.id.tenTo19);
        twentyTo29 = findViewById(R.id.twentyTo29);
        thirtyTo39 = findViewById(R.id.thirtyTo39);
        fortyTo49 = findViewById(R.id.fortyTo49);
        fiftyTo59 = findViewById(R.id.fiftyTo59);
        sixtyTo69 = findViewById(R.id.sixtyTo69);
        seventyTo79 = findViewById(R.id.seventyTo79);
        eightyTo89 = findViewById(R.id.eightyTo89);
        ninetyPlus = findViewById(R.id.ninetyPlus);

        //b) Set visibility off (to hide TextView placeholders)
        lessTen.setVisibility(View.GONE);
        tenTo19.setVisibility(View.GONE);
        twentyTo29.setVisibility(View.GONE);
        thirtyTo39.setVisibility(View.GONE);
        fortyTo49.setVisibility(View.GONE);
        fiftyTo59.setVisibility(View.GONE);
        sixtyTo69.setVisibility(View.GONE);
        seventyTo79.setVisibility(View.GONE);
        eightyTo89.setVisibility(View.GONE);
        ninetyPlus.setVisibility(View.GONE);

        //c) Creates a reference to COVID collections from database
        databaseCOVIDCases = FirebaseDatabase.getInstance().getReference();

        //**************************//QUERYING//************************
        Query lessTenQuery = databaseCOVIDCases.orderByChild("Age_Group")
                             .equalTo("<10");
        lessTenQuery.addListenerForSingleValueEvent(eventListenerAgesTenBelow);

        Query tenTo19Query = databaseCOVIDCases.orderByChild("Age_Group")
                            .equalTo("10-19");
        tenTo19Query.addListenerForSingleValueEvent(eventListenerAgesTenTo19);

        Query twentyTo29Query = databaseCOVIDCases.orderByChild("Age_Group")
                                .equalTo("20-29");
        twentyTo29Query.addListenerForSingleValueEvent(eventListenerAgesTwentyTo29);

        Query thirtyTo39Query = databaseCOVIDCases.orderByChild("Age_Group")
                                .equalTo("30-39");
        thirtyTo39Query.addListenerForSingleValueEvent(eventListenerAgesThirtyTo39);

        Query fortyTo49Query = databaseCOVIDCases.orderByChild("Age_Group")
                               .equalTo("40-49");
        fortyTo49Query.addListenerForSingleValueEvent(eventListenerAgesFortyTo49);

        Query fiftyTo59Query = databaseCOVIDCases.orderByChild("Age_Group")
                               .equalTo("50-59");
        fiftyTo59Query.addListenerForSingleValueEvent(eventListenerAgesFiftyTo59);

        Query sixtyTo69Query = databaseCOVIDCases.orderByChild("Age_Group")
                               .equalTo("60-69");
        sixtyTo69Query.addListenerForSingleValueEvent(eventListenerAgesSixtyTo69);

        Query seventyTo79Query = databaseCOVIDCases.orderByChild("Age_Group")
                                .equalTo("70-79");
        seventyTo79Query.addListenerForSingleValueEvent(eventListenerAgesSeventyTo79);

        Query eightyTo89Query = databaseCOVIDCases.orderByChild("Age_Group")
                .equalTo("80-89");
        eightyTo89Query.addListenerForSingleValueEvent(eventListenerAgesEightyTo89);

        Query ninetyTo90Query = databaseCOVIDCases.orderByChild("Age_Group")
                .equalTo("90+");
        ninetyTo90Query.addListenerForSingleValueEvent(eventListenerAgesNinetyPlus);

        //**********************//END OF QUERYING//*********************

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * An query event listener that gets the total case count for ages < 10.
     */
    ValueEventListener eventListenerAgesTenBelow = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            LESS_THAN_TEN_COUNT = dataSnapshot.getChildrenCount();

            String counter = getString(R.string.ages10BelowHeader, Long.toString(LESS_THAN_TEN_COUNT));
            lessTen.setText(counter);
            lessTen.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(AgeGroupCaseActivity.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total for a particular ages 10-19.
     */
    ValueEventListener eventListenerAgesTenTo19 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            TEN_TO_19_COUNT = dataSnapshot.getChildrenCount();

            String counter = getString(R.string.agesTenTo19Header, Long.toString(TEN_TO_19_COUNT));
            tenTo19.setText(counter);
            tenTo19.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(AgeGroupCaseActivity.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total for a particular ages 20-29.
     */
    ValueEventListener eventListenerAgesTwentyTo29 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            TWENTY_TO_29_COUNT = dataSnapshot.getChildrenCount();

            String counter = getString(R.string.agesTwentyTo29Header, Long.toString(TWENTY_TO_29_COUNT));
            twentyTo29.setText(counter);
            twentyTo29.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(AgeGroupCaseActivity.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total for a particular ages 30-39.
     */
    ValueEventListener eventListenerAgesThirtyTo39 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            THIRTY_TO_39_COUNT = dataSnapshot.getChildrenCount();

            String counter = getString(R.string.agesThirtyTo39Header, Long.toString(THIRTY_TO_39_COUNT));
            thirtyTo39.setText(counter);
            thirtyTo39.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(AgeGroupCaseActivity.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total for a particular ages 40-49.
     */
    ValueEventListener eventListenerAgesFortyTo49 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            FORTY_TO_49_COUNT = dataSnapshot.getChildrenCount();

            String counter = getString(R.string.agesFortyTo49Header, Long.toString(FORTY_TO_49_COUNT));
            fortyTo49.setText(counter);
            fortyTo49.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(AgeGroupCaseActivity.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total for a particular ages 50-59.
     */
    ValueEventListener eventListenerAgesFiftyTo59 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            FIFTY_TO_59_COUNT = dataSnapshot.getChildrenCount();

            String counter = getString(R.string.agesFiftyTo59Header, Long.toString(FIFTY_TO_59_COUNT));
            fiftyTo59.setText(counter);
            fiftyTo59.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(AgeGroupCaseActivity.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total for a particular ages 60-69.
     */
    ValueEventListener eventListenerAgesSixtyTo69 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            SIXTY_TO_69_COUNT = dataSnapshot.getChildrenCount();

            String counter = getString(R.string.agesSixtyTo69Header, Long.toString(SIXTY_TO_69_COUNT));
            sixtyTo69.setText(counter);
            sixtyTo69.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(AgeGroupCaseActivity.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total for a particular ages 70-79.
     */
    ValueEventListener eventListenerAgesSeventyTo79 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            SEVENTY_TO_79_COUNT = dataSnapshot.getChildrenCount();

            String counter = getString(R.string.agesSeventyTo79Header, Long.toString(SEVENTY_TO_79_COUNT));
            seventyTo79.setText(counter);
            seventyTo79.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(AgeGroupCaseActivity.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total for a particular ages 80-89.
     */
    ValueEventListener eventListenerAgesEightyTo89 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            EIGHTY_TO_89_COUNT = dataSnapshot.getChildrenCount();

            String counter = getString(R.string.agesEightyTo89Header, Long.toString(EIGHTY_TO_89_COUNT));
            eightyTo89.setText(counter);
            eightyTo89.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(AgeGroupCaseActivity.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total for a particular ages 90+.
     */
    ValueEventListener eventListenerAgesNinetyPlus = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            NINENTY_PLUS_COUNT = dataSnapshot.getChildrenCount();

            String counter = getString(R.string.ages90PlusHeader, Long.toString(NINENTY_PLUS_COUNT));
            ninetyPlus.setText(counter);
            ninetyPlus.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(AgeGroupCaseActivity.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };
}