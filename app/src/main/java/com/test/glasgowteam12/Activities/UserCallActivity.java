/**
 * An Activity for user while he is talking to someone
 *
 * */

package com.test.glasgowteam12.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.test.glasgowteam12.CONSTANTS;
import com.test.glasgowteam12.R;
import com.test.glasgowteam12.SinchCallListener;

public class UserCallActivity extends AppCompatActivity {

    private Call call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_call);

        Intent intent = new Intent();
        String email = intent.getExtras().getString("email");

        // Create Client for calling
        final SinchClient sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId("email")
                .applicationKey(CONSTANTS.SnichKey)
                .applicationSecret(CONSTANTS.SnichSecret)
                .environmentHost("clientapi.sinch.com")
                .build();

        sinchClient.setSupportCalling(true);
        sinchClient.start();


        // Algorithm to find a best person's id to call

        sinchClient.getCallClient().callUser("call-recipient-id");



        // Add Hang Up button

        final Button hangUp = (Button)findViewById(R.id.endCall);
        hangUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (call == null) {
                    call = sinchClient.getCallClient().callUser("call-recipient-id");
                    call.addCallListener(new SinchCallListener(UserCallActivity.this, "user"));
                } else {
                    call.hangup();
                    call = null;
                    //TODO: go to an activity after a call
                }
            }
        });
    }
}
