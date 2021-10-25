package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class review_booking extends AppCompatActivity {
    TextView Logo, source, destination, selected_date, selected_bus, user_id, bustype;
    String bookings, User_id;
    JSONObject js_bookinglist;
    JSONArray js_bookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_booking);
        js_bookings = new JSONArray();
        js_bookinglist = new JSONObject();

        Logo = findViewById(R.id.logo);
        Typeface font = Typeface.createFromAsset(getAssets(), "Creepster-Regular.ttf");
        Logo.setTypeface(font);
        source = findViewById(R.id.Selected_source);
        destination = findViewById(R.id.Selected_destination);
        selected_date = findViewById(R.id.Selected_date);
        selected_bus = findViewById(R.id.Selected_bus);
        user_id = findViewById(R.id.Userid);
        bustype = findViewById(R.id.Selected_bustype);


        SharedPreferences preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        bookings = preferences.getString("Bookings", "{}");
        try {
            User_id = preferences.getString("User_Id", "");
            user_id.setText(User_id);

            js_bookings = new JSONArray(bookings);
            js_bookinglist = js_bookings.getJSONObject(js_bookings.length() - 1);

            String Source = js_bookinglist.getString("Source");
            source.setText(Source);
            String Destination = js_bookinglist.getString("Destination");
            destination.setText(Destination);
            String Selected_date = js_bookinglist.getString("Date");
            selected_date.setText(Selected_date);
            String Bus_type = js_bookinglist.getString("Bus_Type");
            bustype.setText(Bus_type);
            String Selected_bus = js_bookinglist.getString("Bus_Time");
            selected_bus.setText(Selected_bus);


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}