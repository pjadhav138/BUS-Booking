package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class sendmail extends AppCompatActivity {
EditText et_subject,et_message;
Button sendmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmail);
        et_subject = findViewById(R.id.et_subject);
        et_message = findViewById(R.id.et_mail_message);
        sendmail = findViewById(R.id.btn_sendmail);
        final String mailid = "rushikeshshingan61@gmail.com";

        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendmail = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
                sendmail.setType("text/plain");

                sendmail.putExtra(Intent.EXTRA_EMAIL,new String[]{mailid,"pjadhav138@gmail.com"});
                sendmail.putExtra(Intent.EXTRA_SUBJECT,et_subject.getText().toString());
                sendmail.putExtra(Intent.EXTRA_TEXT,et_message.getText().toString());
                startActivity(sendmail);
            }
        });

    }
}