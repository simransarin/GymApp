package com.innprojects.gymapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.innprojects.gymapp.R;

public class TargetWeightSelectorActivity extends AppCompatActivity {

    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_target_weight_selector);
        if (mPreferences.contains("last_weight")) {

            NumberPicker picker;
            final TextView weightview;

            Button confirmweight;
            Button cancelweight;

            picker = (NumberPicker) findViewById(R.id.target_weight_picker);
            weightview = (TextView) findViewById(R.id.target_weightview);

            confirmweight = (Button) findViewById(R.id.target_confirmweight);
            cancelweight = (Button) findViewById(R.id.target_cancelweight);


            String sweight = mPreferences.getString("last_weight", null);
            Double iweight = Double.parseDouble(sweight);

            weightview.setText(Double.toString(iweight - 10));

            final String[] data1 = new String[]{
                    Double.toString(iweight - 10), Double.toString(iweight - 9), Double.toString(iweight - 8), Double.toString(iweight - 7),
                    Double.toString(iweight - 6), Double.toString(iweight - 5), Double.toString(iweight - 4), Double.toString(iweight - 3),
                    Double.toString(iweight - 2), Double.toString(iweight - 1), Double.toString(iweight + 1), Double.toString(iweight + 2),
                    Double.toString(iweight + 3), Double.toString(iweight + 4), Double.toString(iweight + 5), Double.toString(iweight + 6),
                    Double.toString(iweight + 7), Double.toString(iweight + 8), Double.toString(iweight + 9), Double.toString(iweight + 10)};
            picker.setMinValue(0);
            picker.setMaxValue(data1.length - 1);
            picker.setDisplayedValues(data1);
            picker.setWrapSelectorWheel(false);

            picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    //Display the newly selected value from picker
                    weightview.setText(data1[newVal]);
                }
            });

            cancelweight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("target_weightFlag", "false");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

            confirmweight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences mPreferences;
                    Intent intent = new Intent();
                    mPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString("target_weight", weightview.getText().toString());
                    editor.apply();
                    intent.putExtra("target_weightFlag", "true");
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "No reading for last weight found!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
    }

}
