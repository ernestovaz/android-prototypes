package com.inf01043.androidprototypes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText numberView1;
    EditText numberView2;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberView1 = findViewById(R.id.editText1);
        numberView2 = findViewById(R.id.editText2);
        resultText  = findViewById(R.id.textResult);


        Button resultButton = findViewById(R.id.buttonResult);
        resultButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(numberView1.getText()) || TextUtils.isEmpty(numberView2.getText()))
                    resultText.setText("Numbers cannot be empty!");
                else
                    resultText.setText(String.valueOf(
                            Double.parseDouble(numberView1.getText().toString()) +
                            Double.parseDouble(numberView2.getText().toString())));

            }
        });
    }

}