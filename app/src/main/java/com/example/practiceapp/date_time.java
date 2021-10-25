package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class date_time extends AppCompatActivity {
    Button selectdate, Booknow; //selecttime
    String source, destination;
    private String TAG = getClass().getSimpleName();
    String[] bustype = {"Select Bus Type", "AC Bus", "Non AC Bus"};
    Spinner Bustype, availablebus;
    JSONObject busdata;
    JSONObject Bookings;
    JSONArray Bookinglist;
    String User_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);
        Bustype = findViewById(R.id.bustype_spinner);
        availablebus = findViewById(R.id.availble_bus);
        Bookings = new JSONObject();
        Bookinglist = new JSONArray();


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
//        selecttime = findViewById(R.id.selecttime);
        Booknow = findViewById(R.id.booknow);

        try {
            busdata = new JSONObject("{\"AC_Bus\":{\"1-11-2021\":[\"10:00 AM\",\"11:00  AM\",\"12:00 PM\",\"01:00 PM\",\"05:30 PM\",\"06:30 PM\",\"07:00 PM\",\"08:00 PM\"],\"2-11-2021\":[\"10:00 AM\",\"11:00  AM\",\"12:00 PM\",\"01:00 PM\",\"05:30 PM\",\"06:30 PM\",\"07:00 PM\",\"08:00 PM\"],\"3-11-2021\":[\"10:00 AM\",\"11:00  AM\",\"12:00 PM\",\"01:00 PM\",\"05:30 PM\",\"06:30 PM\",\"07:00 PM\",\"08:00 PM\"],\"4-11-2021\":[\"10:00 AM\",\"11:00  AM\",\"12:00 PM\",\"01:00 PM\",\"05:30 PM\",\"06:30 PM\",\"07:00 PM\",\"08:00 PM\",\"08:30 PM\"],\"5-11-2021\":[\"10:00 AM\",\"11:00  AM\",\"12:00 PM\",\"05:30 PM\",\"06:30 PM\",\"07:00 PM\",\"08:00 PM\"]},\"Non_AC_Bus\":{\"1-11-2021\":[\"10:00 AM\",\"11:30  AM\",\"01:00 PM\",\"05:30 PM\",\"07:00 PM\",\"08:00 PM\"],\"2-11-2021\":[\"10:00 AM\",\"11:30  AM\",\"01:00 PM\",\"05:30 PM\",\"07:00 PM\",\"08:00 PM\"],\"3-11-2021\":[\"10:00 AM\",\"11:30  AM\",\"01:00 PM\",\"05:30 PM\",\"07:00 PM\",\"08:00 PM\"],\"4-11-2021\":[\"10:00 AM\",\"11:30  AM\",\"01:00 PM\",\"05:30 PM\",\"07:00 PM\",\"08:00 PM\",\"08:30 PM\"],\"5-11-2021\":[\"10:00 AM\",\"11:30  AM\",\"01:00 PM\",\"05:30 PM\",\"07:00 PM\",\"08:00 PM\"]}}");
            Log.e(TAG, "onCreate: " + busdata);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        //select date and show on the button
        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(date_time.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if (Bustype.getSelectedItemPosition() != 0) {
                            Bustype.setSelection(0);
                        }
                        int exactmonth = month + 1;
                        selectdate.setText(dayOfMonth + "-" + (exactmonth) + "-" + year);
                        Log.e("TAG", "onDateSet:" + selectdate.getText().toString());

                    }
                });
                datePickerDialog.show();
            }
        });


        Bustype.setAdapter(new ArrayAdapter<>(date_time.this, android.R.layout.simple_dropdown_item_1line, bustype));

        Bustype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + Bustype.getSelectedItem().toString());
                if (Bustype.getSelectedItem().toString().equalsIgnoreCase("AC Bus") || Bustype.getSelectedItem().toString().equalsIgnoreCase("Non AC Bus")) {
                    try {


                        if (busdata.getJSONObject(Bustype.getSelectedItem().toString().replace(" ", "_")).has(selectdate.getText().toString())) {
                            JSONArray timing = busdata.getJSONObject(Bustype.getSelectedItem().toString().replace(" ", "_")).getJSONArray(selectdate.getText().toString());
                            Log.e(TAG, "onItemSelected: " + timing);
                            List<String> list = new ArrayList<>();
                            list.add("Select Bus");
                            for (int i = 0; i < timing.length(); i++) {
                                Log.e(TAG, "onItemSelected: " + timing.getString(i));
                                list.add(timing.getString(i));
                            }
                            Log.e(TAG, "onItemSelected: " + list);
                            availablebus.setAdapter(new ArrayAdapter<>(date_time.this, android.R.layout.simple_list_item_1, list));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // ****select time and show on the button
//        selecttime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                TimePickerDialog timePickerDialog = new TimePickerDialog(date_time.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        Log.e("TAG", "onTimeSet: " + hourOfDay + "-" + minute);
//                        selecttime.setText(+hourOfDay + ":" + minute);
//                    }
//                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
//                timePickerDialog.show();
//            }
//        });
//        ******

        Booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectdate.getText().toString().isEmpty()) {
                    Toast.makeText(date_time.this, "Please Select Proper Date.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Bustype.getSelectedItemPosition() == 0) {
                    Toast.makeText(date_time.this, "Please Select Bus Type as per your Choice..!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (availablebus.getSelectedItemPosition() == 0) {
                    Toast.makeText(date_time.this, "Select Available Buses For Booking .", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();


                User_id = preferences.getString("User_Id", "");
                Log.e(TAG, "Userid: " + User_id);

                try {
                    Bookings.put("User_id", User_id);
                    Bookings.put("Source", source);
                    Bookings.put("Destination", destination);
                    Bookings.put("Date", selectdate.getText().toString());
                    Bookings.put("Bus_Type", Bustype.getSelectedItem().toString());
                    Bookings.put("Bus_Time", availablebus.getSelectedItem().toString());


                    Bookinglist = new JSONArray(preferences.getString("Bookings", "[]"));
                    Bookinglist.put(Bookings);

                    editor.putString("Bookings", Bookinglist.toString());
                    Log.e(TAG, "onClick: " + Bookinglist.toString());

                    editor.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Intent reviewbooking = new Intent(date_time.this, review_booking.class);
                reviewbooking.putExtra("Bustype", Bustype.getSelectedItem().toString());
                reviewbooking.putExtra("selectedbus", availablebus.getSelectedItem().toString());
                startActivity(reviewbooking);

            }
        });


    }
}