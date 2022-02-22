package com.inf01043.sensorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener{

    private LocationManager locationManager;
    private SensorManager sensorManager;

    private Sensor accelerometer;
    private Sensor lightSensor;
    private Sensor proximitySensor;

    private TextView lightText;
    private TextView proximityText;
    private TextView xText;
    private TextView yText;
    private TextView zText;

    private TextView latitudeText;
    private TextView longitudeText;

    Float xValue;
    Float yValue;
    Float zValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        lightText       = findViewById(R.id.textViewLight);
        proximityText   = findViewById(R.id.textViewProximity);
        xText           = findViewById(R.id.textViewX);
        yText           = findViewById(R.id.textViewY);
        zText           = findViewById(R.id.textViewZ);
        latitudeText    = findViewById(R.id.textViewLat);
        longitudeText    = findViewById(R.id.textViewLong);

        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this, permissions, 0);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10, this);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            onLocationChanged(location);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        xValue = null;
        yValue = null;
        zValue = null;
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch(sensorEvent.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                if((xValue != null && Math.abs(xValue - sensorEvent.values[0]) > 20)
                        ||(yValue != null && Math.abs(yValue - sensorEvent.values[0]) > 30)
                        ||(zValue != null && Math.abs(zValue - sensorEvent.values[0]) > 30)){
                    Intent intent = new Intent(this, SecondaryActivity.class);
                    startActivity(intent);
                }
                else{
                    xValue = sensorEvent.values[0];
                    yValue = sensorEvent.values[1];
                    zValue = sensorEvent.values[2];
                    xText.setText("X Acceleration: "+ xValue);
                    yText.setText("Y Acceleration: "+ yValue);
                    zText.setText("Z Acceleration: "+ zValue);
                }
                break;
            case Sensor.TYPE_LIGHT:
                lightText.setText("Light Sensor: " + sensorEvent.values[0]);
                break;
            case Sensor.TYPE_PROXIMITY:
                proximityText.setText("Proximity Sensor: " + sensorEvent.values[0]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLocationChanged(Location location) {
        latitudeText.setText("Latitude: " + location.getLatitude());
        longitudeText.setText("Latitude: " + location.getLongitude());
    }
}