package com.example.StudyMate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RiderRegisterone extends AppCompatActivity {

    private long pressed;
    ImageView r_back_button;
    Button rnextBtn;
    TextInputEditText email,password,confpassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_registerone);
        r_back_button=findViewById(R.id.r_back_button);
        rnextBtn=findViewById(R.id.rnextBtn);
        email=findViewById(R.id.remail);
        password=findViewById(R.id.rpwd);
        confpassword=findViewById(R.id.crpwd);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(RiderRegisterone.this, RiderRegistertwo.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        r_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(RiderRegisterone.this,LoginActivity.class));
            }
        });

        rnextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString().trim();
                final String Password = password.getText().toString().trim();
                final String ConfPassword = confpassword.getText().toString().trim();
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplicationContext(),"Please enter email!!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!Password.equals(ConfPassword)) {
                    Toast.makeText(getApplicationContext(), "Please Verify Confirm Password!!", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(RiderRegisterone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RiderRegisterone.this, "Registered Successfully!!", Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(RiderRegisterone.this, RiderRegistertwo.class);
//                            startActivity(intent);
                        } else {
                            Toast.makeText(RiderRegisterone.this, "Registration Failed!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    private boolean validateEmail(){
        String Email = email.getText().toString().trim();
        if(Email.isEmpty()){
            email.setError("Field Cannot be Empty");
            return false;
        }else{
            email.setError(null);
            return true;
        }
    }
    private boolean validatePassword(){
        String Password = password.getText().toString().trim();
        if(Password.isEmpty()){
            password.setError("Field Cannot be Empty");
            return false;
        }

        else{
            email.setError(null);
            return true;
        }
    }
    public void confirmInput(View v){
        if(!validateEmail() | !validatePassword()){
            return;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onBackPressed(){
        if(pressed + 2000> System.currentTimeMillis()){
            super.onBackPressed();
            finishAffinity();
        }
        else {
            Toast.makeText(RiderRegisterone.this, "Press Back again to Exit!", Toast.LENGTH_SHORT).show();
        }
        pressed = System.currentTimeMillis();
    }

}