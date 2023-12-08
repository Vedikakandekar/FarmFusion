package com.sem3.farmfusion;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class DAO {

    public static DatabaseReference getUserDatabaseReference()
    {
        return FirebaseDatabase.getInstance().getReference("User");
    }

    public static DatabaseReference getPostsDatabaseReference()
    {
        return FirebaseDatabase.getInstance().getReference("Posts");
    }
}
