package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class dashBoard extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private ImageView back, home, logOut;
    private MaterialButton button1, button2;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        firebaseAuth=FirebaseAuth.getInstance();

        button1=(MaterialButton) findViewById(R.id.general);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(dashBoard.this, general.class);
                startActivity(intent);
            }
        });

        button2=(MaterialButton) findViewById(R.id.department);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(dashBoard.this,department.class);
                startActivity(intent);
            }
        });

        back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(dashBoard.this, logIn.class);
                startActivity(intent);
                finish();
            }
        });

        logOut=findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent= new Intent(dashBoard.this, logIn.class);
                intent.putExtra("finish",true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_CLEAR_TASK|
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
}