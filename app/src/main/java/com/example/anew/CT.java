package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CT extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    // navigation

    private static final String TAG = "general";
    private ImageView back, refresh,home,logOut;
    private ctAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private DatabaseReference databaseReference;
    private List<upload> mUploads;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct);

        mRecyclerView= (RecyclerView)findViewById(R.id.recyclerViewCT);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUploads= new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploadsCT");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    upload upLoad= postSnapshot.getValue(upload.class);
                    mUploads.add(upLoad);
                }
                mAdapter = new ctAdapter(CT.this,mUploads);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CT.this, (CharSequence) error,Toast.LENGTH_SHORT).show();
            }
        });

        //take user back to previous screen
        back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setBackgroundColor(Color.GRAY);
                Intent intent= new Intent(CT.this,department.class);
                startActivity(intent);
                finish();
            }
        });
        //refresh button to reload the content
        refresh=(ImageView) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh.animate().rotation(refresh.getRotation()+720).start();
                return;
            }
        });
        //redirect user to home screen
        home=findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CT.this,logIn.class);
                startActivity(intent);
                finish();
            }
        });
        //logOut Button to log user out
        logOut=findViewById(R.id.logOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent= new Intent(CT.this,logIn.class);
                intent.putExtra("finish",true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_CLEAR_TASK|
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        Log.d(TAG, "onCreate: started");

    }

}