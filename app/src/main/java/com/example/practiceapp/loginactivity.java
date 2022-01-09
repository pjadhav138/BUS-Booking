package com.example.practiceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class loginactivity extends AppCompatActivity {

    EditText email, password;
    Button loginbtn, registerbtn;
    JSONObject Userdetailslist;
SessionManage session;
    String list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManage(loginactivity.this);
        setContentView(R.layout.activity_loginactivity);
        email = findViewById(R.id.loginemailid);
        password = findViewById(R.id.loginpassword);
        loginbtn = findViewById(R.id.loginbtn);
        registerbtn = findViewById(R.id.registerbtn);
        Userdetailslist = new JSONObject();


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = session.getUserDetails();
                //changes done
                try {
                    Userdetailslist = new JSONObject(list);
                    if (Userdetailslist.has(email.getText().toString())) {
                        JSONObject UserDetails = Userdetailslist.getJSONObject(email.getText().toString());
                        String passsaved = UserDetails.getString("Password");
                        String passedittext = password.getText().toString();
                        if (passsaved.equals(passedittext)) {
                            session.addLoginStatus(email.getText().toString(),true);
                            Intent Home = new Intent(loginactivity.this, HomeMain.class);
                            startActivity(Home);
                            finish();
                        } else {
                            Toast.makeText(loginactivity.this, "Password Wrong?", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(loginactivity.this, "User not exist , register and try again.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(loginactivity.this, Form.class);
                startActivity(register);
                finish();
            }
        });


        // on back pressed listener to prevent logout issue


    }
    
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(loginactivity.this);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMessage("Do you want to Exit?");
        builder.create();
        builder.show();

    }
}