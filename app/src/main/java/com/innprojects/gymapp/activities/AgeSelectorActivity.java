package com.innprojects.gymapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.innprojects.gymapp.R;

public class AgeSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_selector);

        NumberPicker picker;
        final TextView ageview;

        Button confirmage;
        Button cancelage;

        picker = (NumberPicker) findViewById(R.id.age_picker);
        ageview = (TextView) findViewById(R.id.ageview);
        ageview.setText("1");

        confirmage = (Button) findViewById(R.id.confirmage);
        cancelage = (Button) findViewById(R.id.cancelage);

        final String[] data1 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
                "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
                "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
                "71", "72", "73", "74", "75", "76", "77", "78", "79", "80",
                "81", "82", "83", "84", "85", "86", "87", "88", "89", "90",
                "91", "92", "93", "94", "95", "96", "97", "98", "99", "100"};
        picker.setMinValue(0);
        picker.setMaxValue(data1.length - 1);
        picker.setDisplayedValues(data1);
        picker.setWrapSelectorWheel(false);

        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected value from picker
                ageview.setText(data1[newVal]);
            }
        });

        cancelage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ageflag", "false");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        confirmage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("agevalue", ageview.getText());
                intent.putExtra("ageflag", "true");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        // Leave empty if you want nothing to happen on back press.
    }

}

