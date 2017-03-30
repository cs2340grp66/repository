package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.weichen.water_report.R;
import com.example.weichen.water_report.model.User_Infor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText _userName;
    private EditText _passWord;
    private EditText _reEnterPW;
    private Spinner _classes;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private FirebaseUser userFB;

    private Button create_Account_r_Button;
    private ImageButton goBack;

    private User_Infor user;

    private static String[] classes = new String[]{"USER", "WORKER","MANAGER","ADMINISTRATOR"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


        if (mAuth.getCurrentUser() != null) {
            Intent intents = new Intent(RegisterActivity.this, WelcomActivity.class);
            startActivity(intents);
        }

        _userName = (EditText) findViewById(R.id.user_name_r_input);
        if (_userName.getText().toString().length() == 0)
            _userName.setError("Email can't be empty!");

        _passWord = (EditText) findViewById(R.id.password_r_input);
        if (_passWord.getText().toString().length() < 6)
            _passWord.setError("Least have 6 characters");

        _reEnterPW = (EditText) findViewById(R.id.reEnter_password_r_input);
        if (_reEnterPW.getText().toString().length() < 6)
            _reEnterPW.setError("Least have 6 characters");



        _classes = (Spinner) findViewById(R.id.classes_spinner);
        ArrayAdapter<String> classAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classes);
        classAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _classes.setAdapter(classAdpt);


        create_Account_r_Button = (Button) findViewById(R.id.create_Account_r_Button);

        goBack = (ImageButton) findViewById(R.id.go_back_r_button);

    }

    /**
     * move to login screen
     * @param view
     */
    public void toLoginLayout(View view) {
        Intent intents = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intents);
    }



    /**
     * Code for the back button on the registration
     *
     * @param view
     */
    public void goBack(View view) {
        Intent intents = new Intent(RegisterActivity.this, InitialActivity.class);
        _passWord = null;
        _userName = null;
        startActivity(intents);
    }


    /**
     * when use click the login_and_register button, it will check if the password is 6 character
     * long, and reenter password is same as last one, if so the register will success,
     * if not the error message will come out.
     * @param view the button is click
     */
    public void login_and_register(View view) {
        String userName = _userName.getText().toString();
        String password = _passWord.getText().toString();
        String reEnterPW = _reEnterPW.getText().toString();
        String classes = (String) _classes.getSelectedItem();


        user = new User_Infor(userName, " ", " ", classes);

        FirebaseUser fb_user;

        if (password.equals(reEnterPW)) {
            mAuth.createUserWithEmailAndPassword(userName, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser fb_user = FirebaseAuth.getInstance().getCurrentUser();
                                databaseReference.child("user").child(fb_user.getUid()).setValue(user);
                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                login();
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    _userName.setError("User with this email already exist.");
                                    Toast.makeText(RegisterActivity.this, "Registered Fail! \nUser with this email already exist.", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registered Fail", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        } else {
            _reEnterPW.setError("Re-enter Password are not match ");
        }
    }

    /**
     * using to determent which welcome activity need to go
     */
    public void login() {
        userFB = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(userFB.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String classes = dataSnapshot.child("classes").getValue(String.class);

                if (classes.equals("USER")) {
                    startActivity(new Intent(RegisterActivity.this, WelcomActivity.class));
                } else if (classes.equals("WORKER")) {
                    startActivity(new Intent(RegisterActivity.this, Worker_Welcome_Activity.class));
                } else if (classes.equals("MANAGER")) {
                    startActivity(new Intent(RegisterActivity.this, Manager_Welcome_Activity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

 }

