package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.weichen.water_report.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quality_Report_LIst_Activity extends AppCompatActivity {

    private ListView listView;

    private ArrayList<String> report = new ArrayList<>();

    private ImageButton goBack;

    private DatabaseReference databaseReference;
    private FirebaseUser user;


    public static String clickedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quality__report__list_);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://water-report-cf485.firebaseio.com/quality_report");

        listView = (ListView) findViewById(R.id.quality_report_list);

        final ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, report);

        listView.setAdapter(arrayAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String name = dataSnapshot.child(child.getKey()).child("reporter").getValue(String.class);
                    String date = dataSnapshot.child(child.getKey()).child("date").getValue(String.class);
                    String value = "Report #" + child.getKey() + "\n" +"Reporter: " + name +"\nDate: " + date;
                    report.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                clickedItem =(String) (listView.getItemAtPosition(position));
                String indeX = Character.toString(clickedItem.charAt(8));
                Intent i = new Intent(getApplicationContext(), Quality_Report_details_Activity.class);
                i.putExtra("indeX", indeX);
                startActivity(i);
            }
        });
    }

    /**
     * using to go back from viewReport activity by classes
     * @param view
     */
    public void goBack_from_quality_report_list(View view) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String classes = dataSnapshot.child("classes").getValue(String.class);

                if (classes.equals("USER")){
                    startActivity(new Intent(Quality_Report_LIst_Activity.this, WelcomActivity.class));
                } else if (classes.equals("WORKER")){
                    startActivity(new Intent(Quality_Report_LIst_Activity.this, Worker_Welcome_Activity.class));
                } else if (classes.equals("MANAGER")){
                    startActivity(new Intent(Quality_Report_LIst_Activity.this, Manager_Welcome_Activity.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
