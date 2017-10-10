package com.example.terence.internsmartassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity  {

    // UI references.
    private EditText editEmail;
    private EditText editPassword;
    private Button login;
    private TextView signup;
    private FirebaseAuth fAuth;
    private DatabaseReference reference;
    private ProgressDialog mPd;
    private FirebaseAuth.AuthStateListener fAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        editEmail = (EditText)findViewById(R.id.editEmail);
        editPassword = (EditText)findViewById(R.id.editPassword);
        login = (Button)findViewById(R.id.login);
        signup = (TextView)findViewById(R.id.signup);

        mPd = new ProgressDialog(this);
        fAuth = FirebaseAuth.getInstance();
        //String user_id = fAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");


        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSignup = new Intent(Login.this,Register.class);
                toSignup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(toSignup);
            }
        });

        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    private void checkLogin() {
        String user_id = fAuth.getCurrentUser().getUid();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                mPd.setMessage("Logging in...");
                mPd.show();
            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mPd.dismiss();
                        checkUserExist();

                    }
                    else
                        mPd.dismiss();
                        Toast.makeText(Login.this,"Failed to Login",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void checkUserExist() {
        final String user_id = fAuth.getCurrentUser().getUid();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id)){
                    Intent toMain = new Intent(Login.this,MainActivity.class);
                    toMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toMain);
                }
                else{
                    Toast.makeText(Login.this,"Failed! no such user existed",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

