package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.logging.Handler;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        try {
            Thread.sleep(3000);
            Intent Loginscrn = new Intent(splashscreen.this,loginactivity.class);
            startActivity(Loginscrn);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}