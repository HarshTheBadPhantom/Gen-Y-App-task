package com.harshkothari_geny.bmicalc;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class BMIActivity extends AppCompatActivity {
    private TextView bmiTextView, bmiIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);
        bmiTextView = findViewById(R.id.bmitext);
        bmiIndicator = findViewById(R.id.bmiIndicator);
        float bmiIndex = getIntent().getFloatExtra("BMI", 0f);
        textCounter(bmiIndex, bmiTextView);

        String bmiLabel = "";
        if (Float.compare(bmiIndex, 15f) > 0 && Float.compare(bmiIndex, 18.5f) <= 0) {
            bmiLabel = getString(R.string.severely_underweight);
        } else if (Float.compare(bmiIndex, 18.5f) > 0 && Float.compare(bmiIndex, 25f) <= 0) {
            bmiLabel = getString(R.string.normal);
        } else if (Float.compare(bmiIndex, 25f) > 0 && Float.compare(bmiIndex, 30f) <= 0) {
            bmiLabel = getString(R.string.overweight);
        } else if (Float.compare(bmiIndex, 30f) > 0 && Float.compare(bmiIndex, 35f) <= 0) {
            bmiLabel = getString(R.string.obese_class_i);
        } else if (Float.compare(bmiIndex, 35f) > 0 && Float.compare(bmiIndex, 40f) <= 0) {
            bmiLabel = getString(R.string.obese_class_ii);
        } else {
            bmiLabel = getString(R.string.obese_class_iii);
        }
        bmiIndicator.setText(bmiLabel);
        bmiIndicator.setVisibility(View.VISIBLE);
    }

    // A Number counting animation method
    private void textCounter(float count, TextView textView) {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, (int) count);
        animator.addUpdateListener(animation -> textView.setText(String.valueOf(animation.getAnimatedValue())));
        animator.setEvaluator((TypeEvaluator<Integer>) (fraction, startValue, endValue) -> Math.round(startValue + (endValue - startValue) * fraction));
        animator.setDuration(1600);
        if (count >= 15f && count <= 18.5f) {
            textView.setTextColor(ContextCompat.getColor(BMIActivity.this, R.color.orange));
        } else if (count > 18.5f && count <= 25f) {
            textView.setTextColor(ContextCompat.getColor(BMIActivity.this, R.color.green));
        } else {
            textView.setTextColor(ContextCompat.getColor(BMIActivity.this, R.color.red));
        }
        animator.start();
    }
}