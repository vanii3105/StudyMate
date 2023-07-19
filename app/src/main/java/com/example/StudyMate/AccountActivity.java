package com.example.StudyMate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class
AccountActivity extends AppCompatActivity {

    ImageView AccountBackButton,editprofile,AboutUsNext,faqnext;
    TextView uname,uemail;
    Button AccEditprofilebtn;
//    Switch nightModeswitch;
    RelativeLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        AccountBackButton=findViewById(R.id.AccountBackButton);
        editprofile=findViewById(R.id.editprofile);
        AboutUsNext=findViewById(R.id.AboutUsNext);
        faqnext=findViewById(R.id.faqnext);
        uname=findViewById(R.id.name);
        uemail=findViewById(R.id.email);
        AccEditprofilebtn=findViewById(R.id.AccEditprofilebtn);
//        nightModeswitch=findViewById(R.id.nightModeswitch);
        logout=findViewById(R.id.logout);

        faqnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,FAQ.class));
            }
        });
        AboutUsNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,AboutUS.class));
            }
        });

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String url = snapshot.child("url").getValue().toString();

                uname.setText(name);
                uemail.setText(email);
                Glide.with(AccountActivity.this).load(url).into(editprofile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AccountBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,HomePage.class));
            }
        });

        AccEditprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this,EditProfileActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =  new AlertDialog.Builder(AccountActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Logout");
                builder.setMessage("You sure want to logout? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog =builder.create();
                alertDialog.show();
            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent i = new Intent(AccountActivity.this, HomePage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}