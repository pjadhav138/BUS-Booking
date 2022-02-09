package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class date_time extends AppCompatActivity {
    Button selectdate, Booknow, Notification; //selecttime
    String source, destination;
    private String TAG = getClass().getSimpleName();
    String[] bustype = {"Select Bus Type", "AC Bus", "Non AC Bus"};
    Spinner Bustype, availablebus;
    JSONObject busdata;
    JSONObject Bookings;
    JSONArray Bookinglist;
    String User_id;
    private String CHANNEL_ID_1 = "11111";
    private String CHANNEL_ID_2 = "111122";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);
        Bustype = findViewById(R.id.bustype_spinner);
        Notification = findViewById(R.id.notification);
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


        Notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("MyNotification", "MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(date_time.this, "MyNotification")
                        .setContentTitle("Swift Ride")
                        .setSmallIcon(R.drawable.ic_baseline_message_24)
                        .setAutoCancel(true)
                        .setContentText("Booking Done Sucessfully");

                NotificationManagerCompat manager = NotificationManagerCompat.from(date_time.this);
                manager.notify(99, builder.build());


//                NotificationCompat.Builder mbuilder = (NotificationCompat.Builder)
//                        new NotificationCompat.Builder(getApplicationContext())
//                                .setSmallIcon(R.drawable.ic_baseline_message_24,10)
//                                .setContentTitle("Swift Ride")
//                                .setContentText("Booking Done Successfully");
//
//                NotificationManager notificationManager = (NotificationManager)
//                        getSystemService(NOTIFICATION_SERVICE);
//                notificationManager.notify(0,mbuilder.build());
            }
        });


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


                User_id = preferences.getString("User_id", "");
                Log.e(TAG, "User_id: " + User_id);

                try {
                    Bookings.put("User_id", User_id);
                    Bookings.put("Source", source);
                    Bookings.put("Destination", destination);
                    Bookings.put("Date", selectdate.getText().toString());
                    Bookings.put("Bus_Type", Bustype.getSelectedItem().toString());
                    Bookings.put("Bus_Time", availablebus.getSelectedItem().toString());


                    //saving all the booking values in book map
                    Iterator<String> iterator= Bookings.keys();
                    Map<String,Object> book= new HashMap<>();
                   while (iterator.hasNext()){
                       String key = iterator.next();
                       book.put(key,Bookings.get(key));
                   }


                   // uploading the book map onto server

                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://swift-ride-22040-default-rtdb.firebaseio.com/");
                    DatabaseReference Done_Booking = database.getReferenceFromUrl("https://swift-ride-22040-default-rtdb.firebaseio.com/User/"+Bookings.getString("User_id")+"/order");

                    Done_Booking.updateChildren(book);


                    Bookinglist = new JSONArray(preferences.getString("Bookings", "[]"));
                    Bookinglist.put(Bookings);

                    editor.putString("Bookings", Bookinglist.toString());
                    Log.e(TAG, "onClick: " + Bookinglist.toString());

                    editor.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                ****************


  /*

             upload  whole booking list to firebase data base


             try {
                  FirebaseDatabase database = FirebaseDatabase.getInstance("https://swift-ride-22040-default-rtdb.firebaseio.com/");
                  DatabaseReference Bookings = null;
                  for (int i = 0; i < Bookinglist.length(); i++) {
                      String userid = Bookinglist.getJSONObject(i).getString("User_id");
                      String Source = Bookinglist.getJSONObject(i).getString("Source");
                      String Destination = Bookinglist.getJSONObject(i).getString("Destination");
                      String Date = Bookinglist.getJSONObject(i).getString("Date");
                      String Bus_Type = Bookinglist.getJSONObject(i).getString("Bus_Type");
                      String Bus_Time = Bookinglist.getJSONObject(i).getString("Bus_Time");
                      Map<String,Object> map = new HashMap<>();
                      Map<String,Object> map1 = new HashMap<>();
                      map.put("User_id",userid);
                      map.put("Source",Source);
                      map.put("Destination",Destination);
                      map.put("Date",Date);
                      map.put("Bus_Type",Bus_Type);
                      map.put("Bus_Time",Bus_Time);
                      map1.put(Date+"_"+Bus_Time,map);

                      Bookings = database.getReferenceFromUrl("https://swift-ride-22040-default-rtdb.firebaseio.com/User/"+Bookinglist.getJSONObject(i).getString("User_id")+"/order");
                      Bookings.updateChildren(map1);
                  }

              } catch (JSONException e) {
                  e.printStackTrace();
              }
*/
//                ****************


                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = null;


                Intent reviewbooking = new Intent(date_time.this, review_booking.class);
                reviewbooking.putExtra("Bustype", Bustype.getSelectedItem().toString());
                reviewbooking.putExtra("selectedbus", availablebus.getSelectedItem().toString());
          /*      {
                    String message = "Congratulation ...Booking Successfully Done.!";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(date_time.this)
                            .setSmallIcon(R.drawable.ic_baseline_message_24)
                            .setContentTitle("Swift Booking")
                            .setContentText("Booking Done.")
                            .setAutoCancel(true);

                    Intent notification = new Intent(date_time.this, Booking_Notification.class);
                    notification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingintent = PendingIntent.getActivity(date_time.this, 100, notification, PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(pendingintent);

                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(100, builder.build());
                }*/

//                Intent notification = new Intent(date_time.this, Booking_Notification.class);
//                notification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                PendingIntent pendingintent = PendingIntent.getActivity(date_time.this, 100, notification, PendingIntent.FLAG_UPDATE_CURRENT);
//                createNotificationChannel(pendingintent);

                Intent intent = new Intent(date_time.this, Booking_Notification.class);

                // FLAG_UPDATE_CURRENT specifies that if a previous
                // PendingIntent already exists, then the current one
                // will update it with the latest intent
                // 0 is the request code, using it later with the
                // same method again will get back the same pending
                // intent for future reference
                // intent passed here is to our afterNotification class
                PendingIntent pendingIntent = PendingIntent.getActivity(date_time.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationChannel notificationChannel;
                // RemoteViews are used to use the content of
                // some different layout apart from the current activity layout
//                val contentView = RemoteViews(packageName, R.layout.activity_after_notification)
                builder = new NotificationCompat.Builder(date_time.this)
                        .setSmallIcon(R.drawable.ic_baseline_message_24)
                        .setContentTitle("Swift Booking")
                        .setContentText("Booking Done.")
                        .setAutoCancel(true);

                // checking if android version is greater than oreo(API 26) or not
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = new NotificationChannel("11111", "description", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(false);
                    manager.createNotificationChannel(notificationChannel);
                    builder = new NotificationCompat.Builder(date_time.this)
                            .setSmallIcon(R.drawable.ic_baseline_message_24)
                            .setContentTitle("Swift Booking")
                            .setContentText("Booking Done.")
                            .setAutoCancel(true);
                } else {

                }
                manager.notify(1234, builder.build());
                startActivity(reviewbooking);

            }
        });


    }

    private void createNotificationChannel(PendingIntent pendingintent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_baseline_message_24)
                .setContentTitle("textTitle")
                .setContentText("textContent")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID_2)
                .setSmallIcon(R.drawable.ic_baseline_message_24)
//                .setLargeIcon(thumb)
                .setContentTitle("Title")
                .setContentText("Text")
                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(imgUrl)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingintent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

/*        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification.setSmallIcon(playPauseBtn);
            notification.setColor(getResources().getColor(R.color.colorPrimary));
        } else {
            notification.setSmallIcon(playPauseBtn);
        }*/
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notification.build());
        notificationManager.notify(m, builder.build());
    }


}