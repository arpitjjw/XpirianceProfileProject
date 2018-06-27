package com.example.user.xpirianceprofileproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.user_posts, null);
        return new postviewholder(view);
    }

    @Override
    public void onBindViewHolder(postviewholder holder, int position) {
        userPosts userposts = userpostlist.get(position);
        holder.time.setText(String.valueOf(userposts.getTime()));
        holder.date.setText(String.valueOf(userposts.getDate()));
        holder.post.setText(String.valueOf(userposts.getPost()));
    }

    @Override
    public int getItemCount() {
        return userpostlist.size();
    }

    class postviewholder extends RecyclerView.ViewHolder {

        TextView time,date,post;

        public postviewholder(View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.Time);
            post=itemView.findViewById(R.id.Post);
            date=itemView.findViewById(R.id.Date) ;
        }
    }
}