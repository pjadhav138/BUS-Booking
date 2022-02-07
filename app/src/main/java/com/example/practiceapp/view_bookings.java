package com.example.practiceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class view_bookings extends AppCompatActivity {
    ListView listView;
    String[] bookings = {"item 1", "item 2", "item 3", "item 4", "item 5", "item 6"};
    List<ListItem> lists;
    RecyclerView recycler;
    Button backtohome;
    SessionManage session;
    SharedPreferences preferences;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);
        session = new SessionManage(view_bookings.this);
        backtohome = findViewById(R.id.back);
        listView = findViewById(R.id.list);
        recycler = findViewById(R.id.recycler);
        lists = new ArrayList<>();
        preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        String bookingList = preferences.getString("Bookings", "[]");
        Log.e(TAG, "onCreate: " + bookingList);

//        listView.setAdapter(new ArrayAdapter<>(view_bookings.this, android.R.layout.simple_list_item_1, bookings));


        try {
            JSONArray array = new JSONArray(bookingList);
            for (int i = 0; i < array.length(); i++) {
                Log.e(TAG, "onCreate: ");
                String User_id = array.getJSONObject(i).getString("User_id");
                String Source = array.getJSONObject(i).getString("Source");
                String Destination = array.getJSONObject(i).getString("Destination");
                String Date = array.getJSONObject(i).getString("Date");
                String Bus_Type = array.getJSONObject(i).getString("Bus_Type");
                String Bus_Time = array.getJSONObject(i).getString("Bus_Time");
                if (User_id.equalsIgnoreCase(preferences.getString("User_Id", "NA"))) {
                    ListItem item = new ListItem(Source, Destination, Date, Bus_Type, Bus_Time, User_id);
                    lists.add(item);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        ListItem obj4 = new ListItem("bhandup", "vikhroli", "29-10-2021", "AC Bus", "10:15 A");
//        lists.add(obj4);
//        ListItem obj5 = new ListItem("bhandup", "vikhroli", "29-10-2021", "AC Bus", "10:15 A");
//        lists.add(obj5);


        //list view adapter

        ListAdapter adapter = new ListAdapter(view_bookings.this, R.layout.list_row, lists);
        listView.setAdapter(adapter);


        // recycler view code for setting adapter

        RecyclerAdapter adaper = new RecyclerAdapter(lists);
        recycler.setLayoutManager(new GridLayoutManager(view_bookings.this, 3, RecyclerView.VERTICAL, false));
        recycler.setAdapter(adaper);


        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtohome = new Intent(view_bookings.this, HomeMain.class);
                startActivity(backtohome);
                finish();
            }
        });

    }

    // recycler view

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewholder> {
        List<ListItem> booking_list;

        public RecyclerAdapter(List<ListItem> booking_list) {
            this.booking_list = booking_list;
        }

        @NonNull
        @Override
        public RecyclerAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(view_bookings.this).inflate(R.layout.list_row, null);
            MyViewholder holder = new MyViewholder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewholder holder, int position) {
            ListItem item = booking_list.get(position);
            final int holderposition = position;
            holder.source.setText(item.getSource());
            holder.source.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    Toast toast = Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER,0,0);
//                    toast.show();

                    final Dialog dialog = new Dialog(view_bookings.this);
                    final ListView listView = new ListView(view_bookings.this);
                    final List<String> list = new ArrayList<>();
                    list.add("change source");
                    list.add("update source");
                    listView.setAdapter(new ArrayAdapter<String>(view_bookings.this, android.R.layout.simple_list_item_1, list));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.e(TAG, "onItemClick: " + listView.getAdapter().getItem(position).toString());
                            switch (listView.getAdapter().getItem(position).toString()) {
                                case "change source":
                                    listView.setAdapter(new ArrayAdapter<String>(view_bookings.this, android.R.layout.simple_list_item_1, bookings));
                                    break;
                                case "update source":
                                    listView.setAdapter(new ArrayAdapter<String>(view_bookings.this, android.R.layout.simple_list_item_1, bookings));
                                    break;
                                case "item 1":
                                    booking_list.get(holderposition).setSource("item 1");
                                    RecyclerAdapter.this.notifyDataSetChanged();
                                    session.setItem("item 1", holderposition);
                                    dialog.dismiss();

                                    break;
                                case "item 2":
                                    booking_list.get(holderposition).setSource("item 2");
                                    RecyclerAdapter.this.notifyDataSetChanged();
                                    session.setItem("item 2", holderposition);
                                    dialog.dismiss();

                                    break;


                            }
                        }
                    });
                    dialog.setContentView(listView);
                    dialog.show();

                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return booking_list.size();
        }

        public class MyViewholder extends RecyclerView.ViewHolder {
            TextView source, destination, date, bustype, bustime;

            public MyViewholder(@NonNull View itemView) {
                super(itemView);
                source = itemView.findViewById(R.id.vb_source);
            }
        }
    }

    //listview code for setting adapter and values

    public class ListAdapter extends ArrayAdapter<ListItem> {
        List<ListItem> booking_list;
        Context context;
        int resource;

        public ListAdapter(@NonNull Context context, int resource, @NonNull List<ListItem> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.booking_list = objects;
        }


        @Override
        public int getCount() {
            return booking_list.size();
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(resource, null);
            ListItem item = booking_list.get(position);

            TextView date = view.findViewById(R.id.vb_date);
            date.setText(item.getDate());

            TextView source = view.findViewById(R.id.vb_source);
            source.setText(item.getSource());

            TextView destination = view.findViewById(R.id.vb_destination);
            destination.setText(item.getDestination());

            TextView bus_type = view.findViewById(R.id.vb_bustype);
            bus_type.setText(item.getBusType());

            TextView bus_time = view.findViewById(R.id.vb_bustime);
            bus_time.setText(item.getTime());

//            TextView des = view.findViewById(R.id.des);
            //  des.setText(item.getDate());
            return view;


        }

    }

}