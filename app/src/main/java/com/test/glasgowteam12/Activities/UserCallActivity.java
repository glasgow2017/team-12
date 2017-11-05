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
import com.test.glasgowteam12.Respondent;
import com.test.glasgowteam12.SinchCallListener;
import com.test.glasgowteam12.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserCallActivity extends AppCompatActivity {

    private Call call;
    User user;
    final String PARSE_ONLINE_RESPONDENTS_LIST_URL = "";
    ArrayList<Respondent> respondents;
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

                                Respondent resp = new Respondent(name, respEmail, experience, service);
                                respondents.add(resp);
                            }

                            // The list of respondents was downloaded, now do sorting
                            // create a copy for sorting in case we need to revert changes
                            ArrayList<Respondent> listForSorting = new ArrayList<>(respondents);
                            Respondent ChosenResp;
                            for(Respondent resp: listForSorting){

                                // remove respondents from other services
                                if(!resp.getService().equals(user.getService())){
                                    listForSorting.remove(resp);
                                }
                            }

                            // check if there are respondents left after sorting
                            if(listForSorting.size()>0){
                                //TODO: from remaining respondents choose one with enough experience for the demand level
                                // (For now just choose the first one from the list)
                                ChosenResp = listForSorting.get(0);
                            } else if(listForSorting.size() == 1){
                                // Only one respondent left so grab his info for connecting to him
                                ChosenResp = listForSorting.get(0);
                            } else {
                                // No respondents left, so just choose a guy with a high level of experience from any service
                                Collections.sort(respondents, new Comparator<Respondent>() {
                                    public int compare(Respondent o1, Respondent o2) {
                                        return o1.getExperience().compareTo(o2.getExperience());
                                    }
                                });

                                ChosenResp = respondents.get(0);
                            }


                            // Since we have our chosenRespondent - connect to him
                            sinchClient.getCallClient().callUser(ChosenResp.getEmail());


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
