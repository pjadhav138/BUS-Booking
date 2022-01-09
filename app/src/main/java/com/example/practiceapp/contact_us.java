package com.example.practiceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class contact_us extends AppCompatActivity {

    //    ImageButton callus;
    Button btn_callus, btn_mailus, btn_sms;
    EditText editText;

    private static final int CALL_REQUEST = 101;
    private static final int REQUEST_CAMERA_CAPTURE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
//        callus =  findViewById(R.id.ib_callus);
        btn_callus = findViewById(R.id.btn_callus);
        btn_mailus = findViewById(R.id.btn_mailus);
        btn_sms = findViewById(R.id.btn_sms);

        //animation
        YoYo.with(Techniques.RollIn)
                .duration(700)
                .repeat(1)
                .playOn(findViewById(R.id.ib_callus));
//        callus.setAnimation(AnimationUtils.loadAnimation(contact_us.this,R.anim.rotate_animation));


        // Send sms jump  to activity
        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosms = new Intent(contact_us.this,sendsms.class);
                startActivity(gotosms);
            }
        });

        // send email jump to activity
        btn_mailus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomail = new Intent(contact_us.this,sendmail.class);
                startActivity(gotomail);
            }
        });

        //call us
        btn_callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callus = new Intent(Intent.ACTION_CALL);
                callus.setData(Uri.parse("tel:8898527975"));

                if (ContextCompat.checkSelfPermission(contact_us.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callus);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST);
                }
            }
        });




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CALL_REQUEST:
                Log.e("TAG", "onRequestPermissionsResult: " + grantResults[0]);
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}