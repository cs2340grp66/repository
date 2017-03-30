package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.weichen.water_report.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InitialActivity extends AppCompatActivity {

    Button _sign_in;
    Button _register;
    private FirebaseAuth loginAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);


        loginAuth = FirebaseAuth.getInstance();
        if (loginAuth.getCurrentUser() != null) {
            alradyLogin();
        }


        _sign_in = (Button) findViewById(R.id.sign_in);
        _register = (Button) findViewById(R.id.register_button);
    }

    /**
     * move to login screen
     * @param view
     */
    public void toLoginLayout(View view) {
                Intent intents = new Intent(InitialActivity.this, LoginActivity.class);
                startActivity(intents);

    }

    /**
     * move to register screen
     * @param view
     */
    public void toRegisterLayout(View view) {
                Intent intents = new Intent(InitialActivity.this, RegisterActivity.class);
                startActivity(intents);

    }

    /**
     * if user already login, move to welcome activity
     */
    public void alradyLogin(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String classes = dataSnapshot.child("classes").getValue(String.class);

                if (classes.equals("USER")){
                    startActivity(new Intent(InitialActivity.this, WelcomActivity.class));
                } else if (classes.equals("WORKER")){
                    startActivity(new Intent(InitialActivity.this, Worker_Welcome_Activity.class));
                } else if (classes.equals("MANAGER")){
                    startActivity(new Intent(InitialActivity.this, Manager_Welcome_Activity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
