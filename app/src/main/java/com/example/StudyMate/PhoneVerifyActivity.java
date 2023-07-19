package com.example.StudyMate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class PhoneVerifyActivity extends AppCompatActivity {

    ImageView close;
    Button sendcodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
        close=findViewById(R.id.close);
        sendcodeBtn=findViewById(R.id.sendcodeBtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(PhoneVerifyActivity.this,LoginActivity.class));
            }
        });
        sendcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(PhoneVerifyActivity.this,OTP.class));
            }
        });


    }



}