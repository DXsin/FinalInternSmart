package com.example.terence.internsmartassistant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Terence on 9/29/2017.
 */

public class JournalEntry extends AppCompatActivity {
    private EditText editMessage;
    private EditText editIn;
    private EditText editOut;
    private Button button;
    private DatabaseReference db;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal_entry);

        fAuth = FirebaseAuth.getInstance();
        editMessage = (EditText) findViewById(R.id.editMessage);
        editIn = (EditText) findViewById(R.id.editIn);
        editOut = (EditText) findViewById(R.id.editOut);
        button = (Button) findViewById(R.id.button);
        //artists = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(fAuth.getCurrentUser().getUid());
       final JournalModel model = new JournalModel();
        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String message = editMessage.getText().toString().trim();
                String in = editIn.getText().toString().trim();
                String out = editOut.getText().toString().trim();
                model.setMessage(message);
                model.setIn(in);
                model.setOut(out);

                if(!TextUtils.isEmpty(message) && !TextUtils.isEmpty(in) && !TextUtils.isEmpty(out))

                createJournal(model);
            }
        });
    }



    private void createJournal(JournalModel model) {

        if (fAuth.getCurrentUser() != null) {



            final DatabaseReference newRef = db.child("Task").push();
            final Map noteMap = new HashMap();
            noteMap.put("message", model.getMessage());
            noteMap.put("in", model.getIn());
            noteMap.put("out", model.getOut());

            newRef.setValue(noteMap);
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    newRef.setValue(noteMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//
//                                Toast.makeText(JournalEntry.this, "Entry added", Toast.LENGTH_LONG).show();
//
//                            } else {
//                                Toast.makeText(JournalEntry.this, "Error", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//
//                }
//            });
//            thread.start();
        } else {
            Toast.makeText(JournalEntry.this, "User not signed in!", Toast.LENGTH_LONG).show();
        }


    }



}
