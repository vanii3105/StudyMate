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

import com.chaos.view.PinView;

public class OTP extends AppCompatActivity {

    ImageView otpbackbtn;
    Button verifycodebtn;
    Button editphonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        otpbackbtn=findViewById(R.id.otpbackbtn);
        verifycodebtn=findViewById(R.id.verifycodebtn);
        editphonenumber=findViewById(R.id.editphonenumber);
        otpbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(OTP.this,PhoneVerifyActivity.class));
            }
        });
        verifycodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(OTP.this,RiderRegisterone.class));
            }
        });
        editphonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(OTP.this,PhoneVerifyActivity.class));
            }
        });





    }


}