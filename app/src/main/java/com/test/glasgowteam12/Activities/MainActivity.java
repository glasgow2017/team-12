package com.test.glasgowteam12.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.test.glasgowteam12.R;

public class
MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open another activity for testing purposes

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
