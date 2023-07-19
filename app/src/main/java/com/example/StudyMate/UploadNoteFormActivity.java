package com.example.StudyMate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadNoteFormActivity extends AppCompatActivity {

    ImageView Uploadclose,addDoc;
    MaterialButton uploadDocbtn;
    TextInputEditText subject,dept,topic,sem;
    String name;

    StorageReference storageReference;
    DatabaseReference databaseReference, uploadref;
    String currentuser;
    static int n = 1;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_note_form);
        Uploadclose=findViewById(R.id.Uploadclose);
        uploadDocbtn=findViewById(R.id.uploadDocbtn);
        subject=findViewById(R.id.subjectName);
        topic=findViewById(R.id.topiceName);
        dept=findViewById(R.id.dept);
        sem=findViewById(R.id.sem);
        addDoc=findViewById(R.id.addDoc);

        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("uploads");
        uploadref = FirebaseDatabase.getInstance().getReference().child("uploads");


        Uploadclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(UploadNoteFormActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Discard");
                builder.setMessage("You sure want to discard? ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(UploadNoteFormActivity.this,UploadActivity.class));
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

        addDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDF();
            }
        });

//        uploadDocbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Successfully Uploaded.",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(UploadNoteFormActivity.this,UploadActivity.class));
//            }
//        });
    }

    private void selectPDF() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uploadDocbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = data.getDataString().substring(data.getDataString().lastIndexOf("/")+1);

                    uploadPDFFirebase(data.getData());

                }
            });
        }
    }

    private void uploadPDFFirebase(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is loading...");
        progressDialog.show();
        String cu = currentuser+"/";
        StorageReference reference = storageReference.child("uploads/").child(cu).child("upload"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String Subject = subject.getText().toString();
                        String Topic = topic.getText().toString();
                        String Dept = dept.getText().toString();
                        String Sem = sem.getText().toString();
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        putPDF putPDF = new putPDF(Dept,Sem,Subject,Topic,uri.toString(),name);
                        databaseReference.child(Topic).setValue(putPDF);
                        uploadref.child(Dept).child(Topic).setValue(putPDF);
//                        saveMsg(putPDF,cu,Topic);
//                        saveinUpload(putPDF,Dept);
                        progressDialog.dismiss();

                        Toast.makeText(getApplicationContext(),"Successfully Uploaded.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UploadNoteFormActivity.this,UploadActivity.class));

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0* taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage("File Uploaded.."+(int) progress + "%");
                    }
                });
    }
//    private void saveMsg( putPDF putPDF, String cu, String topic) {
//
//        String countname = cu.concat(topic);
//        Toast.makeText(getApplicationContext(),countname.toString(),Toast.LENGTH_SHORT).show();
//        databaseReference.child(topic).setValue(putPDF);
//
//    }

//    private void saveinUpload( putPDF putPDF, String Dept) {
//        String count = Integer.toString(n);
//        uploadref.child(Dept).child(count).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    n=n+1;
//                    saveinUpload(putPDF, Dept);
//                }
//                else{
//                    if (addDoc.getDrawable() != null){
////                        Toast.makeText(UploadNoteFormActivity.this, n, Toast.LENGTH_LONG).show();
//                        uploadref.child(Dept).child(count).setValue(putPDF);
//                        addDoc.setImageDrawable(null);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//
//    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent i = new Intent(UploadNoteFormActivity.this, UploadActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}

//    private void openAlertBox() {
//        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
//        builder.setCancelable(false);
//        builder.setTitle("Discard");
//        builder.setMessage("You sure want to discard ? ");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(UploadNoteFormActivity.this,HomePage.class));
//
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//            }
//        });
//        AlertDialog alertDialog =builder.create();
//        alertDialog.show();
//




