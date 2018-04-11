package com.innprojects.gymapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.innprojects.gymapp.R;

public class HeightSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height_selector);

        Button cancelheight;
        Button confirmheight;

        final TextView cm;
        final TextView heightcmview;
        final TextView ft;
        final TextView heightftview;
        final TextView heightinview;

        final NumberPicker picker1;
        final NumberPicker picker2;
        final NumberPicker picker3;
        final NumberPicker picker4;

        picker1 = (NumberPicker) findViewById(R.id.number_picker1);
        picker2 = (NumberPicker) findViewById(R.id.number_picker2);
        picker3 = (NumberPicker) findViewById(R.id.number_picker3);
        picker4 = (NumberPicker) findViewById(R.id.number_picker4);

        cm = (TextView) findViewById(R.id.cm);
        heightcmview = (TextView) findViewById(R.id.heightcmview);
        ft = (TextView) findViewById(R.id.ft);
        heightftview = (TextView) findViewById(R.id.heightftview);
        heightinview = (TextView) findViewById(R.id.heightinview);

        confirmheight = (Button) findViewById(R.id.confirmheight);
        cancelheight = (Button) findViewById(R.id.cancelheight);

        cm.setVisibility(View.GONE);
        heightcmview.setVisibility(View.GONE);

        final String[] data1 = new String[]{"4'", "5'", "6'", "7'"};
        picker1.setMinValue(0);
        picker1.setMaxValue(data1.length - 1);
        picker1.setDisplayedValues(data1);
        picker1.setWrapSelectorWheel(false);

        heightftview.setText(data1[0] + " ");

        final String[] data2 = new String[]{"0\"", "1\"", "2\"", "3\"", "4\"", "5\"", "6\"", "7\"", "8\"", "9\"", "10\"", "11\""};
        picker2.setMinValue(0);
        picker2.setMaxValue(data2.length - 1);
        picker2.setDisplayedValues(data2);
        picker2.setWrapSelectorWheel(false);

        heightinview.setText(data2[0] + " ");

        final String[] data3 = new String[]{"ft", "cms"};

        picker3.setMinValue(0);
        picker3.setMaxValue(data3.length - 1);
        picker3.setDisplayedValues(data3);
        picker3.setWrapSelectorWheel(false);

        heightcmview.setText("100");

        final String[] data4 = new String[]{"100", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110",
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
                "211", "212", "213", "214", "215", "216", "217", "218", "219", "220",
                "221", "222", "223", "224", "225", "226"};

        picker4.setMinValue(0);
        picker4.setMaxValue(data4.length - 1);
        picker4.setDisplayedValues(data4);
        picker4.setWrapSelectorWheel(false);
        picker4.setVisibility(View.GONE);

        picker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected value from picker
                if (data3[newVal].equals("ft")) {

                    picker4.setVisibility(View.GONE);
                    picker1.setVisibility(View.VISIBLE);
                    picker2.setVisibility(View.VISIBLE);

                    cm.setVisibility(View.GONE);
                    heightcmview.setVisibility(View.GONE);
                    ft.setVisibility(View.VISIBLE);
                    heightftview.setVisibility(View.VISIBLE);
                    heightinview.setVisibility(View.VISIBLE);

                } else if (data3[newVal].equals("cms")) {

                    picker4.setVisibility(View.VISIBLE);
                    picker1.setVisibility(View.GONE);
                    picker2.setVisibility(View.GONE);

                    cm.setVisibility(View.VISIBLE);
                    heightcmview.setVisibility(View.VISIBLE);
                    ft.setVisibility(View.GONE);
                    heightftview.setVisibility(View.GONE);
                    heightinview.setVisibility(View.GONE);
                }
            }
        });

        picker4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected value from picker
                heightcmview.setText(data4[newVal]);
            }
        });

        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected value from picker
                heightftview.setText(data1[newVal]+ " ");
            }
        });

        picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Display the newly selected value from picker
                heightinview.setText(data2[newVal]+ " ");
            }
        });

        confirmheight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(picker3.getValue() == 0) {
                    String ft = heightftview.getText().toString();
                    ft = ft.replaceAll("' ","");
                    String in = heightinview.getText().toString();
                    in = in.replaceAll("\" ", "");
                    int ftint = Integer.parseInt(ft);
                    int inint = Integer.parseInt(in);
                    double final_value = ((ftint * 12 ) + inint) * 2.54;
                    intent.putExtra("heightvalue", String.valueOf((int)final_value));
                } else if (picker3.getValue() == 1)
                    intent.putExtra("heightvalue", heightcmview.getText());
                intent.putExtra("heightflag", "true");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancelheight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("heightflag", "false");
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
