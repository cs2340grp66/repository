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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewReports extends AppCompatActivity {

    private ListView listView;

    private ArrayList<String> report = new ArrayList<>();

    private ImageButton goBack;

    private DatabaseReference databaseReference;

    public static String clickedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://water-report-cf485.firebaseio.com/reports");

        listView = (ListView) findViewById(R.id.report_list);

        final ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, report);

        listView.setAdapter(arrayAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String name = dataSnapshot.child(child.getKey()).child("repoterName").getValue(String.class);
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
                String index = Character.toString(clickedItem.charAt(8));
                Intent i = new Intent(getApplicationContext(), ReportDetailActivity.class);
                i.putExtra("index", index);
                startActivity(i);
            }
        });





    }


    /**
     *
     * @param view
     */
    public void goBack_from_viewReport(View view) {
        startActivity(new Intent(ViewReports.this, WelcomActivity.class));
    }
}
