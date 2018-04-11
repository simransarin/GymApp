package com.innprojects.gymapp.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.innprojects.gymapp.DemoBase;
import com.innprojects.gymapp.MyXAxisValueFormatter;
import com.innprojects.gymapp.R;
import com.innprojects.gymapp.activities.MainActivity;
import com.innprojects.gymapp.activities.TargetWeightSelectorActivity;
import com.innprojects.gymapp.activities.WeightActivity;
import com.innprojects.gymapp.activities.WeightSelectorActivity;
import com.innprojects.gymapp.javaModels.Stat;

import java.util.ArrayList;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends DemoBase implements OnChartValueSelectedListener{

    ArrayList<Stat> given;
    Stat s1;
    SharedPreferences mPreferences;
    String weightFlag= "false", target_weightFlag = "false";
    protected BarChart mChart;
    protected RectF mOnValueSelectedRectF = new RectF();
    int progresscircle = 0;
    double pStatus = 0;

    TextView tv;
    ImageView setW, setT;

    private Handler handler = new Handler();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Resources res = getResources();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        final SharedPreferences.Editor editor = mPreferences.edit();
        given = ((MainActivity) getActivity()).statsgiven;

        if(!given.isEmpty()) {
            int j = 0;
            for (Stat s : given) {
                j++;
            }

            s1 = given.get(j - 1);
            editor.putString("last_weight", s1.getWeight());
            editor.apply();


            Drawable drawable = res.getDrawable(R.drawable.progress_bar_weight);
            final ProgressBar mProgress = (ProgressBar) view.findViewById(R.id.progressBar);
            mProgress.setProgress(0);   // Main Progress
            mProgress.setSecondaryProgress(300); // Secondary Progress
            mProgress.setMax(300); // Maximum Progress
            mProgress.setProgressDrawable(drawable);

            tv = (TextView) view.findViewById(R.id.progressBarinsideText);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    while (pStatus < Double.parseDouble(s1.getWeight()) - 1) {
                        progresscircle += 5;
                        pStatus += 1;

                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                mProgress.setProgress(progresscircle);
                                tv.setText(pStatus + "");
                                if (pStatus < Double.parseDouble(s1.getWeight()) && pStatus > Double.parseDouble(s1.getWeight()) - 1)
                                    tv.setText(s1.getWeight());
                            }
                        });
                        try {
                            // Sleep for 200 milliseconds.
                            // Just to display the progress slowly
                            Thread.sleep(15); //thread will take approx 1.5 seconds to finish
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            mChart = (BarChart) view.findViewById(R.id.chart);
            mChart.setOnChartValueSelectedListener(this);

            mChart.setDrawBarShadow(false);
            mChart.setDrawValueAboveBar(true);

            mChart.getDescription().setEnabled(false);
            mChart.setScaleEnabled(false);

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            mChart.setMaxVisibleValueCount(60);

            // scaling can now only be done on x- and y-axis separately
            mChart.setPinchZoom(false);
            mChart.getAxisLeft().setEnabled(false);
            mChart.getAxisRight().setEnabled(false);
            mChart.setDrawGridBackground(false);

            mChart.getXAxis().setEnabled(true);

            String[] values = new String[]{"I", "II", "III", "IV", "V", "VI", "VII"};
            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
            xAxis.setTextColor((getResources().getColor(R.color.gradientred)));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1f); // only intervals of 1 day
            xAxis.setLabelCount(7);

            Legend l = mChart.getLegend();
            l.setEnabled(false);

            setData();
        }
        setW = (ImageView) view.findViewById(R.id.setweight);

        setW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), WeightSelectorActivity.class);
                startActivityForResult(i, 1);
            }
        });

        setT = (ImageView) view.findViewById(R.id.settarget);
        setT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), TargetWeightSelectorActivity.class);
                startActivityForResult(i, 2);
            }
        });

        return view;
    }

    private void setData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();


            int j = 0;
            for (Stat s : given) {
                yVals1.add(new BarEntry(j, Float.parseFloat(s.getWeight())));
                j++;
            }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");

            set1.setDrawIcons(false);

            set1.setColor(getResources().getColor(R.color.blueprogress));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
//            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);
            mChart.setData(data);
            mChart.animateXY(2000, 2000);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleX() + ", high: "
                        + mChart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                weightFlag = data.getStringExtra("weightFlag");
                if (weightFlag.equals("true")) {
                    Intent i = new Intent(getActivity(), WeightActivity.class);
                    getActivity().startActivity(i);
                }
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                target_weightFlag = data.getStringExtra("target_weightFlag");
                if (target_weightFlag.equals("true")) {
                    Toast.makeText(this.getContext(), "TARGET SET",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}


