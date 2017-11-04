package com.test.glasgowteam12.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.test.glasgowteam12.Dashboard;
import com.test.glasgowteam12.R;

public class HomeScreenActivity extends AppCompatActivity {

    TextView text_seekbar1;
    int currentValue;
    SeekBar seekbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        text_seekbar1 = (TextView) findViewById(R.id.text_seekBar1);
        seekbar1 = (SeekBar) findViewById(R.id.seekBar1);

        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
            Intent intent = new Intent(HomeScreenActivity.this, Dashboard.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }





}
