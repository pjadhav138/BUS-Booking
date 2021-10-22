package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class date_time extends AppCompatActivity {
    Button  selectdate, checkavailaibilty; //selecttime
    String source, destination;
    private String TAG = getClass().getSimpleName();
    String[] bustype= {"Select Bus Type","AC Bus","Non AC Bus"};
    Spinner Bustype,availablebus;
    JSONObject busdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);
        Bustype = findViewById(R.id.bustype_spinner);
        availablebus = findViewById(R.id.availble_bus);

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
        checkavailaibilty = findViewById(R.id.availability);

        try {
            busdata = new JSONObject("{\"AC_Bus\":{\"20-10-2021\":[\"10:10 A\",\"10:30 A\",\"11:00 A\",\"2:00 P\",\"6:00 P\",\"6:15 P\",\"7:00 P\",\"7:15 P\"],\"21-10-2021\":[\"10:10 A\",\"10:30 A\",\"11:00 A\",\"2:00 P\",\"6:00 P\",\"6:15 P\",\"7:00 P\",\"7:15 P\"],\"22-10-2021\":[\"10:10 A\",\"10:30 A\",\"11:00 A\",\"2:00 P\",\"6:00 P\",\"6:15 P\",\"7:00 P\",\"7:15 P\"],\"23-10-2021\":[\"10:10 A\",\"10:30 A\",\"11:00 A\",\"6:15 P\",\"7:00 P\",\"7:15 P\"],\"24-10-2021\":[\"10:10 A\",\"10:30 A\",\"11:00 A\",\"2:00 P\",\"6:00 P\",\"6:15 P\",\"7:00 P\",\"7:15 P\"]},\"Non_AC_Bus\":{\"24-10-2021\":[\"11:00 A\",\"2:00 P\",\"6:00 P\",\"7:00 P\"],\"25-10-2021\":[\"10:10 A\",\"10:30 A\",\"11:00 A\",\"2:00 P\",\"6:00 P\",\"6:15 P\",\"7:00 P\",\"7:15 P\"],\"26-10-2021\":[\"10:10 A\",\"10:30 A\",\"11:00 A\",\"2:00 P\",\"6:00 P\",\"6:15 P\",\"7:00 P\",\"7:15 P\"],\"27-10-2021\":[\"10:10 A\",\"10:30 A\",\"11:00 A\",\"6:15 P\",\"7:00 P\",\"7:15 P\"]}}");
            Log.e(TAG, "onCreate: "+busdata );

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
                        int exactmonth = month + 1;
                        selectdate.setText(dayOfMonth + "-" + (exactmonth) + "-" +year);
                        Log.e("TAG", "onDateSet:" +selectdate.getText().toString());

                    }
                });
                datePickerDialog.show();
            }
        });


        Bustype.setAdapter(new ArrayAdapter<>(date_time.this,android.R.layout.simple_dropdown_item_1line,bustype));

        Bustype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: "+Bustype.getSelectedItem().toString());
                if (Bustype.getSelectedItem().toString().equalsIgnoreCase("AC Bus")||Bustype.getSelectedItem().toString().equalsIgnoreCase("Non AC Bus")){
                    try {


                        if (busdata.getJSONObject(Bustype.getSelectedItem().toString().replace(" ","_")).has( selectdate.getText().toString())){
                            JSONArray timing = busdata.getJSONObject(Bustype.getSelectedItem().toString().replace(" ","_")).getJSONArray(selectdate.getText().toString());
                            Log.e(TAG, "onItemSelected: "+timing );
                            List<String> list = new ArrayList<>();
                            list.add("Select Bus");
                            for (int i=0;i<timing.length();i++){
                                Log.e(TAG, "onItemSelected: "+timing.getString(i) );
                                list.add(timing.getString(i));
                            }
                            Log.e(TAG, "onItemSelected: "+list );
                            availablebus.setAdapter(new ArrayAdapter<>(date_time.this,android.R.layout.simple_list_item_1,list));
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

        checkavailaibilty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Checkavailability = new Intent(date_time.this, Home.class);
                startActivity(Checkavailability);
            }
        });


    }
}