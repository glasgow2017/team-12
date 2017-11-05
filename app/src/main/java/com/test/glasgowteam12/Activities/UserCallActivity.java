/**
 * An Activity for user while he is talking to someone
 *
 * */

package com.test.glasgowteam12.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.test.glasgowteam12.CONSTANTS;
import com.test.glasgowteam12.NetworkSingleton;
import com.test.glasgowteam12.R;
import com.test.glasgowteam12.SinchCallListener;
import com.test.glasgowteam12.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserCallActivity extends AppCompatActivity {

    private Call call;
    User user;
    final String PARSE_ONLINE_RESPONDENTS_LIST_URL = "";
    ArrayList<User> respondents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_call);

        user = (User)getIntent().getSerializableExtra("user");

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

        // parse online helpers list


        JsonArrayRequest dataPointsRequest = new JsonArrayRequest(
                Request.Method.GET,
                PARSE_ONLINE_RESPONDENTS_LIST_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{


                            // init respondents ArrayList to store them for sorting
                            respondents = new ArrayList<>();

                            // Loop through the respondents
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject respondent = response.getJSONObject(i);
                                // Get the current student (json object) data
                                String service = respondent.getString("service");
                                String experience = respondent.getString("experience");
                                String respEmail = respondent.getString("email");
                                String name = respondent.getString("name");

                                User resp = new User(name, respEmail, experience, service);
                                respondents.add(resp);
                            }

                            // The list of respondents was downloaded, now do sorting
                            // create a copy for sorting in case we need to revert changes
                            ArrayList<User> listForSorting = new ArrayList<>(respondents);
                            for(User resp: listForSorting){

                                // remove respondents from other services
                                if(!resp.getService().equals(user.getService())){
                                    listForSorting.remove(resp);
                                }

                                // check if there are respondents left
                                if(listForSorting.size()>0){

                                }
                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                    }
                }
        );
        NetworkSingleton.getInstance(UserCallActivity.this).addToRequestque(dataPointsRequest); // checks if there is a queue, if there is, puts request to it



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

    void showToast(String message){
        Toast toast = new Toast(UserCallActivity.this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }


}
