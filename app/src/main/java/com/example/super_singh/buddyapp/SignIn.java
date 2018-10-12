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
import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    EditText EditPassword,EditEmail;
    Button signIn;
    TextView signUpFirst;
    private ProgressDialog progress;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth =FirebaseAuth.getInstance();
        progress= new ProgressDialog(this);
        if(firebaseAuth.getCurrentUser()!=null)
        {
            //Profile Activity
            finish();
            Intent ProfileAct= new Intent(getBaseContext(),ProfileActivity.class);
            startActivity(ProfileAct);
        }
        EditPassword=(MaterialEditText)findViewById(R.id.Password);
        EditEmail=(MaterialEditText)findViewById(R.id.email);
        signIn=(Button)findViewById(R.id.btnSignIn);
        signUpFirst=(TextView) findViewById(R.id.newUser);
        signIn.setOnClickListener(this);
        signUpFirst.setOnClickListener(this);
        //Initialize Firebase
    }
    private void userLogin()
    {
        final String email=EditEmail.getText().toString().trim();
        final String Password=EditPassword.getText().toString().trim();

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
        progress.setMessage("Signing in");
        progress.show();
        firebaseAuth.signInWithEmailAndPassword(email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            finish();
                            Toast.makeText(SignIn.this,"Signed In",Toast.LENGTH_SHORT).show();
                            Intent ProfileAct= new Intent(getBaseContext(),ProfileActivity.class);
                            startActivity(ProfileAct);
                        }
                        else
                        {
                            Toast.makeText(SignIn.this,"You have entered wrong Email/Password combination",Toast.LENGTH_SHORT).show();

                        }
                        progress.dismiss();
                    }
                });
    }
    @Override
    public void onClick(View view) {
        if(view == signIn)
        {
            userLogin();
        }
        if(view == signUpFirst)
        {

            Intent signUpPage=new Intent(getBaseContext(),SignUp.class);
            startActivity(signUpPage);
        }
    }
}
