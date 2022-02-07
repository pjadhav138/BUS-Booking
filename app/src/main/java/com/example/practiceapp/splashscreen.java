package com.example.practiceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class splashscreen extends AppCompatActivity {
    SharedPreferences preferences;
    TextView companyname;
    private String TAG=getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        companyname= findViewById(R.id.companyname);
        Typeface font = Typeface.createFromAsset(getAssets(),"Creepster-Regular.ttf");
        companyname.setTypeface(font);

        FirebaseMessaging.getInstance().subscribeToTopic("global");
/*
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.e(TAG, "onComplete: "+token );
                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
*/

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                boolean isloggedIn = preferences.getBoolean("Is_login", false);
                if (isloggedIn) {
                    startActivity(new Intent(splashscreen.this, HomeMain.class));
                    finish();
                } else {
                    Intent Loginscrn = new Intent(splashscreen.this, loginactivity.class);
                    startActivity(Loginscrn);
                    finish();
                }
            }
        }, 2000);


    }
}