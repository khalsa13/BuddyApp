package com.example.super_singh.buddyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class PersonalDetails extends AppCompatActivity implements View.OnClickListener {
    private EditText FName,LName,Email,Pass,Grad,Uni;
    private TextView AlreadyUser;
    private Button Save;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dbReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        firebaseAuth=FirebaseAuth.getInstance();
        FName = (MaterialEditText) findViewById(R.id.FirstName);
        LName = (MaterialEditText) findViewById(R.id.LastName);
        Email = (MaterialEditText) findViewById(R.id.Email);
        Pass = (MaterialEditText) findViewById(R.id.Password);
        Grad = (MaterialEditText) findViewById(R.id.GraduationYear);
        Uni = (MaterialEditText) findViewById(R.id.college);

        FirebaseUser user= firebaseAuth.getCurrentUser();
        if(user == null)
        {

            Intent mainAct= new Intent(getBaseContext(),MainActivity.class);
            startActivity(mainAct);
        }

        Save=(Button)findViewById(R.id.SaveInfo);
        Save.setOnClickListener(this);
        dbReference = FirebaseDatabase.getInstance().getReference();
        Email.setText(user.getEmail());
        //PrePopulating Fields
        dbReference.child(user.getUid()).child("FirstName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                    FName.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbReference.child(user.getUid()).child("LastName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                    LName.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbReference.child(user.getUid()).child("University").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                    Uni.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbReference.child(user.getUid()).child("GYear").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                    Grad.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void saveCredentials()
    {
        final String email=Email.getText().toString().trim();
        final String FirstName=FName.getText().toString().trim();
        final String LastName=LName.getText().toString().trim();
        final String GradYear=Grad.getText().toString().trim();
        final String University=Uni.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(FirstName))
        {
            Toast.makeText(this,"Please enter your First Name",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(LastName))
        {
            Toast.makeText(this,"Please enter your LastName",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(GradYear))
        {
            Toast.makeText(this,"Please enter your Graduation Year",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(University))
        {
            Toast.makeText(this,"Please enter the name of your University/College",Toast.LENGTH_SHORT).show();
            return;
        }

            UserInformation userInfo=new UserInformation(FirstName,LastName,email,GradYear,University);
            FirebaseUser user= firebaseAuth.getCurrentUser();
            dbReference.child(user.getUid()).setValue(userInfo);
            Toast.makeText(PersonalDetails.this,"Successfully Registered",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View view) {
        if(view == Save)
        {
            saveCredentials();
        }
    }
}
