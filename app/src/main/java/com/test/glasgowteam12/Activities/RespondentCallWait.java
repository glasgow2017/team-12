/**
 * An activity for a Respondent while he is waiting to be connected to someone
 * */


package com.test.glasgowteam12.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.test.glasgowteam12.CONSTANTS;
import com.test.glasgowteam12.NetworkSingleton;
import com.test.glasgowteam12.R;
import com.test.glasgowteam12.Respondent;
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
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respondent_call_wait);
        //TODO because of server problems feed in dummy data instead of HTTP request

        //// Beginning Dummy data


        Intent intent = new Intent();
        Respondent respondent = (Respondent) getIntent().getSerializableExtra("respondent");
        email = respondent.getEmail();

        // initialize sinch client, but do not start it

        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(email)
                .applicationKey(CONSTANTS.SnichKey)
                .applicationSecret(CONSTANTS.SnichSecret)
                .environmentHost("clientapi.sinch.com")
                .build();

        sinchClient.setSupportCalling(true);


        final GraphView graph = (GraphView) findViewById(R.id.dashboard_graph);

        final LinearLayout userStuff = (LinearLayout)findViewById(R.id.userInfo);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(1, 5),
                new DataPoint(2, 2),
                new DataPoint(3, 8),
                new DataPoint(4, 3),
                new DataPoint(5, 2),
                new DataPoint(6, 1),
        });

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(6);
        graph.addSeries(series);





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


                    sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener(RespondentCallWait.this, "respondent"));

                    // tell database, that this person is now online
                    if(isChecked){
                        showToast("Online");
                        userStuff.setVisibility(View.VISIBLE);
                    }else{
                        showToast("Offline");
                        userStuff.setVisibility(View.GONE);
                    }


                    // changePersonStatusOnline(email, true);


                } else{
                    // hide user info
                    userStuff.setVisibility(View.GONE);
                    // the person logged out, hiding all unnecessary info
                    if(sinchClient.isStarted()){
                        // if sinch client was started - destroy it


                        sinchClient.stopListeningOnActiveConnection();
                        sinchClient.terminate();

                    }

                    // changePersonStatusOnline(email, false);
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
