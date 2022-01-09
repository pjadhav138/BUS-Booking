package com.example.practiceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sendsms extends AppCompatActivity {
    EditText et_message;
    Button send;
    String phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendsms);
        et_message = findViewById(R.id.et_message);
        send = findViewById(R.id.btn_send);

        phoneno = "8898527975";



        //send sms
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(sendsms.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                    sendmessage();
                }
                else {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},100);
                }
            }
        });
    }

    private void sendmessage() {

        String message = et_message.getText().toString().trim();

        if (!message.equals("")){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneno,null,message,null,null);

            //successfull message
            Toast.makeText(this, "Text Message Sent Successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Please Enter Message first.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== 100 && grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            sendmessage();
        }else {
            Toast.makeText(this, "Please Give Permission To Send Text MEssage", Toast.LENGTH_SHORT).show();
        }
    }
}