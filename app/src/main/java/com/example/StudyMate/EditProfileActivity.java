package com.example.StudyMate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.KeyListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    private long pressed;
    ImageView AccountSettingBackButton, changePhoto, editname, editemail, editpass, editage, editphoneno;
    CircularImageView profile;
    EditText EditProfileName, EditProfileEmail, EditAccSettingPassword, EditAccSettingAge, EditAccSettingPhoneNumber;
    Button save;
    String age,phone,name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AccountSettingBackButton=findViewById(R.id.AccountSettingBackButton);

        profile=findViewById(R.id.profile);
        changePhoto=findViewById(R.id.changePhoto);

        EditProfileName=findViewById(R.id.EditProfileName);
        editname=findViewById(R.id.editname);

        editemail=findViewById(R.id.editemail);
        EditProfileEmail=findViewById(R.id.EditProfileEmail);

        editpass=findViewById(R.id.editpass);
        EditAccSettingPassword=findViewById(R.id.EditAccSettingPassword);

        editage=findViewById(R.id.editage);
        EditAccSettingAge=findViewById(R.id.EditAccSettingAge);

        editphoneno=findViewById(R.id.editphoneno);
        EditAccSettingPhoneNumber=findViewById(R.id.EditAccSettingPhoneNumber);

        save=findViewById(R.id.save);

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String email = snapshot.child("email").getValue().toString();
                String birthdate = snapshot.child("birthdate").getValue().toString();
                String phone = snapshot.child("phone").getValue().toString();
                String url = snapshot.child("url").getValue().toString();

                EditProfileName.setText(name);
                EditProfileEmail.setText(email);
//                EditAccSettingPassword.setText(email);
                EditAccSettingAge.setText(birthdate);
                EditAccSettingPhoneNumber.setText(phone);
                Glide.with(EditProfileActivity.this).load(url).into(profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(EditProfileActivity.this);
                mydialog.setTitle("New Name?");

                final EditText nameInput = new EditText(EditProfileActivity.this);
                nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(nameInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name = nameInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("name",name);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });

        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(EditProfileActivity.this);
                mydialog.setTitle("New Email Address?");

                final EditText emailInput = new EditText(EditProfileActivity.this);
                emailInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(emailInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        email = emailInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("email",email);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });

//        editpass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditProfileName.setFocusableInTouchMode(true);
//            }
//        });

        editage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(EditProfileActivity.this);
                mydialog.setTitle("New Age?");

                final EditText ageInput = new EditText(EditProfileActivity.this);
                ageInput.setInputType(InputType.TYPE_CLASS_DATETIME);
                mydialog.setView(ageInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        age = ageInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("age",age);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });

        editphoneno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(EditProfileActivity.this);
                mydialog.setTitle("New Phone Number?");

                final EditText phoneInput = new EditText(EditProfileActivity.this);
                phoneInput.setInputType(InputType.TYPE_CLASS_PHONE);
                mydialog.setView(phoneInput);

                mydialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phone = phoneInput.getText().toString();
                        HashMap<String,Object> m = new HashMap<String,Object>();
                        m.put("phone",phone);
                        FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).updateChildren(m);
                    }
                });
                mydialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mydialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this,AccountActivity.class));
            }
        });

        AccountSettingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this,AccountActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed(){
        if(pressed + 2000> System.currentTimeMillis()){
            super.onBackPressed();
            Intent i = new Intent(EditProfileActivity.this, AccountActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(EditProfileActivity.this, "Press Back again to go Back!", Toast.LENGTH_SHORT).show();
        }
        pressed = System.currentTimeMillis();
    }

}

//    editname.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            toggleEditableofEditText(EditProfileName);
////                EditProfileName.setFocusableInTouchMode(true);
////                EditProfileName.setSelection(EditProfileName.getText().length());
//        }
//    });
//    private void toggleEditableofEditText(EditText editProfileName) {
//        if(editProfileName.getKeyListener() != null){
//            editProfileName.setTag(editProfileName.getKeyListener());
//            editProfileName.setKeyListener(null);
//        }
//        else{
//            editProfileName.setKeyListener((KeyListener) editProfileName.getTag());
//            editProfileName.setFocusableInTouchMode(true);
//            editProfileName.setSelection(editProfileName.getText().length());
//        }
//    }
