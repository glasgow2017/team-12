package com.test.glasgowteam12.Activities;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.test.glasgowteam12.R;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dashboard extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> arrayList = new ArrayList<>();

    private ArrayAdapter<String> adapter;
    int seekbarValue1;
    String currentTimestamp;
    int timeInt;
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();

        if (bundleExtras != null){
            seekbarValue1 = bundleExtras.getInt("seekBarValue1");
            currentTimestamp = bundleExtras.getString("currentTimestamp");
            System.out.println("dashboard" + seekbarValue1 + "   " + currentTimestamp);
        }

        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);

        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();



        /* Initialise graph of user mood points */
        /* TODO:  pull in external data for moods associated with user */
        GraphView graph = (GraphView) findViewById(R.id.dashboard_graph);
         series= new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, 4),
                new DataPoint(d2, 5),
                new DataPoint(d3, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
        });
        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3);

        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setHumanRounding(false);

        /*Add values to list view */
        list = (ListView) findViewById(R.id.dashboard_log_list);


        // this line adds the data of your EditText and puts in your array

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        arrayList.add("Mood:5");
        arrayList.add("Mood:8");
        arrayList.add("Mood:2");
        list.setAdapter(adapter);


        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
