package com.example.practiceapp;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsPolygon extends FragmentActivity implements OnMapReadyCallback, SeekBar.OnSeekBarChangeListener {

    private GoogleMap mMap;
    CheckBox checkbox;
    SeekBar seekred,seekblue,seekgreen;
    Button draw,clear;

    Polygon polygon = null;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerlist = new ArrayList<>();

    int red=0,green=0,blue=0;
    private String TAG=getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_polygon);


        checkbox = findViewById(R.id.checkbox_polygon);
        seekred = findViewById(R.id.seek_red);
        seekblue = findViewById(R.id.seek_blue);
        seekgreen = findViewById(R.id.seek_green);
        draw = findViewById(R.id.draw_polygon);
        clear = findViewById(R.id.Clear_polygon);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    if (polygon==null) return;
                    polygon.setFillColor(Color.rgb(red,green,blue));
                }
                else{
                    polygon.setFillColor(Color.TRANSPARENT);
                }
            }
        });

        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (polygon != null) polygon.remove();

                Log.e(TAG, "onClick: "+red );
                Log.e(TAG, "onClick: "+green );
                Log.e(TAG, "onClick: "+blue );
                PolygonOptions polygonOptions = new PolygonOptions().addAll(latLngList).clickable(true);
                polygon = mMap.addPolygon(polygonOptions);

                polygon.setStrokeColor(Color.rgb(10,69,96));
                if (checkbox.isChecked())
                    polygon.setFillColor(Color.rgb(255,0,0));
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (polygon != null) polygon.remove();
                for (Marker marker : markerlist)marker.remove();
                latLngList.clear();
                markerlist.clear();
                checkbox.setChecked(false);
                seekred.setProgress(0);
                seekblue.setProgress(0);
                seekgreen.setProgress(0);
            }
        });

        seekred.setOnSeekBarChangeListener(this);
        seekgreen.setOnSeekBarChangeListener(this);
        seekblue.setOnSeekBarChangeListener(this);

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

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

       mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
           @Override
           public void onMapClick(LatLng latLng) {

               MarkerOptions markerOptions = new MarkerOptions().position(latLng);
               Marker marker = mMap.addMarker(markerOptions);

               latLngList.add(latLng);
               markerlist.add(marker);


           }
       });
    }


    //seekbar
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        switch (seekBar.getId()){
            case R.id.seek_red:
                red=1;
                break;

            case R.id.seek_green:
                green=1;
                break;

            case R.id.seek_blue:
                blue=1;
                break;
        }

        if (polygon != null) {
            polygon.setStrokeColor(Color.rgb(red, green, blue));
            if (checkbox.isChecked())
                polygon.setFillColor(Color.rgb(red, green, blue));

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}