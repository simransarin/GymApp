package com.innprojects.gymapp.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.innprojects.gymapp.R;
import java.util.ArrayList;

public class WeightActivity extends AppCompatActivity {

    private double weight, height;
    private int sex, age, flagM = 0, flagF = 0;

    private double BMIvalue, proteinsvalue, boneMassvalue, BMRvalue;
    private TextView BMIvaluetext,proteinsvaluetext, boneMassvaluetext, BMRvaluetext, bodyWeight;

    private PieChart mChart;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        setTitle("Stats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(WeightActivity.this);

        BMIvaluetext = (TextView) findViewById(R.id.BMIWeight);
        proteinsvaluetext = (TextView) findViewById(R.id.proteinsWeight);
        boneMassvaluetext = (TextView) findViewById(R.id.boneMassWeight);
        BMRvaluetext = (TextView) findViewById(R.id.BMRWeight);
        bodyWeight = (TextView) findViewById(R.id.bodyWeightWeight);

        weight = Integer.parseInt(mPreferences.getString("weight", ""));
        bodyWeight.setText(mPreferences.getString("weight", ""));
        height = Integer.parseInt(mPreferences.getString("height", ""));
        String gender = mPreferences.getString("GEnder", "");
        if (gender.equals("female"))
            sex = 0;
        else if (gender.equals("male"))
            sex = 1;
        age = Integer.parseInt(mPreferences.getString("age", ""));

        if (sex == 1) {
            flagM = 1;
            flagF = 0;
        } else {
            flagM = 0;
            flagF = 1;
        }
        BMIvalue = weight / (height / 100 * height / 100);
        BMRvalue = (10 * weight) + (6.25 * height) - (5 * age) + 5 * flagM - 161 * flagF;

        proteinsvalue = weight + 1.5;
        boneMassvalue = weight * 0.15;

        BMIvaluetext.setText(String.valueOf((int) BMIvalue));
        proteinsvaluetext.setText(String.valueOf((int) proteinsvalue));
        boneMassvaluetext.setText(String.valueOf((int) boneMassvalue));
        BMRvaluetext.setText(String.valueOf((int) BMRvalue));

        Log.e("values", BMIvalue + " "+ proteinsvalue + " " + boneMassvalue + " " + BMRvalue);

        mChart = (PieChart) findViewById(R.id.chartWeight);
        mChart.setBackgroundColor(Color.WHITE);

        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(38f);
        mChart.setTransparentCircleRadius(51f);
        mChart.setBackgroundColor(Color.TRANSPARENT);

        mChart.setDrawCenterText(true);

        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);

        mChart.setCenterTextOffset(0, -20);

        ArrayList<PieEntry> values = new ArrayList<PieEntry>();
        values.add(new PieEntry(0));
        values.add(new PieEntry(100 - 0));

        PieDataSet dataSet = new PieDataSet(values, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(new int[] {getResources().getColor(R.color.gradientlightred), getResources().getColor(R.color.amber)});

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        mChart.invalidate();
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
