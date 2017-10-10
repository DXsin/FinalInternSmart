package com.example.terence.internsmartassistant.helpers;


import android.util.Log;

import com.example.terence.internsmartassistant.JournalModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;


/**
 * Created by Terence on 10/5/2017.
 */

public class Firebasehelper {
    DatabaseReference db;
    Boolean saved=null;
    ArrayList<JournalModel> modelList = new ArrayList<>();
    private FirebaseAuth fAuth;


    public Firebasehelper(DatabaseReference db) {
        this.db = db;



    }

    public Boolean save(JournalModel model){
        db = FirebaseDatabase.getInstance().getReference();
        if(model==null){
            saved=false;
        }
        else{
            try{


               // DatabaseReference current_user = db.child("JournalModel");
//                DatabaseReference current_user;


                fAuth = FirebaseAuth.getInstance();
                String user_id = fAuth.getCurrentUser().getUid();
                db = FirebaseDatabase.getInstance().getReference();

//                db.child("JournalModel").child("Users").child(user_id).child("Task");

                db.push().setValue(model);


                //Sakto na structure for entry

//                db.child(user_id).setValue(model);



               // db.child(user_id).push().setValue(model);
                Log.v("VALUE",db.toString());
                saved=true;
            }
            catch(DatabaseException e){
                e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }

    private void fetchData(DataSnapshot dataSnapshot){
       modelList.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            JournalModel model = ds.getValue(JournalModel.class);
            modelList.add(model);

        }

    }

    public ArrayList<JournalModel> retrieve(){
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.d("VALUE", "onChildAdded:" + dataSnapshot.getKey());
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return modelList;
    }

}
