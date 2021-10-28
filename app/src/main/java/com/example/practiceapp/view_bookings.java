package com.example.practiceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class view_bookings extends AppCompatActivity {
    ListView listView;
    String[] bookings = {"item 1", "item 2", "item 3", "item 4", "item 5", "item 6"};
    List<ListItem> lists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);
        listView = findViewById(R.id.list);
        lists = new ArrayList<>();
//        listView.setAdapter(new ArrayAdapter<>(view_bookings.this, android.R.layout.simple_list_item_1, bookings));
        ListItem obj = new ListItem("bhandup", "vikhroli", "25-10-2021", "AC Bus", "10:15 A");
        lists.add(obj);
        ListItem obj1 = new ListItem("bhandup", "vikhroli", "26-10-2021", "AC Bus", "10:15 A");
        lists.add(obj1);
        ListItem obj2 = new ListItem("bhandup", "vikhroli", "27-10-2021", "AC Bus", "10:15 A");
        lists.add(obj2);
        ListItem obj3 = new ListItem("bhandup", "vikhroli", "28-10-2021", "AC Bus", "10:15 A");
        lists.add(obj3);
        ListItem obj4 = new ListItem("bhandup", "vikhroli", "29-10-2021", "AC Bus", "10:15 A");
        lists.add(obj4);
        ListItem obj5 = new ListItem("bhandup", "vikhroli", "29-10-2021", "AC Bus", "10:15 A");
        lists.add(obj5);
        ListAdapter adapter = new ListAdapter(view_bookings.this,R.layout.list_row,lists);




        listView.setAdapter(adapter);


    }

    public class ListAdapter extends ArrayAdapter<ListItem>  {
        List<ListItem> booking_list;
        Context context;
        int resource;

        public ListAdapter(@NonNull Context context, int resource, @NonNull List<ListItem> objects) {
            super(context, resource, objects);
            this.context= context;
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
            TextView des = view.findViewById(R.id.des);
            ListItem item = booking_list.get(position);
            des.setText(item.getDate());
            return view;
        }
    }
}