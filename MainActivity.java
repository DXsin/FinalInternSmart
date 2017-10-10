package com.example.terence.internsmartassistant;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import static android.support.v4.view.GravityCompat.START;

public class MainActivity extends AppCompatActivity {
    private Button Journal;
    private Button Calendar;
    private Button Clock;
    private TextView logout;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
       // button =(Button)findViewById(R.id.button);

        fAuth = FirebaseAuth.getInstance();
        fAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent loginIntent = new Intent(MainActivity.this, Login.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };

        logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        mDrawerLayout=(DrawerLayout)findViewById(R.id.activity_main);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.Open,R.string.Close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        onStart();

        Journal = (Button) findViewById(R.id.Journal);
        Journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), JournalMain.class);
                view.getContext().startActivity(Intent);}

        });

        Calendar = (Button) findViewById(R.id.Calendar);
        Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), Calendar.class);
                view.getContext().startActivity(Intent);}

        });
        Clock = (Button) findViewById(R.id.Clock);
        Clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), Clock.class);
                view.getContext().startActivity(Intent);}

        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(mToggle.onOptionsItemSelected(item)){
           if(id == R.id.logout){
               logOut();
           }

            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
       fAuth.signOut();
    }

    protected void onStart(){
        super.onStart();

        fAuth.addAuthStateListener(fAuthListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =(DrawerLayout) findViewById(R.id.activity_main);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawermenu,menu);
        return false;
    }

   public boolean onNavigationItemSelected(MenuItem item) {
       int id = item.getItemId();

       if (id == R.id.Profile) {
//           Intent sIntent = new Intent(MainActivity.this, Profile.class);
//           startActivity(sIntent);
//           overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
       }
       else if (id == R.id.Calculate) {
//           Intent sIntent = new Intent(MainActivity.this, Calculate.class);
//           startActivity(sIntent);
//           overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
       }
       else if (id==R.id.Settings){
//           Intent sIntent=new Intent(MainActivity.this,Settings.class);
//           startActivity(sIntent);
//           overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
        }
       else if (id==R.id.Logout){
          logOut();
       }

       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main);
       drawer.closeDrawer(GravityCompat.START);
        return true;
   }
}
