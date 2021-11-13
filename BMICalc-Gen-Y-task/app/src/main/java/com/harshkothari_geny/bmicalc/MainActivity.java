package com.harshkothari_geny.bmicalc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText weight, heightFeet, heightIn;
    private TextView calcBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the variables
        weight = findViewById(R.id.weight);
        heightFeet = findViewById(R.id.height_f);
        heightIn = findViewById(R.id.height_in);
        calcBtn = findViewById(R.id.calcBtn);


        calcBtn.setOnClickListener(v -> {
            if (!weight.getText().toString().isEmpty()) {
                if (!heightFeet.getText().toString().isEmpty()) {
                    if (!heightIn.getText().toString().isEmpty()) {

                        //Converting strings to int
                        int heightInF = Integer.parseInt(heightFeet.getText().toString());
                        int heightInInches = Integer.parseInt(heightIn.getText().toString());
                        int wgt = Integer.parseInt(weight.getText().toString());

                        //convering the feet and inches to meter
                        float height = (float) ((heightInF * 0.3048) + (heightInInches * 0.0254));

                        float bmi_index = wgt / (height * height);
                        Intent i = new Intent(this, BMIActivity.class);
                        i.putExtra("BMI", bmi_index);
                        startActivity(i);
                    } else heightIn.setError("provide inches");
                } else heightFeet.setError("Fill this feild");
            } else weight.setError("Fill your weight");
        });
    }
}