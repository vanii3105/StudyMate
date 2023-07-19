package com.example.StudyMate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class AboutUS extends AppCompatActivity {

    ImageView AboutUsBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        AboutUsBackButton=findViewById(R.id.AboutUsBackButton);

        AboutUsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUS.this,AccountActivity.class));
            }
        });
    }
}