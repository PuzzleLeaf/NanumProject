package com.puzzleleaf.nanum;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by cmtyx on 2016-11-25.
 */

public class FireBaseData {

    FirebaseDatabase database;
    DatabaseReference myRef;


    FireBaseData(String key){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(key);
    }

    DatabaseReference getRef()
    {
        return myRef;
    }

    void resetRef()
    {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MusicData.mdata.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("qwe",child.getKey().toString()+"간다~~~!");
                    MusicData.mdata.put(child.getKey(),(HashMap<String, String>)child.getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
