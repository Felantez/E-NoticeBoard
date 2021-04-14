package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class adm extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private TextInputEditText adminEmail, adminPassword;
    private MaterialButton button;
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm);

        adminEmail= (TextInputEditText)findViewById(R.id.adminEmail);
        adminPassword= (TextInputEditText)findViewById(R.id.adminPassWord);

        button=findViewById(R.id.signIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAdmin();
            }
        });
    }
    public void toAdmin(){
        String Email= adminEmail.getText().toString().trim();
        String Password= adminPassword.getText().toString().trim();

        if (Email.isEmpty()){
            adminEmail.setError("Enter Email");
            adminEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            adminEmail.setError("Enter Valid Email Address");
            adminEmail.requestFocus();
            return;
        }
        if (Password.isEmpty()){
            adminPassword.setError("Enter Password");
            adminPassword.requestFocus();
            return;
        }
        if(Password.length()<8){
            adminPassword.setError("Password Should be 8 characters or more");
            adminPassword.requestFocus();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(adm.this, "Welcome to Admin page", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(adm.this,UploadCenter.class);
                    startActivity(intent);
                    adminEmail.setText("");
                    adminPassword.setText("");
                }else {
                    Toast.makeText(adm.this, "Failed to Log In, Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}