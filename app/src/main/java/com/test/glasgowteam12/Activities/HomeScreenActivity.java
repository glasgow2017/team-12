package com.test.glasgowteam12.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

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



}
