package com.sem3.farmfusion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Community extends BottomSheetDialogFragment {



    public Community() {
        // Required empty public constructor
    }

ExtendedFloatingActionButton fab ;
    RecyclerView RV_posts;
    DatabaseReference dbPosts;
    ArrayList<Posts> postList ;
    PostAdapter postAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        fab = view.findViewById(R.id.fab_create_post);
        RV_posts = view.findViewById(R.id.RV_community_posts);
        dbPosts = DAO.getPostsDatabaseReference();
        postList = new ArrayList<Posts>();
        RV_posts.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter(getContext(),postList);
        RV_posts.setAdapter(postAdapter);


        dbPosts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot Snapshot : dataSnapshot.getChildren())
                {
                    Posts posts = Snapshot.getValue(Posts.class);
                    postList.add(posts);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






       // getPostsSetToRecyclerView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePostFragment post = new CreatePostFragment();

                Bundle args = new Bundle();
                args.putString("here","createPost");
                post.setArguments(args);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,post);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }
}