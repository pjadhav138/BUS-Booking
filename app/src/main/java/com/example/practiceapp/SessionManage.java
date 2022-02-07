package com.example.practiceapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionManage {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SessionManage(Context context) {
        preferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setLoginStatus(boolean status) {
        editor.putBoolean("Is_login", status);
        editor.commit();
    }

    public boolean getLoginStatus() {
        return preferences.getBoolean("Is_login", false);
    }

    public void addLoginStatus(String userid, boolean status) {
        editor.putString("User_Id", userid);
        editor.putBoolean("Is_login", status);
        editor.commit();
    }

    public void setItem(String item, int position) {
      try {
          String bookingList = preferences.getString("Bookings", "[]");
          JSONArray array = new JSONArray(bookingList);
          JSONObject object = array.getJSONObject(position);
          object.put("Source", item);
//          array.remove(position);
          array.put(position, object);
          editor.putString("Bookings", array.toString());
          editor.commit();
      } catch (JSONException e) {
          e.printStackTrace();
      }
    }

    public String getUserDetails() {
        return preferences.getString("UserDetails", "{}");
    }


}
