package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class Home extends AppCompatActivity {

    Spinner source, destination;
    TextView Logo;
    ImageView logout;
    Button next;
    String[] sourcelist = {"Select Source","Thane", "Mulund", "Bhandup", "Kanjur", "Vikroli", "Powai"};
 //   String[] destinationlist = {"Select Destination","Thane", "Mulund", "Bhandup", "Kanjur", "Vikroli", "Powai"};
    SessionManage session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        session = new SessionManage(Home.this);


        source = findViewById(R.id.source);
        logout = findViewById(R.id.logout);
        destination = findViewById(R.id.destination);
        next = findViewById(R.id.next);

        Logo = findViewById(R.id.logo);
        Typeface font = Typeface.createFromAsset(getAssets(), "Creepster-Regular.ttf");
        Logo.setTypeface(font);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Home.this, android.R.layout.simple_spinner_dropdown_item, sourcelist);
        source.setAdapter(adapter);

        destination.setAdapter(adapter);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,loginactivity.class));
                finish();
               session.setLoginStatus(false);

            }
        });
        source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    Log.e(TAG, "onItemSelected: " + source.getSelectedItem().toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0){
                    Log.e(TAG, "onItemSelected: " + destination.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (source.getSelectedItemPosition()==0){
                    Toast.makeText(Home.this, "Please Select Source.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (destination.getSelectedItemPosition()==0){
                    Toast.makeText(Home.this, "Please Select Destination.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent gotonext = new Intent(Home.this, date_time.class);
                    gotonext.putExtra("source",source.getSelectedItem().toString());
                gotonext.putExtra("destination",destination.getSelectedItem().toString());
                startActivity(gotonext);
            }
        });
    }

}