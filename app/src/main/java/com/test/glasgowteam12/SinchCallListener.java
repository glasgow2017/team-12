package com.test.glasgowteam12;

import android.content.Context;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

/**
 * Created by Daumantas on 2017-11-05.
 */

public class SinchCallListener implements CallListener {

    Context context;
    String user;
    public SinchCallListener(Context context, String user) {
        this.context = context;
        this.user = user;
    }

    @Override
    public void onCallEnded(Call endedCall) {
        //call ended by either party

        // check which person is running this instance and do appropriate actions

        if(user.equals("respondent")){
            // TODO: open feedback
        } else {
            // TODO: open rate it
        }
    }
    @Override
    public void onCallEstablished(Call establishedCall) {
        if(user.equals("respondent")){
            // TODO: show info about user
        }else{
            //TODO: show info about respondent
        }
    }
    @Override
    public void onCallProgressing(Call progressingCall) {
        //call is ringing
    }
    @Override
    public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
        //don't worry about this right now
    }
}
