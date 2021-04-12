package com.example.anew;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class testActivity extends AppCompatActivity {
    ImageView testImage;
    StorageReference storageReference= FirebaseStorage.getInstance().getReference("uploads");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testImage=findViewById(R.id.testImage);
        Glide.with(this).load(storageReference).into(testImage);
    }
}