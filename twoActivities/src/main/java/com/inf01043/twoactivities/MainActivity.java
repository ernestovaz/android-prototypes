package com.inf01043.twoactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button activityButton;
    EditText text;

    public static final String EXTRA_MESSAGE = "com.inf01043.twoactivities.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityButton = findViewById(R.id.button);
        text = findViewById(R.id.editText);

        activityButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(text.getText()))
                    text.setHint("Text cannot be empty!");
                else  {
                    Intent intent = new Intent(v.getContext(), SecondaryActivity.class);
                    String message = text.getText().toString();
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                }

            }
        });
    }
}