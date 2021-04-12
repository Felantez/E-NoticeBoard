package com.example.anew;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class UploadCenter extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private MaterialButton choose, upload, uploadCCN,uploadCT,uploadIT;
    private TextInputEditText fileName;
    private ImageView preview;
    private ProgressBar progressBar;
    private Uri mImageUri;
    private DatabaseReference databaseReference,databaseReferenceCCN,databaseReferenceCT,databaseReferenceIT;
    private StorageReference storageReference, storageReferenceCCN,storageReferenceCT,storageReferenceIT;
    private StorageTask storageTask, storageTaskCCN, storageTaskCT,storageTaskIT;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Intent intent= new Intent(this,logIn.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_center);


        progressBar=findViewById(R.id.progressbar);
        choose=(MaterialButton) findViewById(R.id.ChoseFile);
        upload=findViewById(R.id.upload);
        uploadCCN= findViewById(R.id.uploadCCN);
        uploadCT= findViewById(R.id.uploadCT);
        uploadIT= findViewById(R.id.uploadIT);
        fileName=(TextInputEditText) findViewById(R.id.fileName);
        preview=(ImageView) findViewById(R.id.preview);

        progressBar.setVisibility(View.INVISIBLE);
        //general reference
        storageReference= FirebaseStorage.getInstance().getReference("uploads");
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        //CCN reference
        storageReferenceCCN= FirebaseStorage.getInstance().getReference("uploadsCCN");
        databaseReferenceCCN= FirebaseDatabase.getInstance().getReference("uploadsCCN");
        //CT reference
        storageReferenceCT= FirebaseStorage.getInstance().getReference("uploadsCT");
        databaseReferenceCT= FirebaseDatabase.getInstance().getReference("uploadsCT");
        //IT reference
        storageReferenceIT= FirebaseStorage.getInstance().getReference("uploadsIT");
        databaseReferenceIT= FirebaseDatabase.getInstance().getReference("uploadsIT");

        // select image from file manager/ local storage
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    openFileChoose();
            }
        });
                // upload to general
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storageTask !=null && storageTask.isInProgress()){
                    Toast.makeText(UploadCenter.this,"There is an upload in progress",Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });

        //upload to CCN
        uploadCCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storageTaskCCN !=null && storageTaskCCN.isInProgress()){
                    Toast.makeText(UploadCenter.this,"There is an upload in progress",Toast.LENGTH_SHORT).show();
                }else{
                    uploadFileCCN();
                }
            }
        });

        //upload to CT
        uploadCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storageTaskCT !=null && storageTaskCT.isInProgress()){
                    Toast.makeText(UploadCenter.this,"There is an upload in progress",Toast.LENGTH_SHORT).show();
                }else{
                    uploadFileCT();
                }
            }
        });

        //upload to IT
        uploadIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storageTaskIT !=null && storageTaskIT.isInProgress()){
                    Toast.makeText(UploadCenter.this,"There is an upload in progress",Toast.LENGTH_SHORT).show();
                }else{
                    uploadFileIT();
                }
            }
        });
    }
    private void openFileChoose() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST){
            if (resultCode==RESULT_OK && data !=null & data.getData() !=null){
                mImageUri = data.getData();
                Picasso.get().load(mImageUri).into(preview);
            }
        }
    }

    private  void uploadFile(){
        if (mImageUri !=null){
          StorageReference fileReference= storageReference.child( "file" + mImageUri.getLastPathSegment());
            storageTask= fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  Handler handler= new Handler();
                  handler.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          progressBar.setProgress(0);

                      }
                  },500);


                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                      @Override
                      public void onSuccess(Uri uri) {

                       HashMap <String,String>hashMap= new HashMap<>();
                       hashMap.put("url",String.valueOf(uri));
                       upload upload = new upload(fileName.getText().toString().trim(),String.valueOf(uri) );
                       String uploadId= databaseReference.push().getKey();
                       databaseReference.child(uploadId).setValue(upload);
                       progressBar.setVisibility(View.INVISIBLE);
                       fileName.setText("");
                       preview.setImageResource(0);
                          Toast.makeText(UploadCenter.this,"Upload SuccessFul",Toast.LENGTH_SHORT).show();
                      }
                      });

                  }
                 })
                  .addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          progressBar.setVisibility(View.INVISIBLE);
                          Toast.makeText(UploadCenter.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                      }
                  })
                  .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                          progressBar.setVisibility(View.VISIBLE);
                          double progress= (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                          progressBar.setProgress((int)progress);
                      }
                  });

        }else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    private  void uploadFileCCN(){
        if (mImageUri !=null){
            StorageReference fileReferenceCCN= storageReferenceCCN.child( "file" + mImageUri.getLastPathSegment());
            storageTaskCCN= fileReferenceCCN.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler= new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);

                        }
                    },500);


                    fileReferenceCCN.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            HashMap <String,String>hashMap= new HashMap<>();
                            hashMap.put("url",String.valueOf(uri));
                            upload upload = new upload(fileName.getText().toString().trim(),String.valueOf(uri) );
                            String uploadId= databaseReferenceCCN.push().getKey();
                            databaseReferenceCCN.child(uploadId).setValue(upload);
                            progressBar.setVisibility(View.INVISIBLE);
                            fileName.setText("");
                            preview.setImageResource(0);
                            Toast.makeText(UploadCenter.this,"Upload SuccessFul",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(UploadCenter.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                            double progress= (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);
                        }
                    });

        }else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    private  void uploadFileCT(){
        if (mImageUri !=null){
            StorageReference fileReferenceCT= storageReferenceCT.child( "file" + mImageUri.getLastPathSegment());
            storageTaskCT= fileReferenceCT.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler= new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);

                        }
                    },500);


                    fileReferenceCT.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            HashMap <String,String>hashMap= new HashMap<>();
                            hashMap.put("url",String.valueOf(uri));
                            upload upload = new upload(fileName.getText().toString().trim(),String.valueOf(uri) );
                            String uploadId= databaseReferenceCT.push().getKey();
                            databaseReferenceCT.child(uploadId).setValue(upload);
                            progressBar.setVisibility(View.INVISIBLE);
                            fileName.setText("");
                            preview.setImageResource(0);
                            Toast.makeText(UploadCenter.this,"Upload SuccessFul",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(UploadCenter.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                            double progress= (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);
                        }
                    });

        }else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    private  void uploadFileIT(){
        if (mImageUri !=null){
            StorageReference fileReferenceIT= storageReferenceIT.child( "file" + mImageUri.getLastPathSegment());
            storageTaskIT= fileReferenceIT.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler= new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);

                        }
                    },500);


                    fileReferenceIT.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            HashMap <String,String>hashMap= new HashMap<>();
                            hashMap.put("url",String.valueOf(uri));
                            upload upload = new upload(fileName.getText().toString().trim(),String.valueOf(uri) );
                            String uploadId= databaseReferenceIT.push().getKey();
                            databaseReferenceIT.child(uploadId).setValue(upload);
                            progressBar.setVisibility(View.INVISIBLE);
                            fileName.setText("");
                            preview.setImageResource(0);
                            Toast.makeText(UploadCenter.this,"Upload SuccessFul",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(UploadCenter.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                            double progress= (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);
                        }
                    });

        }else {
            Toast.makeText(this,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }
}