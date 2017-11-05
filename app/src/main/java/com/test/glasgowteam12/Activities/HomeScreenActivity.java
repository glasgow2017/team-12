package com.test.glasgowteam12.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.test.glasgowteam12.NetworkSingleton;
import com.test.glasgowteam12.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeScreenActivity extends AppCompatActivity {

    Intent intent;
    TextView text_seekbar1;
    private final String SEND_MOOD_URL = "";
    int currentValue = -1;
    SeekBar seekbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();
        final String email = bundleExtras.getString("email");

        text_seekbar1 = (TextView) findViewById(R.id.text_seekBar1);
        seekbar1 = (SeekBar) findViewById(R.id.seekBar1);



        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentValue = progress;
                text_seekbar1.setText(Integer.toString(currentValue) + "/10");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Button submitMoodButton = (Button)findViewById(R.id.submitMood);

        submitMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get current time

                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                final String todayString = formatter.format(todayDate);

                // put mood to the server if it was changed
                if(currentValue == -1){
                    showToast("You haven't set your mood");
                }else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, SEND_MOOD_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");

                                // login failed, show dialog
                                if(code.equals("success"))
                                {
                                    showToast("Thank you!");
                                }
                                else if(code.equals("failure"))
                                {
                                    showToast("Sadly, we have encountered the network error, please try again later!");
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
                            params.put("date",todayString);
                            params.put("mood",String.valueOf(currentValue));
                            params.put("email", email);
                            return params;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            5000,
                            5,
                            5));

                    NetworkSingleton.getInstance(HomeScreenActivity.this).addToRequestque(stringRequest); // checks if there is a queue, if there is, puts request to it
                }
            }
        });
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.dashboard_button) {
            // do something here


            intent = new Intent(HomeScreenActivity.this, Dashboard.class);
            Bundle bundle = new Bundle();
            bundle.putInt("seekBarValue1", currentValue);
            intent.putExtras(bundle);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


    void showAlert(String title, String message){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    void showToast(String message){
        Toast toast = new Toast(HomeScreenActivity.this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(message);
        toast.show();
    }
}
