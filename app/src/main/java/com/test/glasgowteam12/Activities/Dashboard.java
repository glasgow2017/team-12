package com.test.glasgowteam12.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.test.glasgowteam12.R;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> arrayList = new ArrayList<>();
    private String FetchGraphPoints_URL = "";


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

        final GraphView graph = (GraphView) findViewById(R.id.dashboard_graph);


        //TODO because of server problems feed in dummy data instead of HTTP request

        //// Beginning Dummy data


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(20170204, 5),
                new DataPoint(20170205, 2),
                new DataPoint(20170206, 8),
                new DataPoint(20170207, 3),
                new DataPoint(20170208, 2),
                new DataPoint(201702030, 1),
        });
        graph.addSeries(series);

        ///End dummy data

        /* pull in external data for moods associated with user */

        /*
        JsonArrayRequest dataPointsRequest = new JsonArrayRequest(
                Request.Method.GET,
                FetchGraphPoints_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        try{
                            // Init DataPoints Array of required length

                            DataPoint[] dataPointsArray = new DataPoint[response.length() - 1];

                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject graphPoint = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String date = graphPoint.getString("date");
                                String mood = graphPoint.getString("mood");

                                dataPointsArray[i] = new DataPoint(Double.parseDouble(date.replace("-","")), Double.parseDouble(mood));
                            }


                            /* Initialise graph of user mood points */ /*

                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointsArray);
                            graph.addSeries(series);


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                    }
                }
        );
        NetworkSingleton.getInstance(Dashboard.this).addToRequestque(dataPointsRequest); // checks if there is a queue, if there is, puts request to it
        */

        /*Add values to list view */
        list = (ListView) findViewById(R.id.dashboard_log_list);


        // this line adds the data of your EditText and puts in your array

        // what is this..?
        /*
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
    */

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
