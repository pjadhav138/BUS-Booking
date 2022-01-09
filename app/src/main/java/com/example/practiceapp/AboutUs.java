package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class AboutUs extends AppCompatActivity {
WebView webview;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us2);
        toolbar = findViewById(R.id.back);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        webview = findViewById(R.id.webview);
        webview.loadUrl("https://github.com/daimajia/AndroidViewAnimations");

    }
}