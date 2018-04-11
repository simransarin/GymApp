package com.innprojects.gymapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.innprojects.gymapp.DemoBase;
import com.innprojects.gymapp.R;
import com.innprojects.gymapp.activities.MainActivity;
import com.innprojects.gymapp.javaModels.Stat;
import com.innprojects.gymapp.listviewitems.BarChartItem;
import com.innprojects.gymapp.listviewitems.ChartItem;
import com.innprojects.gymapp.listviewitems.LineChartItem;
import com.innprojects.gymapp.listviewitems.PieChartItem;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends DemoBase {

    Context context;
    ArrayList<Stat> given;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_listview_chart, container, false);
        ListView lv = (ListView) view.findViewById(R.id.listView1);
        given = ((MainActivity) getActivity()).statsgiven;
        if(!given.isEmpty()) {
            ArrayList<ChartItem> list = new ArrayList<ChartItem>();

            // 6 items
            for (int i = 0; i <= 2; i++) {

                if (i % 3 == 0 && !given.get(0).getBMI().equals("null") && !given.get(0).getBMR().equals("null")) {
                    list.add(new LineChartItem(generateDataLine(), context));
                } else if (i % 3 == 1 && !given.get(0).getWater().equals("null")) {
                    list.add(new BarChartItem(generateDataBar(), context));
                } else if (i % 3 == 2 && !given.get(0).getBody_fat().equals("null") && !given.get(0).getBone_mass().equals("null") &&!given.get(0).getMuscle().equals("null")) {
                    list.add(new PieChartItem(generateDataPie(), context));
                }
            }

            ChartDataAdapter cda = new ChartDataAdapter(getContext(), list);
            lv.setAdapter(cda);
        }

        return view;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine() {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        int i = 0;
        for (Stat s : given) {
            e1.add(new Entry(i, Float.parseFloat(s.getBMI())));
            i++;
        }

        LineDataSet d1 = new LineDataSet(e1, "BMI");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setColor(getResources().getColor(R.color.gradientred));
        d1.setCircleColor(getResources().getColor(R.color.gradientred));
//        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        int j = 0;
        for (Stat s : given) {
            e2.add(new Entry(j, Float.parseFloat(s.getBMR())));
            j++;
        }

        LineDataSet d2 = new LineDataSet(e2, "BMR");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
//        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(getResources().getColor(R.color.amber));
        d2.setCircleColor(getResources().getColor(R.color.amber));
        d2.setDrawValues(false);

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(d1);
        sets.add(d2);

        LineData cd = new LineData(sets);
        return cd;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        int j = 0;
        for (Stat s : given) {
            entries.add(new BarEntry(j, Float.parseFloat(s.getWater())));
            j++;
        }

        BarDataSet d = new BarDataSet(entries, "Total Body Water");
        d.setColors(new int[]{getResources().getColor(R.color.gradientred), getResources().getColor(R.color.gradientredmiddle), getResources().getColor(R.color.gradientlightred), getResources().getColor(R.color.amber)});
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private PieData generateDataPie() {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        int j = 0;
        for (Stat s : given) {
            j++;
        }

        entries.add(new PieEntry(Float.parseFloat(given.get(j-1).getBody_fat()), "fat mass"));
        entries.add(new PieEntry(Float.parseFloat(given.get(j-1).getBone_mass()), "bone mass"));
        entries.add(new PieEntry(Float.parseFloat(given.get(j-1).getMuscle()), "muscle mass"));
        entries.add(new PieEntry(100 - (Float.parseFloat(given.get(j-1).getBody_fat())+ Float.parseFloat(given.get(j-1).getBone_mass()) +Float.parseFloat(given.get(j-1).getMuscle()) ), "mass"));

//        entries.add(new PieEntry(20, "fat mass"));
//        entries.add(new PieEntry(20, "bone mass"));
//        entries.add(new PieEntry(20, "muscle mass"));
//        entries.add(new PieEntry(40, "mass"));

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(new int[]{getResources().getColor(R.color.gradientred), getResources().getColor(R.color.amber), getResources().getColor(R.color.gradientredmiddle), getResources().getColor(R.color.gradientlightred)});

        PieData cd = new PieData(d);
        return cd;
    }

    /**
     * adapter that supports 3 different item types
     */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            return getItem(position).getItemType();
        }

        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }
}
