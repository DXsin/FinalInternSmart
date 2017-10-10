package com.example.terence.internsmartassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.example.terence.internsmartassistant.adapters.JournalViewHolder;

import com.example.terence.internsmartassistant.adapters.ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by Terence on 9/23/2017.
 */
public class JournalMain extends AppCompatActivity {
    ArrayAdapter rv;
    private DatabaseReference db;
    EditText editMessage, editIn, editOut;
    Button button;
    FirebaseAuth fAuth;
    JournalModel model;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal_main);

        lv = (ListView) findViewById(R.id.lv);
        fAuth = FirebaseAuth.getInstance();

        editMessage = (EditText)findViewById(R.id.editMessage);
        editIn = (EditText)findViewById(R.id.editIn);
        editOut = (EditText)findViewById(R.id.editOut);



        if(fAuth.getCurrentUser() != null){
            db = FirebaseDatabase.getInstance().getReference().child("Users").child(fAuth.getCurrentUser().getUid()).child("Task");
        }

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      //  helper = new Firebasehelper(db);

//        adapter = new MyAdapter(this, helper.retrieve());
//        rv.setAdapter(adapter);

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerAdapter<JournalModel,ViewHolder> firebaseuirecycler = new FirebaseRecyclerAdapter<JournalModel, ViewHolder>(
//                JournalModel.class,
//                R.layout.single_jour_layout,
//                ViewHolder.class,
//                db
//
//        ){
//
//            @Override
//            protected void populateViewHolder(final ViewHolder viewHolder, JournalModel model, int position) {
//                String jour_id = getRef(position).getKey();
//
//                db.child(jour_id).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        JournalModel model = new JournalModel();
//                        String message = dataSnapshot.getValue().toString();
//                        String in = dataSnapshot.getValue().toString();
//                        String out = dataSnapshot.getValue().toString();
//
//
//                        viewHolder.setJMessage(message);
//                        viewHolder.setJIn(in);
//                        viewHolder.setJOut(out);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        };
//
//        rv.setAdapter(firebaseuirecycler);
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent Intent = new Intent(JournalMain.this, JournalEntry.class);
            startActivity(Intent);
        } else if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            JournalModel model = new JournalModel();
            model.setMessage(ds.getValue(JournalModel.class).getMessage());
            model.setIn(ds.getValue(JournalModel.class).getIn());
            model.setOut(ds.getValue(JournalModel.class).getOut());


            //display all the information
//            Log.d(TAG, "showData: name: " + uInfo.getName());
//            Log.d(TAG, "showData: email: " + uInfo.getEmail());
//            Log.d(TAG, "showData: phone_num: " + uInfo.getPhone_num());

            ArrayList<String> array  = new ArrayList<>();
            array.add(model.getMessage());
            array.add(model.getIn());
            array.add(model.getOut());
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            lv.setAdapter(adapter);
        }
    }










}

