package com.github.pengrad.mapscaleview.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.github.pengrad.mapscaleview.MapScaleView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraChangeListener {

    private GoogleMap map;
    private MapScaleView scaleView;
    private MapScaleView scaleViewMiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        scaleView = (MapScaleView) findViewById(R.id.scaleView);
        scaleViewMiles = (MapScaleView) findViewById(R.id.scaleViewMiles);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        googleMap.setOnCameraMoveListener(this);
        googleMap.setOnCameraIdleListener(this);
        googleMap.setOnCameraChangeListener(this);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(21, 105.8), 10));
    }

    @Override
    public void onCameraMove() {
        update(map.getCameraPosition());
    }

    @Override
    public void onCameraIdle() {
        update(map.getCameraPosition());
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        update(cameraPosition);
    }

    private void update(CameraPosition cameraPosition) {
        scaleView.update(cameraPosition.zoom, cameraPosition.target.latitude);
        scaleViewMiles.update(cameraPosition.zoom, cameraPosition.target.latitude);
    }

    public void changeColor(View view) {
        scaleView.setColor(Color.parseColor("#991111"));
    }

    public void changeTextSize(View view) {
        scaleView.setTextSize(18);
    }

    public void changeStrokeWidth(View view) {
        scaleView.setStrokeWidth(2);
    }

    public void changeMiles(View view) {
        scaleView.setIsMiles(true);
    }

    public void changeSize(View view) {
        ViewGroup.LayoutParams layoutParams = scaleView.getLayoutParams();
        layoutParams.width = new Random().nextBoolean() ? 400 : 200;
        scaleView.requestLayout();
    }
}
