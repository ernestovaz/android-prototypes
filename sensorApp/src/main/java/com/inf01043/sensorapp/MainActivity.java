package com.inf01043.sensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView xText;
    private TextView yText;
    private TextView zText;
    Float xValue;
    Float yValue;
    Float zValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        xText = findViewById(R.id.textViewX);
        yText = findViewById(R.id.textViewY);
        zText = findViewById(R.id.textViewZ);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                if((xValue != null && Math.abs(xValue - sensorEvent.values[0]) > 10)
                    ||(yValue != null && Math.abs(yValue - sensorEvent.values[0]) > 10)
                    ||(zValue != null && Math.abs(zValue - sensorEvent.values[0]) > 10)){
                    Intent intent = new Intent(this, SecondaryActivity.class);
                    startActivity(intent);
                }
                else{
                    xValue = sensorEvent.values[0];
                    yValue = sensorEvent.values[1];
                    zValue = sensorEvent.values[2];
                    xText.setText("X:"+ xValue);
                    yText.setText("Y:"+ yValue);
                    zText.setText("Z:"+ zValue);
                }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}