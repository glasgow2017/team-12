package com.test.glasgowteam12.Activities;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.test.glasgowteam12.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class HomeScreenActivity extends AppCompatActivity {

    Intent intent;
    TextView text_seekbar1;
    int currentValue;
    SeekBar seekbar1;
    Button submitResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        text_seekbar1 = (TextView) findViewById(R.id.text_seekBar1);
        submitResults = (Button) findViewById(R.id.submitResults);

        seekbar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentValue = progress;
                text_seekbar1.setText(Integer.toString(currentValue) + "/10");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        intent = new Intent(HomeScreenActivity.this, Dashboard.class);

        submitResults.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm");
                String format = simpleDateFormat.format(new Date());
                Log.d("MainActivity", "Current Timestamp: " + format);

                Bundle bundle = new Bundle();
                bundle.putInt("seekBarValue1", currentValue);
                bundle.putString("currentTimestamp", format);
                intent.putExtras(bundle);
            }
        });



    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.dashboard_button) {
            // do something here
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }





}
