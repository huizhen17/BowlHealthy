package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AboutUs extends AppCompatActivity implements OnMapReadyCallback {
    MapView mvMapView;
    private GoogleMap gmap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mvMapView = findViewById(R.id.mapView);
        mvMapView.setClickable(true);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY); //get key(like ic no)
        }

        mvMapView.onCreate(mapViewBundle);// show google the key
        mvMapView.getMapAsync(this);//get the map from google
    }

    @Override
    protected void onResume() {
        mvMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mvMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mvMapView.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mvMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mvMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {//load the map in
        gmap = googleMap;

        Double latitude = 5.341604;
        Double longitude = 100.281871;
        //Display reference map
        LatLng ny = new LatLng(latitude, longitude);
        gmap.addMarker(new MarkerOptions().position(ny).title("We're here!"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(ny, 20F));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }

    public void btnOnClick_Message(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
        startActivity(intent);
    }

    public void ivPhone_onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "RESTAURANT_PHONE_NO"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);
    }

}
