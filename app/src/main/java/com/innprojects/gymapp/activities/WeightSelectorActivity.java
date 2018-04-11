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
import com.innprojects.gymapp.R;

public class WeightSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_selector);

        NumberPicker picker;
        final TextView weightview;

        Button confirmweight;
        Button cancelweight;

        picker = (NumberPicker) findViewById(R.id.weight_picker);
        weightview = (TextView) findViewById(R.id.weightview);

        confirmweight = (Button) findViewById(R.id.confirmweight);
        cancelweight = (Button) findViewById(R.id.cancelweight);

        weightview.setText("21");

        final String[] data1 = new String[]{"21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
                "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
                "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
                "71", "72", "73", "74", "75", "76", "77", "78", "79", "80",
                "81", "82", "83", "84", "85", "86", "87", "88", "89", "90",
                "91", "92", "93", "94", "95", "96", "97", "98", "99", "100",
                "101", "102", "103", "104", "105", "106", "107", "108", "109", "110",
                "111", "112", "113", "114", "115", "116", "117", "118", "119", "120",
                "121", "122", "123", "124", "125", "126", "127", "128", "129", "130",
                "131", "132", "133", "134", "135", "136", "137", "138", "139", "140",
                "141", "142", "143", "144", "145", "146", "147", "148", "149", "150",
                "151", "152", "153", "154", "155", "156", "157", "158", "159", "160",
                "161", "162", "163", "164", "165", "166", "167", "168", "169", "170",
                "171", "172", "173", "174", "175", "176", "177", "178", "179", "180",
                "181", "182", "183", "184", "185", "186", "187", "188", "189", "190",
                "191", "192", "193", "194", "195", "196", "197", "198", "199", "200",
                "201", "202", "203", "204", "205", "206", "207", "208", "209", "210",
                "211", "212", "213", "214", "215", "216", "217", "218", "219", "220"};
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
                intent.putExtra("weightFlag", "false");
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
                editor.putString("weight", weightview.getText().toString());
                editor.apply();
                intent.putExtra("weightFlag", "true");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed()
    {
        // Your Code Here. Leave empty if you want nothing to happen on back press.
    }

}
