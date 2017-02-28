package com.example.weichen.water_report.controllers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weichen.water_report.R;
import com.example.weichen.water_report.model.User_Infor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {


    private EditText _resetPass;
    private EditText _resetPassRE;
    private EditText _userName;
    private EditText _userPhone;
    private EditText _userAddress;
    private TextView _class;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private Button save_return;
    private ImageButton back_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(ProfileActivity.this, InitialActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();

        _userName = (EditText) findViewById(R.id.username_profile_input);
        _userPhone = (EditText) findViewById(R.id.userphone_profile_input);
        _userAddress = (EditText) findViewById(R.id.userAddress_profile_input);
        _resetPass = (EditText) findViewById(R.id.reset_password);
        _resetPassRE = (EditText) findViewById(R.id.reset_password_RE);
        _class = (TextView) findViewById(R.id.classes_user);

        save_return = (Button) findViewById(R.id.save_return_button);
        back_profile = (ImageButton) findViewById(R.id.back_profile);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String classes = dataSnapshot.child("classes").getValue(String.class);
                _class.setText(classes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    /**
     * using to updata the user profile. after it is finish, will jump back to welcome activity
     * @param view
     */
    public void save_return(View view) {
        String username = _userName.getText().toString().trim();
        String userphone = _userPhone.getText().toString().trim();
        String useraddress  = _userAddress.getText().toString().trim();
        String reset_pass = _resetPass.getText().toString().trim();
        String reser_pass_RE = _resetPassRE.getText().toString().trim();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(username.length() > 1)
            databaseReference.child("user").child(user.getUid()).child("userName").setValue(username);
        if(userphone.length() > 1)
            databaseReference.child("user").child(user.getUid()).child("phoneNum").setValue(userphone);
        if(useraddress.length() > 1)
            databaseReference.child("user").child(user.getUid()).child("address").setValue(useraddress);

        if(reset_pass.length() > 1 && reser_pass_RE.length() > 1) {
            if(reset_pass.length() < 6)
                _resetPass.setError("At least for 6 characters");
            if(reser_pass_RE.length() < 6)
                _resetPassRE.setText("At least for 6 characters");
            if(!reset_pass.equals(reser_pass_RE)) {
                Toast.makeText(ProfileActivity.this, "ReEnter password is NOT match!", Toast.LENGTH_LONG).show();
            } else {
                user.updatePassword(reset_pass)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ProfileActivity.this, "Profile Update Successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ProfileActivity.this, WelcomActivity.class));
                            }
                        });
            }

        }

        if (reset_pass.length() < 1 && reser_pass_RE.length() < 1) {
            Toast.makeText(ProfileActivity.this, "Profile Update Successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(ProfileActivity.this, WelcomActivity.class));
        }
    }

    /**
     * us to jump back to welcome if user don't want to change anything
     * @param view view
     */
    public void back_profile(View view) {
        startActivity(new Intent(ProfileActivity.this, PersonalProfileActivity.class));
    }
}
