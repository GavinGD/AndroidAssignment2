//SOURCES USED: https://www.py4u.net/discuss/649798
package ca.bcit.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.List;

public class HealthAuthorityCase extends AppCompatActivity {
    private TextView fraserHA;
    private TextView interiorHA;
    private TextView northernHA;
    private TextView outOfCanadaHA;
    private TextView vanCoastalHA;
    private TextView vanIslandHA;
    private Button backButton;

    //Case Count for each Health Authority
    private static long FRASER_CASE_COUNT;
    private static long INTERIOR_CASE_COUNT;
    private static long NORTHERN_CASE_COUNT;
    private static long OUT_OF_CAN_CASE_COUNT;
    private static long VANCOUVER_COASTAL_CASE_COUNT;
    private static long VANCOUVER_ISLAND_CASE_COUNT;


    private DatabaseReference databaseCOVIDCases;
    private List<Case> caseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_authority_case);
        Toast.makeText(HealthAuthorityCase.this, "Fetching data, please wait...", Toast.LENGTH_LONG).show();

        //All of the textViews
        fraserHA = findViewById(R.id.fraserHA);
        interiorHA = findViewById(R.id.interiorHA);
        northernHA = findViewById(R.id.northernHA);
        outOfCanadaHA = findViewById(R.id.outOfCanadaHA);
        vanCoastalHA = findViewById(R.id.vanCoastalHA);
        vanIslandHA = findViewById(R.id.vanIslandHA);

        //a) Set visibility off (to hide how Query works :D )
        fraserHA.setVisibility(View.GONE);
        interiorHA.setVisibility(View.GONE);
        northernHA.setVisibility(View.GONE);
        outOfCanadaHA.setVisibility(View.GONE);
        vanCoastalHA.setVisibility(View.GONE);
        vanIslandHA.setVisibility(View.GONE);

        //Creates a reference to COVID collections from database
        databaseCOVIDCases = FirebaseDatabase.getInstance().getReference();

        //IMPORTANT: Always initialize arrayList before use (otherwise crash)
        caseList = new ArrayList<Case>();

        //******************************//1) FRASER CASE COUNT QUERY//****************************//
        Query fraserQuery = databaseCOVIDCases.orderByChild("HA").equalTo("Fraser");
        fraserQuery.addListenerForSingleValueEvent(eventListenerFraser);
        //********************************//END OF FRASER CASE COUNT QUERY//****************************//

        //******************************//2) Interior CASE COUNT QUERY//****************************//
        Query interiorQuery = databaseCOVIDCases.orderByChild("HA").equalTo("Interior");
        interiorQuery.addListenerForSingleValueEvent(eventListenerInterior);
        //********************************//END OF Interior CASE COUNT QUERY//****************************//

        //******************************//3) Northern CASE COUNT QUERY//****************************//
        Query northernQuery = databaseCOVIDCases.orderByChild("HA").equalTo("Northern");
        northernQuery.addListenerForSingleValueEvent(eventListenerNorthern);
        //********************************//END OF Northern CASE COUNT QUERY//****************************//

        //******************************//4) Out of Canada CASE COUNT QUERY//****************************//
        Query outOfCanQuery = databaseCOVIDCases.orderByChild("HA").equalTo("Out of Canada");
        outOfCanQuery.addListenerForSingleValueEvent(eventListenerOutOfCanada);
        //********************************//END OF Out of Canada CASE COUNT QUERY//****************************//

        //******************************//5) Vancouver Coastal CASE COUNT QUERY//****************************//
        Query vanCoastalQuery = databaseCOVIDCases.orderByChild("HA").equalTo("Vancouver Coastal");
        vanCoastalQuery.addListenerForSingleValueEvent(eventListenerVanCoastal);
        //********************************//END OF Vancouver Coastal CASE COUNT QUERY//****************************//

        //******************************//6) Vancouver Island CASE COUNT QUERY//****************************//
        Query vanIslandQuery = databaseCOVIDCases.orderByChild("HA").equalTo("Vancouver Island");
        vanIslandQuery.addListenerForSingleValueEvent(eventListenerVanIsland);
        //********************************//END OF Vancouver Island CASE COUNT QUERY//****************************//

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * An query event listener that gets the total case count from the Fraser health authority.
     */
    ValueEventListener eventListenerFraser = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            caseList.clear();
            FRASER_CASE_COUNT = dataSnapshot.getChildrenCount();

            String fraserCount = getString(R.string.fraserHeader, Long.toString(FRASER_CASE_COUNT));
            fraserHA.setText(fraserCount);
            fraserHA.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(HealthAuthorityCase.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total case count from the Interior health authority.
     */
    ValueEventListener eventListenerInterior = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            caseList.clear();
            INTERIOR_CASE_COUNT = dataSnapshot.getChildrenCount();

            String interiorCount = getString(R.string.interiorHeader, Long.toString(INTERIOR_CASE_COUNT));
            interiorHA.setText(interiorCount);
            interiorHA.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(HealthAuthorityCase.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total case count from the Northern health authority.
     */
    ValueEventListener eventListenerNorthern = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            caseList.clear();
            NORTHERN_CASE_COUNT = dataSnapshot.getChildrenCount();

            String northernCount = getString(R.string.northernHeader, Long.toString(NORTHERN_CASE_COUNT));
            northernHA.setText(northernCount);
            northernHA.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(HealthAuthorityCase.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total case count from any health authorities outside of Canada.
     */
    ValueEventListener eventListenerOutOfCanada = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            caseList.clear();
            OUT_OF_CAN_CASE_COUNT = dataSnapshot.getChildrenCount();

            String outOfCanCount = getString(R.string.outOfCanadaHeader, Long.toString(OUT_OF_CAN_CASE_COUNT));
            outOfCanadaHA.setText(outOfCanCount);
            outOfCanadaHA.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(HealthAuthorityCase.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total case count from the Vancouver Coastal authority.
     */
    ValueEventListener eventListenerVanCoastal = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            caseList.clear();
            VANCOUVER_COASTAL_CASE_COUNT = dataSnapshot.getChildrenCount();

            String vanCoastalCount = getString(R.string.vanCoastalHeader, Long.toString(VANCOUVER_COASTAL_CASE_COUNT));
            vanCoastalHA.setText(vanCoastalCount);
            vanCoastalHA.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(HealthAuthorityCase.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * An query event listener that gets the total case count from the Vancouver Island authority.
     */
    ValueEventListener eventListenerVanIsland = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            caseList.clear();
            VANCOUVER_ISLAND_CASE_COUNT = dataSnapshot.getChildrenCount();

            String vanIslandCount = getString(R.string.vanCoastalHeader, Long.toString(VANCOUVER_ISLAND_CASE_COUNT));
            vanIslandHA.setText(vanIslandCount);
            vanIslandHA.setVisibility(View.VISIBLE);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(HealthAuthorityCase.this, "Data failed to load!", Toast.LENGTH_LONG).show();
        }
    };

}