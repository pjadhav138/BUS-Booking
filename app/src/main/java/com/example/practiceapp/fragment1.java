package com.example.practiceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class fragment1 extends Fragment {
    int position;

    TextView textView;

    public fragment1(int position) {
        this.position = position;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        textView = view.findViewById(R.id.textview1);
        textView.setText("Visit Us Online");

        return view;

    }
}