package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class logIn extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    ImageView imageView;
    TextView label, slogan;
    MaterialButton btnToDashBord,fPassword;
    ProgressBar progressBar;
    Button next, admin;
    TextInputEditText editEmail, editPassword;

    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    SharedPreferences sharedPreferences;
    private static final String saveFileName ="login";
    public static final String saveEmail ="Email";
    public static final String savePassword ="PassWord";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        label=findViewById(R.id.label);
        slogan=findViewById(R.id.slogan);
        imageView=findViewById(R.id.imageView);
        admin=findViewById(R.id.admin);
        progressBar=findViewById(R.id.circular);
        progressBar.setVisibility(View.INVISIBLE);


        sharedPreferences= getSharedPreferences(saveFileName, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(saveEmail)){
            Intent intent= new Intent(this,dashBoard.class);
            startActivity(intent);
        }


        mAuth = FirebaseAuth.getInstance();

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(logIn.this,adm.class );
                startActivity(intent);

            }
        });

        editEmail=(TextInputEditText) findViewById(R.id.editTextEmail);
        editPassword=(TextInputEditText)findViewById(R.id.editTextPassWord);
        btnToDashBord=(MaterialButton) findViewById(R.id.toDashBoard);
        fPassword=findViewById(R.id.button2);
        fPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(logIn.this, fPassword.class);
                startActivity(intent);
            }
        });

        next=findViewById(R.id.toSignUp);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(logIn.this,SignUp.class);
                Pair[] pairs = new Pair[2];
                pairs[0]=new Pair< View, String >(imageView, "logo");
                pairs[1]=new Pair< View, String >(label, "label");

                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(logIn.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnToDashBord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDash();

            }

        });
    }
    public void toDash(){
       String Email=editEmail.getText().toString().trim();
       String PassWord=editPassword.getText().toString().trim();

       if (Email.equals(saveEmail) && PassWord.equals(savePassword)){
           SharedPreferences.Editor editor= sharedPreferences.edit();
           editor.putString(saveEmail,Email);
           editor.putString(savePassword,PassWord);
           editor.commit();
           Intent intent= new Intent(this,dashBoard.class);
           startActivity(intent);

       }


       if(Email.isEmpty()){
           editEmail.setError("Enter userName");
           editEmail.requestFocus();
           return;
       }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editEmail.setError("Enter Valid Email Address");
            editEmail.requestFocus();
            return;
        }

       if (PassWord.isEmpty()){
           editPassword.setError("Enter Password");
           editPassword.requestFocus();
           return;
       }
        if(PassWord.length()<8){
            editPassword.setError("Password Should be 8 characters or more");
            editPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(Email,PassWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(logIn.this, "Welcome to E-notice", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(logIn.this,dashBoard.class);
                    startActivity(intent);
                    
                    editEmail.setText("");
                    editPassword.setText("");
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(logIn.this, "Failed to Log In, Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}