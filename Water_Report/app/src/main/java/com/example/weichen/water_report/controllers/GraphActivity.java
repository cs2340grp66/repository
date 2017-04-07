package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.weichen.water_report.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private Spinner ppmType;
    private Spinner yearNum;

    private static String[] ppmArr = new String[]{"Virus", "Contaminant"};
    private static String[] yearArr = new String[]{"2015", "2016","2017"};
    ArrayList<Double> ppmVir = new ArrayList<>();
    ArrayList<Double> ppmCon = new ArrayList<>();
    ArrayList<Double> months = new ArrayList<>();

    private String _ppmType;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

//        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://water-report-cf485.firebaseio.com/quality_report");

//        String passLocation = null;
//
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            passLocation = extras.getString("clickedItem");
//        }
//
//        final String choilocation  = passLocation;

        ppmType = (Spinner) findViewById(R.id.graph_contaminant);
        ArrayAdapter<String> typeAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ppmArr);
        typeAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ppmType.setAdapter(typeAdpt);

        yearNum = (Spinner) findViewById(R.id.graph_virus);
        ArrayAdapter<String> yearAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearArr);
        typeAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearNum.setAdapter(yearAdpt);

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot child : dataSnapshot.getChildren()){
//                    String location = dataSnapshot.child(child.getKey()).child("locations").getValue(String.class);
//                    if (location.equals(choilocation)) {
//
//                        String vir = dataSnapshot.child(child.getKey()).child("virus_PPM").getValue(String.class);
//                        ppmVir.add(stringToDouble(vir));
//
//                        String con = dataSnapshot.child(child.getKey()).child("contaminant_PPM").getValue(String.class);
//                        ppmCon.add(stringToDouble(con));
//
//                        String month = dataSnapshot.child(child.getKey()).child("date").getValue(String.class);
//                        String month1 = Character.toString(month.charAt(3)) + Character.toString(month.charAt(4));
//                        months.add(stringToDouble(month1));
//                    }
//                }
//                showGraph(ppmVir, ppmCon, months);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }


    /**
     * user button to show the graph
     */
    public void showGraph_button(View view){

        String passLocation = null;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            passLocation = extras.getString("clickedItem");
        }

        final String choilocation  = passLocation;

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://water-report-cf485.firebaseio.com/quality_report");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String location = dataSnapshot.child(child.getKey()).child("locations").getValue(String.class);
                    if (location.equals(choilocation)) {

                        String vir = dataSnapshot.child(child.getKey()).child("virus_PPM").getValue(String.class);
                        ppmVir.add(stringToDouble(vir));

                        String con = dataSnapshot.child(child.getKey()).child("contaminant_PPM").getValue(String.class);
                        ppmCon.add(stringToDouble(con));

                        String month = dataSnapshot.child(child.getKey()).child("date").getValue(String.class);
                        String month1 = Character.toString(month.charAt(4));
                        months.add(stringToDouble(month1));
                    }
                }
                showGraph(ppmVir, ppmCon, months);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    /**
     * use to show the historical graph
     */
    public void showGraph(ArrayList<Double> vir, ArrayList<Double> con,  ArrayList<Double> months){
        _ppmType = (String)ppmType.getSelectedItem();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        PointsGraphSeries<DataPoint> series2;



        if(_ppmType.equals("Virus")){
            series2 = new PointsGraphSeries<>();
            for(int x = 0; x < vir.size(); x++){
                series2.appendData(new DataPoint(months.get(x), vir.get(x)), false, vir.size());
            }
        } else{
            series2 = new PointsGraphSeries<>();
            for(int x = 0; x < con.size(); x++){
                series2.appendData(new DataPoint(months.get(x), con.get(x)), false, con.size());
            }
        }


//        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
//        staticLabelsFormatter.setHorizontalLabels(new String[] {"Jan", "Feb", "Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Noc","Dec"});
//        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);



        graph.addSeries(series2);
        series2.setShape(PointsGraphSeries.Shape.POINT);


    }


    /**
     * covert string to double
     * @param num
     * @return
     */
    public double stringToDouble(String num){
        double x = Double.parseDouble(num);
        return x;
    }


    /**
     * go back to histotical report
     * @param view
     */
    public void goBack_from_graph(View view){
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String classes = dataSnapshot.child("classes").getValue(String.class);

                if (classes.equals("USER")){
                    startActivity(new Intent(GraphActivity.this, WelcomActivity.class));
                } else if (classes.equals("WORKER")){
                    startActivity(new Intent(GraphActivity.this, Worker_Welcome_Activity.class));
                } else if (classes.equals("MANAGER")){
                    startActivity(new Intent(GraphActivity.this, Historical_report_Activity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
