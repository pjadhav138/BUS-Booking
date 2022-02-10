package com.example.practiceapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class googlemap extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;

    SharedPreferences preferences ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlemap);

        preferences = getSharedPreferences("MyApp",MODE_PRIVATE);
        editor = preferences.edit();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this);
    }


        /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        LatLng Maharastra = new LatLng(19.151801, 72.950958);//19.151801, 72.950958
//        mMap.addMarker(new MarkerOptions().position(Maharastra).title("Mumbai"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Maharastra));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Maharastra,20));
//        mMap.setMaxZoomPreference(10);



        String Currentloc_Lat= preferences.getString("Lat","19°03'41.2");
        String Currentloc_Long= preferences.getString("Long","72°52'39.9"); //19°03'41.2"N 72°52'39.9 mumbai

        double Lat = Double.parseDouble(Currentloc_Lat);
        double Long = Double.parseDouble(Currentloc_Long);


        LatLng Currentlocation =  new LatLng(Lat,Long);
        mMap.addMarker(new MarkerOptions().position(Currentlocation).title("Bhandup"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Currentlocation,20));



    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        location.getLatitude();
        location.getLongitude();

        editor.putString("Lat", String.valueOf(location.getLatitude()));
        editor.putString("Long", String.valueOf(location.getLongitude()));
        editor.commit();



    }
}