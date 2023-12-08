package com.sem3.farmfusion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    Context context;
    ArrayList<Posts> Postlist;


    public PostAdapter(Context context, ArrayList<Posts> list) {
        this.context = context;
        this.Postlist = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.post_layout,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.MyViewHolder viewHolder, int i) {

        Posts post = Postlist.get(i);

        viewHolder.TV_dateTime.setText(post.dateTime);
        viewHolder.TV_postContent.setText(post.content);
        viewHolder.TV_userEmail.setText(post.userEmail);
        if(post.cropImageUri != null && !post.cropImageUri.isEmpty()) {
            viewHolder.Img_post_img.setImageURI(Uri.parse(post.cropImageUri));
            viewHolder.Img_post_img.setVisibility(View.VISIBLE);
        }

        viewHolder.IB_postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.BTN_postReply.setVisibility(View.VISIBLE);

            }
        });

        if(viewHolder.BTN_postReply.getVisibility() == View.VISIBLE)
        {
            viewHolder.BTN_postReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }


        }

    @Override
    public int getItemCount() {
        return Postlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView TV_userEmail,TV_dateTime,TV_postContent;
        ImageView Img_post_img;
        Button BTN_postReply;
        ImageButton IB_postComment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TV_postContent = itemView.findViewById(R.id.TV_postContent);
            TV_dateTime = itemView.findViewById(R.id.TV_dateTime);
            TV_userEmail = itemView.findViewById(R.id.TV_userEmail);
            Img_post_img = itemView.findViewById(R.id.Img_post_img);
            BTN_postReply = itemView.findViewById(R.id.BTN_postReply);
            IB_postComment = itemView.findViewById(R.id.IB_postComment);

        }
    }


}
