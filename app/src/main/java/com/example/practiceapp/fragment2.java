package com.example.practiceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class fragment2 extends Fragment {
    int position;

    TextView textView;

    public fragment2(int position) {
        this.position = position;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        textView = view.findViewById(R.id.textview);
        textView.setText("Select You Journey Bus and Time.");

        return view;
    }
}

