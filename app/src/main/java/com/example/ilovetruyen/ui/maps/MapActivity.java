package com.example.ilovetruyen.ui.maps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.ilovetruyen.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap googleMap;
    private MaterialButton directionBtn;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng currentClickedLocation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapClickListener(this);

        // Add markers for predefined locations
        addMarkers();

        // Adjust camera to show all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(10.8714660148384, 106.79179385386033)); // Marker 1 position
        builder.include(new LatLng(10.78075096308672, 106.69977162390617)); // Marker 2 position
        builder.include(new LatLng(10.787728621060744, 106.70531017760534)); // Marker 3 position
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
    }

    private void addMarkers() {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.8714660148384, 106.79179385386033))
                .title("Đại học Nông Lâm Thành phố Hồ Chí Minh"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.78075096308672, 106.69977162390617))
                .title("Đường sách Nguyễn Văn Bình"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.787728621060744, 106.70531017760534))
                .title("Thảo Cầm Viên Sài Gòn"));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        currentClickedLocation = latLng;
        Toast.makeText(this, "Clicked on: " + latLng.latitude + ", " + latLng.longitude, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    private void navigateToDestination(LatLng destinationLatLng) {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        String source = location.getLatitude() + "," + location.getLongitude();
                        String destination = destinationLatLng.latitude + "," + destinationLatLng.longitude;

                        Uri uri = Uri.parse("https://www.google.com/maps/dir/" + source + "/" + destination);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.setPackage("com.google.android.apps.maps");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
