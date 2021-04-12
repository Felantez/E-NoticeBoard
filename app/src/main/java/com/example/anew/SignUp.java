package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.TextUtilsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    Button login, signUpToF;
    TextInputEditText eName, eUsername,eEmail,ePhoneNumber,ePassword;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        eName=findViewById(R.id.name);
        eUsername=findViewById(R.id.username);
        eEmail=findViewById(R.id.Email);
        ePhoneNumber=findViewById(R.id.phone);
        ePassword=findViewById(R.id.passWord);
        login=findViewById(R.id.toLogIn);

        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login= new Intent(SignUp.this,logIn.class);
                startActivity(login);
                finish();
            }
        });

        signUpToF=findViewById(R.id.signUpToDatabase);
        signUpToF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   toDatabase();
            }
        });

    }
    public void toDatabase(){
        String name=eName.getText().toString().trim();
        String username=eUsername.getText().toString().trim();
        String email=eEmail.getText().toString().trim();
        String phoneNumber=ePhoneNumber.getText().toString().trim();
        String password=ePassword.getText().toString().trim();


              if(TextUtils.isEmpty(name)) {
                  eName.setError("Please enter name");
                  eName.requestFocus();
                  return;
              }else if (TextUtils.isEmpty(username)) {
                  eUsername.setError("Please enter username");
                  eUsername.requestFocus();
                  return;
              } else if (TextUtils.isEmpty(email)) {
                  eEmail.setError("Please enter valid email");
                  eEmail.requestFocus();
                  return;
              }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                   eEmail.setError("Kindly enter valid email");
                   eEmail.requestFocus();
                  return;
              }
              else if (TextUtils.isEmpty(phoneNumber)) {
                  ePhoneNumber.setError("Please enter phone number");
                  ePhoneNumber.requestFocus();
                  return;
              } else if (TextUtils.isEmpty(password)) {
                  ePassword.setError("Please enter password");
                  ePassword.requestFocus();
                  return;
              } else if (password.length()<8){
                  ePassword.setError("Password should be 8 character or more");
                  ePassword.requestFocus();
                  return;
              }

              else{
                  
                  mAuth.createUserWithEmailAndPassword(email,password)
                          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {
                                  signUpData data = new signUpData(name, username, email, phoneNumber, password);
                                  FirebaseDatabase.getInstance().getReference("Users")
                                          .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                          .setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                      @Override
                                      public void onComplete(@NonNull Task<Void> task) {
                                          if (task.isSuccessful()) {

                                              Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                              Intent intent = new Intent(SignUp.this, logIn.class);
                                              startActivity(intent);
                                              finish();
                                          } else {
                                              Toast.makeText(SignUp.this, "Failed to register user, try again", Toast.LENGTH_SHORT).show();
                                          }
                                      }
                                  });
                              }

                          });

          }
    }
}