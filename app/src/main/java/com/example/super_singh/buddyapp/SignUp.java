package com.example.super_singh.buddyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private EditText Email,Pass;
    private TextView AlreadyUser;
    private  Button btnSignUp;
    private ProgressDialog progress;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        progress= new ProgressDialog(this);
        Email = (MaterialEditText) findViewById(R.id.Email);
        Pass = (MaterialEditText) findViewById(R.id.Password);
        AlreadyUser=(TextView)findViewById(R.id.AlreadyUser);
        btnSignUp=(Button)findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        AlreadyUser.setOnClickListener(this);
        auth= FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();

}
    private void registerUser()
    {
        final String email=Email.getText().toString().trim();
        final String Password=Pass.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please enter an email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(Password))
        {
            Toast.makeText(this,"Please enter a password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Password.length()<6)
        {
            Toast.makeText(this,"Your Password should be greater than 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        //using progress
        progress.setMessage("You are getting registered...Please wait.");
        progress.show();
        auth.createUserWithEmailAndPassword(email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this,"Successfully Registered",Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }

                        else
                        {
                            Toast.makeText(SignUp.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                        }

                    }
                });

    }
    @Override
    public void onClick(View view) {
        if(view == btnSignUp)
        {
            registerUser();
        }
        if(view == AlreadyUser)
        {
            finish();
            Intent SignInPage=new Intent(getBaseContext(),SignIn.class);
            startActivity(SignInPage);
        }
    }
}
