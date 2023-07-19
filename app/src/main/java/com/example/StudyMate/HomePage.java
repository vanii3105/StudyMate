package com.example.StudyMate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class HomePage extends AppCompatActivity {

    private long pressed;
//    private SearchView searchView;
    LinearLayout Uploads,Archive,Account;
    CardView itcardview,cecardview,eccardview,civilcardview,electricalcardview,mechanicalcardview;
//    RecyclerView recyclerView,recyclerViewarchive;
//    adapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        searchView=findViewById(R.id.searchView);
//        searchView.clearFocus();

        Uploads=findViewById(R.id.Uploads);
        Archive=findViewById(R.id.Archive);
        Account=findViewById(R.id.Account);
        itcardview=findViewById(R.id.itcardview);
        cecardview=findViewById(R.id.cecardview);
        eccardview=findViewById(R.id.eccardview);
        civilcardview=findViewById(R.id.civilcardview);
        electricalcardview=findViewById(R.id.electricalcardview);
        mechanicalcardview=findViewById(R.id.mechanicalcardview);
//        recyclerView=findViewById(R.id.recyclerView);
//        recyclerViewarchive=findViewById(R.id.recyclerViewarchive);



        itcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,ITNotes.class));
            }
        });
        cecardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,CENotes.class));
            }
        });
        eccardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,ECNotes.class));
            }
        });
        civilcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,CIVILNotes.class));
            }
        });
        electricalcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,ELECTRICALNotes.class));
            }
        });
        mechanicalcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,MECHANICALNotes.class));
            }
        });


//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(HomePage.this, query, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

//        FirebaseRecyclerOptions<putPDF> options =
//                new FirebaseRecyclerOptions.Builder<putPDF>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("archive"),putPDF.class).build();
//
//        adapter = new adapter(options);
//        recyclerViewarchive.setAdapter(adapter);


        Uploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,UploadActivity.class));
            }
        });

        Archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,ArchiveActivity.class));
            }
        });

        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,AccountActivity.class));
            }
        });
    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        adapter.startListening();
//    }
//    @Override
//    protected void onStop(){
//        super.onStop();
//        adapter.stopListening();
//    }


    //coz history is not implemented
//    @Override
//    public void onBackPressed(){
//        super.onBackPressed();
//        Intent i = new Intent(HomePage.this, UploadActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(i);
//        finish();
//    }

    @Override
    public void onBackPressed(){
        if(pressed + 2000> System.currentTimeMillis()){
            super.onBackPressed();
            finishAffinity();
        }
        else {
            Toast.makeText(HomePage.this, "Press Back again to Exit!", Toast.LENGTH_SHORT).show();
        }
        pressed = System.currentTimeMillis();
    }
}