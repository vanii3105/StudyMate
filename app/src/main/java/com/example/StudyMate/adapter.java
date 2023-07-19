package com.example.StudyMate;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;

public class adapter extends FirebaseRecyclerAdapter<putPDF,adapter.myviewholder> {

    public adapter(@NonNull FirebaseRecyclerOptions<putPDF> options) {
        super(options);
//        options.getSnapshots()
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull putPDF model) {


        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        holder.topic.setText(String.valueOf(model.getTopic()));
        holder.dept.setText(String.valueOf(model.getDept()));
        holder.sem.setText(String.valueOf(model.getSem()));
        holder.subject.setText(String.valueOf(model.getSubject()));

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(holder.img1.getContext(),viewpdf.class);
                intent.putExtra("name",model.getName());
                intent.putExtra("url",model.getUrl());
//                intent.putExtra("context",(Serializable) holder.img1.getContext());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.img1.getContext().startActivity(intent);

            }
        });

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("uploads").child(getRef(holder.getAdapterPosition()).getKey());

        firebaseDatabase.child("isarchived").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String isarchived = snapshot.getValue().toString();
                    if(isarchived.equals("true")){
                        holder.save.setImageResource(R.drawable.ic_bookmark_black);
                    }
                    if(isarchived.equals("false")){
                        holder.save.setImageResource(R.drawable.ic_bookmark_white);
                    }
                }
                else{
                    holder.save.setImageResource(R.drawable.ic_bookmark_white);
                    firebaseDatabase.child("isarchived").setValue("false");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(firebaseDatabase.child("inarchive").get)
                firebaseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String isarchived = snapshot.child("isarchived").getValue().toString();

                        if(isarchived.equals("true")){
                            AlertDialog.Builder builder =  new AlertDialog.Builder(holder.save.getContext());
                            builder.setCancelable(false);
                            builder.setTitle("Discard");
                            builder.setMessage("You sure want to remove? ");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                    startActivity(new Intent(holder.save.getContext(),holder.save.getContext()));
//                                    holder.save.setImageResource(R.drawable.ic_bookmark_white);
//                                    firebaseDatabase.child("isarchived").setValue("false");

                                    FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("archive").child(getRef(holder.getAdapterPosition()).getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                                    firebaseDatabase.child("isarchived").setValue("false");

                                    holder.save.setImageResource(R.drawable.ic_bookmark_white);
                                    Toast.makeText(holder.save.getContext(), "Removed from Archive!", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
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
//                        if(isarchived.equals("true")){
//
//                            holder.save.setImageResource(R.drawable.ic_bookmark_white);
//                            firebaseDatabase.child("isarchived").setValue("false");
//
//                            FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("archive").child(getRef(holder.getAdapterPosition()).getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//
//                                }
//                            });
//                            Toast.makeText(holder.save.getContext(), "Removed from Archive!", Toast.LENGTH_SHORT).show();
//
//                        }
                        if(isarchived.equals("false")){
                            holder.save.setImageResource(R.drawable.ic_bookmark_black);
                            firebaseDatabase.child("isarchived").setValue("true");
                            FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("uploads").child(getRef(holder.getAdapterPosition()).getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    putPDF putpdf = snapshot.getValue(putPDF.class);
                                    HashMap<String,Object> m = new HashMap<String,Object>();
                                    m.put("dept",putpdf.getDept());
                                    m.put("sem",putpdf.getSem());
                                    m.put("name",putpdf.getName());
                                    m.put("subject",putpdf.getSubject());
                                    m.put("topic",putpdf.getTopic());
                                    m.put("url",putpdf.getUrl());
                                    m.put("isarchived","true");
                                    FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("archive").child(getRef(holder.getAdapterPosition()).getKey()).setValue(m);
                                    Toast.makeText(holder.save.getContext(), "Saved to Archive!", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
//        holder.save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("uploads").child(getRef(holder.getAdapterPosition()).getKey()).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            putPDF putpdf = snapshot.getValue(putPDF.class);
//                            HashMap<String,Object> m = new HashMap<String,Object>();
//                            m.put("grade",putpdf.getGrade());
//                            m.put("name",putpdf.getName());
//                            m.put("subject",putpdf.getSubject());
//                            m.put("topic",putpdf.getTopic());
//                            m.put("url",putpdf.getUrl());
//                            FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("archive").child(getRef(holder.getAdapterPosition()).getKey()).setValue(m);
//                            Toast.makeText(buttonView.getContext(), "Saved to Archive!", Toast.LENGTH_SHORT).show();
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//                else{
//                    FirebaseDatabase.getInstance().getReference().child("users").child(currentuser).child("archive").child(getRef(holder.getAdapterPosition()).getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                        }
//                    });
//                    Toast.makeText(buttonView.getContext(), "Removed from Archive!", Toast.LENGTH_SHORT).show();
//                }
//                isChecked = !isChecked;
//            }
//        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        ImageView img1,save;
        CardView card;
        TextView subject,topic,dept,sem;

        public myviewholder(@NonNull View itemView){
            super(itemView);
            save=itemView.findViewById(R.id.save);
            img1=itemView.findViewById(R.id.img1);
            subject=itemView.findViewById(R.id.subject);
            topic=itemView.findViewById(R.id.topic);
            dept=itemView.findViewById(R.id.dept);
            sem=itemView.findViewById(R.id.sem);
            card=itemView.findViewById(R.id.card);
        }
    }
}
