package com.example.user.xpirianceprofileproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.postviewholder> {


    private Context mCtx;
    private List<userPosts> userpostlist;

    public Adapter(Context mCtx, List<userPosts> userPostlist) {
        this.mCtx = mCtx;
        this.userpostlist = userPostlist;
    }

    @Override
    public postviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_posts,parent,false);
        return new postviewholder(view);
    }

    @Override
    public void onBindViewHolder(postviewholder holder, int position) {
        userPosts userposts = userpostlist.get(position);

        holder.post.setText(String.valueOf(userposts.getPost()));


    }

    @Override
    public int getItemCount() {
        return userpostlist.size();
    }

    class postviewholder extends RecyclerView.ViewHolder {

        TextView post;


        public postviewholder(View itemView) {
            super(itemView);


            post=itemView.findViewById(R.id.Questions);


        }
    }
}