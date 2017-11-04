package com.test.glasgowteam12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open another activity for testing purposes
        /*
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);*/
    }

}
