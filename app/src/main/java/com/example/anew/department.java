package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class department extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    ImageView back;

    MaterialButton Ccn, It, Ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        Ccn=findViewById(R.id.CCN);
        Ccn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(department.this,ccn.class);
                startActivity(intent);
                return;
            }
        });

        Ct=findViewById(R.id.CT);
        Ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(department.this,CT.class);
                startActivity(intent);
                return;
            }
        });
        It= findViewById(R.id.IT);
        It.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(department.this, IT.class);
                startActivity(intent);
                return;
            }
        });
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(department.this, dashBoard.class);
                startActivity(intent);
                return;
            }
        });
    }
}