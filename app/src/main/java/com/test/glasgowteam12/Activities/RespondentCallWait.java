/**
 * An activity for a Respondent while he is waiting to be connected to someone
 * */


package com.test.glasgowteam12.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.test.glasgowteam12.CONSTANTS;
import com.test.glasgowteam12.R;
import com.test.glasgowteam12.SinchCallListener;


public class RespondentCallWait extends AppCompatActivity {

    private Call call;
    Button hangUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respondent_call_wait);

        // Create Client for answering
        final SinchClient sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId("current-user-id")
                .applicationKey(CONSTANTS.SnichKey)
                .applicationSecret(CONSTANTS.SnichSecret)
                .environmentHost("clientapi.sinch.com")
                .build();

        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.start();

        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener(this, "respondent"));

        hangUp = (Button)findViewById(R.id.endCall);


    }

    private class SinchCallClientListener implements CallClientListener {

        private Context context;
        private String user;

        public SinchCallClientListener(Context context, String user){
            this.context = context;
            this.user = user;
        }

        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            // answer a call and add CallListener to this call
            call = incomingCall;
            call.answer();
            call.addCallListener(new SinchCallListener(context, user));
        }
    }
}
