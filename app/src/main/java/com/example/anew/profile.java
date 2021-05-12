package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class profile extends AppCompatActivity {
     private TextView fullName, username,contact,email;
     private TextInputEditText fullN2, email2,contact2,userN2;
     private MaterialButton button;
     FirebaseDatabase firebaseDatabase;
     FirebaseAuth firebaseAuth;
     FirebaseUser user;
     DatabaseReference reference, update;
     String dbFName, dbUsername,dbContact,dbEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fullName=(TextView)findViewById(R.id.fullname);
        fullN2=findViewById(R.id.fullName2);
        username=(TextView)findViewById(R.id.username);
        userN2=findViewById(R.id.username2);
        contact=(TextView)findViewById(R.id.contact);
        contact2=findViewById(R.id.contact2);
        email=(TextView)findViewById(R.id.email);
      //  email2=findViewById(R.id.email2);

        firebaseAuth=FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();
        email.setText(user.getEmail());

        firebaseDatabase=FirebaseDatabase.getInstance();
        reference= firebaseDatabase.getReference("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dbFName= snapshot.child("name").getValue().toString();
                dbUsername= snapshot.child("username").getValue().toString();
                dbContact= snapshot.child("phoneNumber").getValue().toString();
                dbEmail= snapshot.child("email").getValue().toString();

                fullName.setText(dbFName);
                username.setText(dbUsername);
                contact.setText("Contact:"+ dbContact);
                email.setText("Email: " + dbEmail);
                fullN2.setText(dbFName);
                userN2.setText(dbUsername);
                contact2.setText(dbContact);
              //  email2.setText(dbEmail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profile.this, error.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        button=findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();

            }
        });

    }

    private void updateInfo() {
        update= firebaseDatabase.getReference("Users").child(user.getUid());

        String EditName= fullN2.getText().toString().trim();
        String EditUsername= userN2.getText().toString().trim();
     //   String EditEmail= email2.getText().toString().trim();
        String EditContact= contact2.getText().toString().trim();

        if (!EditName.equals(dbFName)){
            if(TextUtils.isEmpty(EditName)) {
                fullN2.setError("Please enter name");
                fullN2.requestFocus();
                return;}
            else{
                update.child("name").setValue(EditName);
                this.recreate();
                Toast.makeText(this,"   Name updated successful",Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this,"FullName is similar, cant be changed",Toast.LENGTH_SHORT).show();
        }

        if(!EditUsername.equals(dbUsername)){
            if(TextUtils.isEmpty(EditUsername)) {
                userN2.setError("Please enter name");
                userN2.requestFocus();
                return;}
            else{
                update.child("username").setValue(EditUsername);
                this.recreate();
                Toast.makeText(this," Username updated successful",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Username is similar, cant be changed",Toast.LENGTH_SHORT).show();
        }
      /*  if(!EditEmail.equals(dbEmail)){
            if (TextUtils.isEmpty(EditEmail)) {
                email2.setError("Please enter valid email");
                email2.requestFocus();
                return;
            }else if (!Patterns.EMAIL_ADDRESS.matcher(EditEmail).matches()){
                email2.setError("Kindly enter valid email");
                email2.requestFocus();
                return;
            }else{
                update.child("email").setValue(EditEmail);
                this.recreate();
                Toast.makeText(this,"Email updated successful",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Email is similar, cant be changed",Toast.LENGTH_SHORT).show();
        }*/
        if(!EditContact.equals(dbContact)){
            if(TextUtils.isEmpty(EditUsername)) {
                userN2.setError("Please enter phone number");
                userN2.requestFocus();
                return;
            } else{
            update.child("phoneNumber").setValue(EditContact);
            this.recreate();
            Toast.makeText(this,"Contact updated successful",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Contact is similar, cant be changed",Toast.LENGTH_SHORT).show();
        }


    }


}