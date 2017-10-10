package com.example.terence.internsmartassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Terence on 10/9/2017.
 */

public class Register extends AppCompatActivity {

    private EditText editName;
    private EditText editEmail;
    private EditText editPassword;
    private EditText editCompany;
    private TextView next;

    private FirebaseAuth fAuth;
    private ProgressDialog mPd;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        editName = (EditText) findViewById(R.id.editName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editCompany = (EditText) findViewById(R.id.editCompany);
        next = (TextView) findViewById(R.id.next);
        fAuth = FirebaseAuth.getInstance();

       // reference = FirebaseDatabase.getInstance().getReference().child("JournalModel").child("Users");
       // reference = FirebaseDatabase.getInstance().getReference().child("Users");

        mPd = new ProgressDialog(this);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });




    }

    private void startRegister() {
        final String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
       final String company = editCompany.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) &&
                !TextUtils.isEmpty(company)){

                mPd.setMessage("Signing up!");
                mPd.show();

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        String user_id = fAuth.getCurrentUser().getUid();

                        //DatabaseReference current_user = reference.child("Users").child(user_id);
                        DatabaseReference current_user = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                        Log.v("Current User",current_user.toString());
                        current_user.child("name").setValue(name);
                        current_user.child("image").setValue("default");
                        current_user.child("company").setValue(company);
                        current_user.child("university").setValue("default");
                        current_user.child("program").setValue("default");


                        mPd.dismiss();

                        Intent toEdit = new Intent(Register.this,MainActivity.class);
                        startActivity(toEdit);


                    }
                }
            });

        }
    }

}
