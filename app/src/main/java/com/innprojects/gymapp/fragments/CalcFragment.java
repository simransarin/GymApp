package com.innprojects.gymapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.innprojects.gymapp.R;
import com.innprojects.gymapp.activities.MainActivity;
import com.innprojects.gymapp.javaModels.Stat;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalcFragment extends Fragment {

    ArrayList<Stat> given;
    TextView BMIvaluetext, musclevaluetext, watervaluetext, proteinsvaluetext, visceralfatvaluetext, boneMassvaluetext, BMRvaluetext, bodyWeight, Impedance, fitnessAgetext;

    private PieChart mChart;

    public CalcFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calc, container, false);
        BMIvaluetext = (TextView) view.findViewById(R.id.BMI);
        musclevaluetext = (TextView) view.findViewById(R.id.muscle);
        watervaluetext = (TextView) view.findViewById(R.id.water);
        proteinsvaluetext = (TextView) view.findViewById(R.id.proteins);
        visceralfatvaluetext = (TextView) view.findViewById(R.id.visceralFat);
        boneMassvaluetext = (TextView) view.findViewById(R.id.boneMass);
        BMRvaluetext = (TextView) view.findViewById(R.id.BMR);
        bodyWeight = (TextView) view.findViewById(R.id.bodyWeight);
        Impedance = (TextView) view.findViewById(R.id.impedance);
        fitnessAgetext = (TextView) view.findViewById(R.id.fitnessAge);

        given = ((MainActivity) getActivity()).statsgiven;
        if (!given.isEmpty()) {
            int j = 0;
            for (Stat s : given) {
                j++;
            }


            if (!given.get(j - 1).getWeight().equals("null"))
                bodyWeight.setText(String.format(Locale.US, "%.2f", Float.parseFloat(given.get(j - 1).getWeight())));
            if (!given.get(j - 1).getImpedance().equals("null"))
                Impedance.setText(String.format(Locale.US, "%.2f", Float.parseFloat(given.get(j - 1).getImpedance())));
            if (!given.get(j - 1).getBMI().equals("null"))
                BMIvaluetext.setText(String.format(Locale.US, "%.2f", Float.parseFloat(given.get(j - 1).getBMI())));
            if (!given.get(j - 1).getMuscle().equals("null"))
                musclevaluetext.setText(String.format(Locale.US, "%.2f", Float.parseFloat(given.get(j - 1).getMuscle())));
            if (!given.get(j - 1).getWater().equals("null"))
                watervaluetext.setText(String.format(Locale.US, "%.2f", Float.parseFloat(given.get(j - 1).getWater())));
            if (!given.get(j - 1).getProtein().equals("null"))
                proteinsvaluetext.setText(String.format(Locale.US, "%.2f", Float.parseFloat(given.get(j - 1).getProtein())));
            if (!given.get(j - 1).getVisceral_fat().equals("null"))
                visceralfatvaluetext.setText(String.format(Locale.US, "%.2f", Float.parseFloat(given.get(j - 1).getVisceral_fat())));
            if (!given.get(j - 1).getBone_mass().equals("null"))
                boneMassvaluetext.setText(String.format(Locale.US, "%.2f", Float.parseFloat(given.get(j - 1).getBone_mass())));
            if (!given.get(j - 1).getBMR().equals("null"))
                BMRvaluetext.setText(String.format(Locale.US, "%.2f", Float.parseFloat(given.get(j - 1).getBMR())));
            if (!given.get(j - 1).getFitness_age().equals("null"))
                fitnessAgetext.setText(given.get(j - 1).getFitness_age());

            mChart = (PieChart) view.findViewById(R.id.chart1);
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
            values.add(new PieEntry(Float.parseFloat(given.get(j - 1).getBody_fat())));
            values.add(new PieEntry(100 - Float.parseFloat(given.get(j - 1).getBody_fat())));

            PieDataSet dataSet = new PieDataSet(values, "Election Results");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);

            dataSet.setColors(new int[]{getResources().getColor(R.color.gradientlightred), getResources().getColor(R.color.amber)});

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
        return view;
    }
}
