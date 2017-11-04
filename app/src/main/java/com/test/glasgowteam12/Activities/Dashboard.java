package com.test.glasgowteam12.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.test.glasgowteam12.R;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



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
        arrayList = new ArrayList<String>();
        //adapter = new ArrayAdapter<String>(getApplicationContext(), , arrayList);// this line adds the data of your EditText and puts in your array
        arrayList.add("Mood:5");
        arrayList.add("Mood:8");
        arrayList.add("Mood:2");
        //adapter.notifyDataSetChanged();

    }

}
