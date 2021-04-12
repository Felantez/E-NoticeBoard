package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class fPassword extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    ImageView back;
    MaterialButton fPassword;
    TextInputEditText fEmail;

    private FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_password);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(fPassword.this,logIn.class);
                startActivity(intent);
            }
        });


        fEmail=(TextInputEditText)findViewById(R.id.fEmail);
        fPassword=(MaterialButton) findViewById(R.id.fPassword);
        fPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forgotPassword();

    }
    public void forgotPassword(){
        String Email= fEmail.getText().toString().trim();

        if (Email.isEmpty()){
            fEmail.setError("Enter Email Address");
            fEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            fEmail.setError("Enter Valid Email Address");
            fEmail.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(fPassword.this, " Reset Email has been sent to the Email address provided", Toast.LENGTH_SHORT).show();
                     Intent intent= new Intent(fPassword.this, logIn.class);
                     startActivity(intent);
                }else {
                    Toast.makeText(fPassword.this, "There was am error, Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
        });

    }

}
