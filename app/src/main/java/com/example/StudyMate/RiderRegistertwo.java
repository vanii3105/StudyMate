package com.example.StudyMate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;


public class RiderRegistertwo extends AppCompatActivity {

    private long pressed;
    ImageView r2_back_button;
    Button rregisterbtn;
    CircularImageView img;
    ImageView edit;
    TextInputEditText name,address,phonenumber;
    DatePicker datePicker;
    android.widget.RadioGroup RadioGroup;
    RadioButton radioButton,radioButton2,radioButton3;
    private Uri filepath;
    Bitmap bitmap;
    String gender;
    String strDate;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_registertwo);
        r2_back_button=findViewById(R.id.r2_back_button);
        rregisterbtn=findViewById(R.id.rregisterbtn);
        img=findViewById(R.id.rprofile);
        edit=findViewById(R.id.redit);
        name=findViewById(R.id.rname);
        address=findViewById(R.id.raddress);
        datePicker=findViewById(R.id.bDate);
        RadioGroup=findViewById(R.id.RadioGroup);
        radioButton=findViewById(R.id.radioButton);
        radioButton2=findViewById(R.id.radioButton2);
        radioButton3=findViewById(R.id.radioButton3);
        phonenumber=findViewById(R.id.rphonenumber);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        r2_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RiderRegistertwo.this,RiderRegisterone.class));
            }
        });


        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth()+1;
        int year = datePicker.getYear();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
        Date d = new Date(year,month,day);
        strDate = dateFormatter.format(d);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(RiderRegistertwo.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });


        rregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog p = new ProgressDialog(RiderRegistertwo.this);
                p.setTitle("Uploading...");
                p.show();
                if(filepath!=null)
                {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference uploader = storage.getReference().child("profile/" + UUID.randomUUID().toString());
                    uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    {
                                        p.dismiss();


                                        if(radioButton.isChecked()){
                                            HashMap<String,Object> m = new HashMap<String,Object>();
                                            m.put("name",name.getText().toString());
                                            m.put("email",address.getText().toString());
                                            m.put("birthdate",strDate);
                                            m.put("gender","other");
                                            m.put("url",uri.toString());
                                            m.put("phone",phonenumber.getText().toString());
                                            m.put("userid",currentuser.toString());
                                            FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).setValue(m);
                                            startActivity(new Intent(RiderRegistertwo.this,HomePage.class));
//                                            startActivity(new Intent(RiderRegistertwo.this,UploadActivity.class));
                                        }
                                        else if(radioButton2.isChecked()){
                                            HashMap<String,Object> m = new HashMap<String,Object>();
                                            m.put("name",name.getText().toString());
                                            m.put("email",address.getText().toString());
                                            m.put("birthdate",strDate);
                                            m.put("gender","female");
                                            m.put("url",uri.toString());
                                            m.put("phone",phonenumber.getText().toString());
                                            m.put("userid",currentuser.toString());
                                            FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).setValue(m);
                                            startActivity(new Intent(RiderRegistertwo.this,HomePage.class));
//                                            startActivity(new Intent(RiderRegistertwo.this,UploadActivity.class));

                                        }
                                        else if(radioButton3.isChecked()){
                                            HashMap<String,Object> m = new HashMap<String,Object>();
                                            m.put("name",name.getText().toString());
                                            m.put("email",address.getText().toString());
                                            m.put("birthdate",strDate);
                                            m.put("gender","male");
                                            m.put("url",uri.toString());
                                            m.put("phone",phonenumber.getText().toString());
                                            m.put("userid",currentuser.toString());
                                            FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).setValue(m);
                                            startActivity(new Intent(RiderRegistertwo.this,HomePage.class));
//                                            startActivity(new Intent(RiderRegistertwo.this,UploadActivity.class));

                                        }
                                        Toast.makeText(RiderRegistertwo.this, "Saved!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RiderRegistertwo.this, "ERROR!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed(){
        if(pressed + 2000> System.currentTimeMillis()){
            super.onBackPressed();
            finishAffinity();
        }
        else {
            Toast.makeText(RiderRegistertwo.this, "Press Back again to Exit!", Toast.LENGTH_SHORT).show();
        }
        pressed = System.currentTimeMillis();
    }

}