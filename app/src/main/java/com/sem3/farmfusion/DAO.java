package com.sem3.farmfusion;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class DAO {

    public DatabaseReference getDatabaseReference(String db)
    {
        return FirebaseDatabase.getInstance().getReference(db);
    }

}
