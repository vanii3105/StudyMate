package com.example.StudyMate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UploadActivity extends AppCompatActivity {

    private long pressed;
    FloatingActionButton uploadbtn;
    ImageView uploadNoteBackbtn;
    RecyclerView recyclerView;
    adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadbtn=findViewById(R.id.uploadbtn);
        uploadNoteBackbtn=findViewById(R.id.uploadNoteBackbtn);
        recyclerView=findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseRecyclerOptions<putPDF> options =
                new FirebaseRecyclerOptions.Builder<putPDF>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("uploads"),putPDF.class).build();

        adapter = new adapter(options);
        recyclerView.setAdapter(adapter);


        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadActivity.this,UploadNoteFormActivity.class));
            }
        });

        uploadNoteBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadActivity.this,HomePage.class));
            }
        });



    }
    public void go (View view) {
        Intent i = new Intent(this,UploadNoteFormActivity.class);
        Bundle b = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        startActivity(i,b);
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    //coz history is not implemented

//    @Override
//    public void onBackPressed(){
//        if(pressed + 2000> System.currentTimeMillis()){
//            super.onBackPressed();
//            finishAffinity();
//        }
//        else {
//            Toast.makeText(UploadActivity.this, "Press Back again to Exit!", Toast.LENGTH_SHORT).show();
//        }
//        pressed = System.currentTimeMillis();
//    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent i = new Intent(UploadActivity.this, HomePage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}