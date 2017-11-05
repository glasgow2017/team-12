package com.test.glasgowteam12.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.test.glasgowteam12.R;
import com.test.glasgowteam12.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class DialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);

        Intent intent = new Intent();
        final User user = (User) getIntent().getSerializableExtra("user");
        String email = user.getEmail();

        CircleImageView image = (CircleImageView)findViewById(R.id.profile_image1);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DialActivity.this, UserCallActivity.class);
                intent.putExtra("user", user);

                startActivity(intent);
            }
        });


    }
}
