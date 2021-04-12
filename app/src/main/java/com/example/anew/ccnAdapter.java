package com.example.anew;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ccnAdapter extends RecyclerView.Adapter <ccnAdapter.ViewHolder>{

    private Context mContext;
    private List<upload> mUploads;

    public ccnAdapter(){

    }

    public ccnAdapter(Context mContext, List<upload> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @NonNull
    @Override
    public ccnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.ccncard,parent,false);
        return new ccnAdapter.ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        upload uploadCurrent = mUploads.get(position);
        holder.text.setText(uploadCurrent.getmName());
        Picasso.get().load(uploadCurrent.getmImageUrl()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView text;
        public ImageView imageView;

        public ViewHolder(View itemView){
            super(itemView);
            text=itemView.findViewById(R.id.TextView2);
            imageView=itemView.findViewById(R.id.img2);
        }
    }
}
