package com.test.glasgowteam12.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.test.glasgowteam12.R;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> arrayList = new ArrayList<>();

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();

        if (bundleExtras != null){
            int seekbarValue1 = bundleExtras.getInt("seekBarValue1");
            System.out.println("dashboard" + seekbarValue1);
        }

        /* Initialise graph of user mood points */
        /* TODO:  pull in external data for moods associated with user */
        GraphView graph = (GraphView) findViewById(R.id.dashboard_graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        /*Add values to list view */
        list = (ListView) findViewById(R.id.dashboard_log_list);


        // this line adds the data of your EditText and puts in your array

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        arrayList.add("Mood:5");
        arrayList.add("Mood:8");
        arrayList.add("Mood:2");
        list.setAdapter(adapter);
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
            System.out.println("Hello");
        }

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
