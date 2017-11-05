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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

import java.util.HashMap;
import java.util.Map;

public class UserCallActivity extends AppCompatActivity {

    private Call call;
    User user;
    final String PARSE_ONLINE_RESPONDENTS_LIST_URL = "";
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, PARSE_ONLINE_RESPONDENTS_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("code");
                    String userType = jsonObject.getString("userType");

                    // login failed, show dialog
                    if(code.equals("failure"))
                    {
                        showToast("Failed fetching online respondents help");
                    }
                    else if(code.equals("success"))
                    {


                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Network error has occurred, check your connection");

            }
        })
        {
            protected Map<String, String> getParams()throws AuthFailureError {
                Map<String,String>params = new HashMap<String, String>();
                params.put("email",user.getEmail());
                params.put("service",user.getService());
                params.put("age",user.getAge());
                params.put("hereFor",user.getHereFor());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                5,
                5));

        NetworkSingleton.getInstance(UserCallActivity.this).addToRequestque(stringRequest); // checks if there is a queue, if there is, puts request to it



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
