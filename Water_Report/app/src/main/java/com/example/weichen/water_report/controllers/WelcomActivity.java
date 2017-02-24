package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.weichen.water_report.R;
import com.example.weichen.water_report.model.User_Infor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomActivity extends AppCompatActivity {

    private Button logout;

    private TextView userEmail;

    private FirebaseAuth userAuth;

    private FirebaseUser user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);



        userAuth = FirebaseAuth.getInstance();

        if (userAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        logout = (Button) findViewById(R.id.logout);

        user = userAuth.getCurrentUser();

        userEmail = (TextView) findViewById(R.id.user_email);
        userEmail.setText("Hello: " + user.getEmail());
    }

    /**
     * use to logout a use, will move to initial activity, and logout from database
     * @param view
     */
    public void logout(View view) {
        userAuth.signOut();
        Intent intents = new Intent(WelcomActivity.this, InitialActivity.class);
        startActivity(intents);

    }
}
