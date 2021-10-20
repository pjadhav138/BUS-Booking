package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.io.ObjectInputStream;
import java.util.Calendar;

public class date_time extends AppCompatActivity {
    Button selecttime, selectdate, checkavailaibilty;
    String source, destination;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        Intent intent = getIntent();
        if (intent.hasExtra("source")) {
            source = intent.getStringExtra("source");
            Log.e(TAG, "onCreate: " + source);
        }
        if (intent.hasExtra("destination")) {
            destination = intent.getStringExtra("destination");
            Log.e(TAG, "onCreate: " + destination);
        }
        selectdate = findViewById(R.id.selectdate);
        selecttime = findViewById(R.id.selecttime);
        checkavailaibilty = findViewById(R.id.availability);


        //select date and show on the button
        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(date_time.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int exactmonth = month + 1;
                        Log.e("TAG", "onDateSet:" + exactmonth);
                        selectdate.setText(year + "-" + (exactmonth) + "-" + dayOfMonth);
                    }
                });
                datePickerDialog.show();
            }
        });


        //select time and show on the button
        selecttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(date_time.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.e("TAG", "onTimeSet: " + hourOfDay + "-" + minute);
                        selecttime.setText(+hourOfDay + ":" + minute);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });

        checkavailaibilty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Checkavailability = new Intent(date_time.this, Home.class);
                startActivity(Checkavailability);
            }
        });


    }
}