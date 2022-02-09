package com.example.practiceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Form extends AppCompatActivity {
    AutoCompleteTextView country;
    EditText name, email, password, confirmpassword;
    Button button, login_btn;//show
    RadioGroup Group;
    CheckBox checkBox;
    String gender = "";
    String[] array = {"India", "USA", "Russia", "China", "Japan", "Shri Lanka"};
    JSONObject genderObj;
    JSONObject Userdetails;
    boolean terms = false;
    SessionManage session;
    String TAG = getClass().getSimpleName();
//    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        session = new SessionManage(Form.this);
        if (session.getLoginStatus()) {
            startActivity(new Intent(Form.this, HomeMain.class));
            finish();
        }
        genderObj = new JSONObject();
        Userdetails = new JSONObject();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
//        show = findViewById(R.id.show);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        country = findViewById(R.id.country);
        button = findViewById(R.id.submit);
        login_btn = findViewById(R.id.loginbtn);
        checkBox = findViewById(R.id.checkbox);



/*
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//        ******
//                DatePickerDialog datePickerDialog = new DatePickerDialog(Form.this);
//                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        int temp = month+1;
//                        Log.e("TAG", "onDateSet: " );
//                        show.setText(year+"-"+(temp)+"-"+dayOfMonth);
//                    }
//                });
//                Calendar calendar = Calendar.getInstance();
//
//                TimePickerDialog timePickerDialog = new TimePickerDialog(Form.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        Log.e("TAG", "onTimeSet: "+hourOfDay+"-"+minute );
//                    }
//                },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);
//                timePickerDialog.show();
// ******


//                datePickerDialog.show();
            }
        });
*/
//        progressbar = findViewById(R.id.progressbar);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                terms = isChecked;
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Form.this, android.R.layout.simple_list_item_1, array);
        country.setAdapter(adapter);


        Group = findViewById(R.id.gender);
        Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {

                    if (checkedId == R.id.male) {
                        gender = "Male";
                        genderObj.put("Gender", "Male");

                    } else if (checkedId == R.id.female) {
                        gender = "Female";
                        genderObj.put("Gender", "Female");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(Form.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(Form.this, "Please Provide Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (country.getText().toString().isEmpty()) {
                    Toast.makeText(Form.this, "Please Enter Country Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (gender.isEmpty()) {
                    Toast.makeText(Form.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!terms) {
                    Toast.makeText(Form.this, "Please Agree To Terms and Conditions !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().equals(confirmpassword.getText().toString())) {


                  /* progressbar.setVisibility(View.VISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Form.this);
                    builder.setMessage("message");
//                    builder.setIcon(R.drawable.ic_launcher_background);
                    builder.setTitle("title");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    try {
                        Thread.sleep(5000);
                        dialog.dismiss();
//                        progressbar.setVisibility(View.GONE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/

                    try {
                        Userdetails.put("Name", name.getText().toString());
                        Userdetails.put("Email", email.getText().toString());
                        Userdetails.put("Country", country.getText().toString());
                        Userdetails.put("Password", password.getText().toString());
                        Userdetails.put("Terms and condition :", terms);

                        SharedPreferences preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        String registerdat = preferences.getString("UserDetails", "{}");
                        JSONObject jsRegister = new JSONObject(registerdat);
                        jsRegister.put(email.getText().toString(), Userdetails);
                        editor.putString("UserDetails", jsRegister.toString());
                        editor.putBoolean("Is_login", true);
                        editor.putString("User_Id", email.getText().toString());
                        editor.commit();
                        String s = preferences.getString("Userdetails", "{}");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e(TAG, "USERDETAILS: " + Userdetails.toString());
                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://swift-ride-22040-default-rtdb.firebaseio.com/");
                    DatabaseReference Login = database.getReference("User");

                    User userdetails = null;

                    Map<String, Object> map = new HashMap<>();
                    Map<String, Object> map1 = new HashMap<>();
                    Iterator<String> iterator = Userdetails.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        try {
                            map.put(key, Userdetails.get(key));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG, "onClick: " + map);
                    }
                    try {
                        map1.put(Userdetails.getString("Name"), map);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Login.updateChildren(map1);

                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e(TAG, "onDataChange: " + dataSnapshot.child("order"));


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            Log.e(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    };
                    Login.addValueEventListener(postListener);


                    Intent gotohome = new Intent(Form.this, Home.class);
                    startActivity(gotohome);
                    finish();
                } else {
                    Toast.makeText(Form.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                }


            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotologin = new Intent(Form.this, loginactivity.class);
                startActivity(gotologin);
                finish();

            }
        });
    }

}