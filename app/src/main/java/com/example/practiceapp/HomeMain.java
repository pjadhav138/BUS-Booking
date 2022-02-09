package com.example.practiceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeMain extends AppCompatActivity {

    Button googlemap, viewbooking, btn_viewpager;
    TextView Logo;
    Toolbar toolbar;
    private String TAG = getClass().getSimpleName();
    SessionManage session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        googlemap = findViewById(R.id.Google_Map);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        viewbooking = findViewById(R.id.view_booking);
        Logo = findViewById(R.id.logo);

        session = new SessionManage(HomeMain.this);


        btn_viewpager = findViewById(R.id.view_pager);
        Typeface font = Typeface.createFromAsset(getAssets(), "Creepster-Regular.ttf");
        Logo.setTypeface(font);


        googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent googlemap = new Intent(HomeMain.this, com.example.practiceapp.googlemap.class);
                startActivity(googlemap);
            }
        });


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://swift-ride-22040-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("User1");

        Map<String,Object> map = new HashMap<>();





        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: "+dataSnapshot.child("pankaj_011"));
                // Get Post object and use the values to update the UI
//                Post post = dataSnapshot.getValue(Post.class);
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        myRef.addValueEventListener(postListener);

       /*
       // on click listener previously used but now visibility gone and used in menu item option menu below down thier

       booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent booknow = new Intent(HomeMain.this, Home.class);
                startActivity(booknow);
            }
        });

        viewbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewbooking = new Intent(HomeMain.this, view_bookings.class);
                startActivity(viewbooking);
            }
        });


        // view pager button onclick listner
        btn_viewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn_viewpager = new Intent(HomeMain.this, view_pager.class);
                startActivity(btn_viewpager);
            }
        });

        */
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeMain.this);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage("Do you want to Exit?");
        builder.create();
        builder.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_book_now:
                Intent booknow = new Intent(HomeMain.this, Home.class);
                startActivity(booknow);
                return true;

            case R.id.menu_viewbooking:
                Intent viewbooking = new Intent(HomeMain.this, view_bookings.class);
                startActivity(viewbooking);
                return true;

            case R.id.menu_steps:
                Intent btn_viewpager = new Intent(HomeMain.this, view_pager.class);
                startActivity(btn_viewpager);
                return true;

            case R.id.menu_aboutus:
                Intent aboutus = new Intent(HomeMain.this, AboutUs.class);
                startActivity(aboutus);
                return true;

            case R.id.menu_contactus:
                Intent contactus = new Intent(HomeMain.this, contact_us.class);
                startActivity(contactus);
                return true;

            case R.id.menu_profile:
                startActivity(new Intent(HomeMain.this, myprofile.class));
                finish();
                return true;

            case R.id.logout:
                startActivity(new Intent(HomeMain.this, loginactivity.class));
                finish();
                session.setLoginStatus(false);
                return true;


            default:
                return false;
        }
    }
}