/**
 * An activity for a Respondent while he is waiting to be connected to someone
 * */


package com.test.glasgowteam12.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.test.glasgowteam12.CONSTANTS;
import com.test.glasgowteam12.NetworkSingleton;
import com.test.glasgowteam12.R;
import com.test.glasgowteam12.SinchCallListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RespondentCallWait extends AppCompatActivity {

    private Call call;
    boolean online = false;
    Button hangUp;
    SinchClient sinchClient;
    final String SET_PERSON_ONLINE_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respondent_call_wait);

        Intent intent = new Intent();
        final String email = intent.getExtras().getString("email");

        // initialize sinch client, but do not start it
        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(email)
                .applicationKey(CONSTANTS.SnichKey)
                .applicationSecret(CONSTANTS.SnichSecret)
                .environmentHost("clientapi.sinch.com")
                .build();

        sinchClient.setSupportCalling(true);

        Switch onlineSwitch = (Switch)findViewById(R.id.online);

        onlineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                online = isChecked;

                // check if respondent is online
                if(online){
                    // Start listening for calls

                    // TODO because of unknows error in the library "snitch" we are using we cant import it and so I have moved jniLibs
                    // TODO from src/main where they should be to be compiled to src/main/java and I will comment out the code that uses it

                    /*
                    sinchClient.startListeningOnActiveConnection();
                    sinchClient.start();
                    sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener(RespondentCallWait.this, "respondent"));
                    */

                    // tell database, that this person is now online
                    //TODO because of server problems feed in dummy data instead of HTTP request
                    ///Start of Dummy data

                    // end of dummy data
                    if(isChecked){
                        showToast("Online");
                    }else{
                        showToast("Offline");
                    }


                   /* changePersonStatusOnline(email, true);*/


                } else{

                    if(sinchClient.isStarted()){
                        // if sinch client was started - destroy it
                        sinchClient.stopListeningOnActiveConnection();
                        sinchClient.terminate();
                    }

                    changePersonStatusOnline(email, false);
                }

            }
        });



        hangUp = (Button)findViewById(R.id.endCall);
    }

    void changePersonStatusOnline(final String email, final boolean online){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SET_PERSON_ONLINE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // get response from the server as JSON
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");

                            // check if setting online was successful
                            if(code.equals("success")){
                                // set online succesfully
                            } else if(code.equals("failure")){
                                // going online failed, show toast
                                showToast("Going online failed");
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
                showToast("Network error has occurred, please check your connection");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<String, String>();

                params.put("email",email);
                params.put("online",String.valueOf(online));


                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                5,
                5));
        NetworkSingleton.getInstance(RespondentCallWait.this).addToRequestque(stringRequest);
    }

    void showToast(String message){
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
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
