package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeMain extends AppCompatActivity {

    Button booknow,viewbooking;
    TextView Logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        booknow=findViewById(R.id.book_now);
        viewbooking=findViewById(R.id.view_booking);
        Logo = findViewById(R.id.logo);
        Typeface font = Typeface.createFromAsset(getAssets(), "Creepster-Regular.ttf");
        Logo.setTypeface(font);

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent booknow = new Intent(HomeMain.this,Home.class);
                startActivity(booknow);
            }
        });

        viewbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewbooking = new Intent(HomeMain.this,view_bookings.class);
                startActivity(viewbooking);
            }
        });



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

}