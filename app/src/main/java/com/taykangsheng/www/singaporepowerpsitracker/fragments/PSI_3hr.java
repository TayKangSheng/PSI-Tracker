package com.taykangsheng.www.singaporepowerpsitracker.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.taykangsheng.www.singaporepowerpsitracker.R;
import com.taykangsheng.www.singaporepowerpsitracker.helpers.DataHelper;
import com.taykangsheng.www.singaporepowerpsitracker.helpers.JSONHelper;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PSI_3hr extends Fragment {
    Context mContext;
    View mView;
    DataHelper mDataHelper;
    JSONHelper mJSONHelper;

    public PSI_3hr() {

    }

    public static PSI_3hr newInstance() {
        PSI_3hr fragment = new PSI_3hr();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("3hr PSI");
        mContext = getContext();
        mDataHelper = new DataHelper(getContext());
        String chartData = mDataHelper.getPSIData();
        if (chartData == null){
            try {
                mJSONHelper = new JSONHelper(null);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        } else {
            try {
                mJSONHelper = new JSONHelper(new JSONObject(mDataHelper.getPSIData()));
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psi_3hr, container, false);
        mView = view;

        if (mJSONHelper.isDataAvailable()) {
            addDataToChart();

            int latestReadingsIndex = mJSONHelper.getNumberOfReadings() - 1;
            Date timestamp = mJSONHelper.getUpdateTimeStamp(latestReadingsIndex);
            DateFormat print_format = new SimpleDateFormat("MMM dd',' yyyy HH:mma");
            TextView date = (TextView) mView.findViewById(R.id.chart_date);
            date.setText(print_format.format(timestamp));
        }

        return view;
    }

    private void addDataToChart(){
        LineChart chart = (LineChart) mView.findViewById(R.id.chart);

        String key = "psi_three_hourly";
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        int[] colors = {Color.RED, Color.BLUE, Color.DKGRAY, Color.GREEN, Color.CYAN, Color.MAGENTA};

        String[] locations = mJSONHelper.getLocationNames();
        for (int j=0 ; j<locations.length ; j++) {
            List<Entry> entries = new ArrayList<Entry>();

            for (int i = 0; i < mJSONHelper.getNumberOfReadings(); i++) {
                Date date = mJSONHelper.getTimeStamp(i);
                DateFormat print_format = new SimpleDateFormat("H");
                int xAxis = Integer.valueOf(print_format.format(date));
                float yAxis = mJSONHelper.getReading(i, key, locations[j]);
                entries.add(new Entry(xAxis, yAxis));
            }
            LineDataSet dataSet = new LineDataSet(entries, locations[j]+" PSI 3");
            dataSet.setColor(colors[j]);
            dataSets.add(dataSet);
        }

        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new MyValueFormatter());

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);

        chart.getLegend().setWordWrapEnabled(true);
        Description chartDesc = new Description();
        chartDesc.setText("3hr PSI");
        chart.setDescription(chartDesc);

        chart.invalidate(); // refresh

    }

    class MyValueFormatter implements IAxisValueFormatter {

        private SimpleDateFormat mFormat;

        public MyValueFormatter() {
            mFormat = new SimpleDateFormat("HH a");
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            String hour = String.valueOf((int) value);
            try {
                SimpleDateFormat given_format = new SimpleDateFormat("H");
                Date date = given_format.parse(hour);
                return mFormat.format(date);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
