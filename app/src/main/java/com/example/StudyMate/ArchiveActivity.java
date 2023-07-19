package com.example.StudyMate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ArchiveActivity extends AppCompatActivity {

     ImageView archiveBackbtn;
     RecyclerView recyclerView;
    adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        archiveBackbtn = findViewById(R.id.archiveBackbtn);
        recyclerView = findViewById(R.id.ArchiverecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseRecyclerOptions<putPDF> options =
                new FirebaseRecyclerOptions.Builder<putPDF>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("archive"),putPDF.class).build();

        adapter = new adapter(options);
        recyclerView.setAdapter(adapter);

        archiveBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArchiveActivity.this,HomePage.class));
            }
        });

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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent i = new Intent(ArchiveActivity.this, HomePage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}