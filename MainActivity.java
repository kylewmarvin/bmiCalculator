package com.udemy.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private RadioButton femaleButton;
    private RadioButton maleButton;
    private EditText ageEditText;
    private EditText feetEditText;
    private EditText inchesEditText;
    private EditText weightEditText;
    private Button calculateButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setupButtonClickListener();
    }

    private void findViews() {
        //xml elements by id
        femaleButton = findViewById(R.id.radio_button_female);
        maleButton = findViewById(R.id.radio_button_male);
        ageEditText = findViewById(R.id.edit_text_age);
        feetEditText = findViewById(R.id.edit_text_feet);
        inchesEditText = findViewById(R.id.edit_text_inches);
        weightEditText = findViewById(R.id.edit_text_weight);
        calculateButton = findViewById(R.id.button_calculate);
        resultText = findViewById(R.id.text_view_result);
    }

    private void setupButtonClickListener() {
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                double bmi = calculateBMI();

                String ageText = ageEditText.getText().toString();
                int age = Integer.parseInt(ageText);

                if (age >= 18) {
                    displayResult(bmi);
                } else {
                    displayGuidance(bmi);
                }

            }
        });
    }

    private double calculateBMI() {
        String feetText = feetEditText.getText().toString();
        String inchesText = inchesEditText.getText().toString();
        String weightText = weightEditText.getText().toString();

        int feet = Integer.parseInt(feetText);
        int inches = Integer.parseInt(inchesText);
        int weight = Integer.parseInt(weightText);

        int totalInches = (feet * 12) + inches;
        double heightInMeters = totalInches * 0.0254;
        return weight / (heightInMeters * heightInMeters);
    }

    private void displayResult(double bmi) {
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String bmiResult = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (bmi < 18.5) {
            //Display underweight
            fullResultString = bmiResult + " - you are underweight";
        } else if (bmi > 25) {
            //Display overweight
            fullResultString = bmiResult + " - you are overweight";
        } else {
            //Display healthy
            fullResultString = bmiResult + " - you are a healthy weight";
        }
        resultText.setText(fullResultString);
    }

    private void displayGuidance(double bmi) {
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String bmiResult = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (maleButton.isChecked()) {
            fullResultString = bmiResult + " - as you are under 18, consult with your doctor for the healthy range for boys.";
        } else if (femaleButton.isChecked()){
            fullResultString = bmiResult + " - as you are under 18, consult with your doctor for the healthy range for girls.";
        } else {
            fullResultString = bmiResult + " - as you are under 18, consult with your doctor for the healthy range.";
        }
        resultText.setText(fullResultString);
    }
}