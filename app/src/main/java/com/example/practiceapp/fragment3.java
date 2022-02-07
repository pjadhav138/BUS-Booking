package com.example.practiceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class fragment3 extends Fragment {
    TextView textView;
    int position;

    public fragment3(int position) {
        this.position= position;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        textView = view.findViewById(R.id.textview);
        textView.setText("Easy Booking With Us, Enjoy Your Journey.");
        return view;

    }

}