package com.test.glasgowteam12.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.test.glasgowteam12.R;
import com.test.glasgowteam12.Respondent;
import com.test.glasgowteam12.User;

public class LoginActivity extends AppCompatActivity {

    String emailText;
    String passwordText;
    final String LOGIN_URL = "52.214.117.48/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final Button login = (Button)findViewById(R.id.LogIn);
        final EditText email = (EditText)findViewById(R.id.Email);
        final EditText password = (EditText)findViewById(R.id.Password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailText = email.getText().toString();
                passwordText = password.getText().toString();

                if(emailText.equals("")||passwordText.equals("")){
                    showAlert("Login failed", "Some fields are missing");
                } else {

                    // HTTTP not working : dummy data
                    if(emailText.equals("respondent")){
                        Respondent respondent = new Respondent("me", "email", "exp", "military");
                        Intent intent = new Intent(LoginActivity.this ,RespondentCallWait.class);
                        intent.putExtra("respondent", respondent);
                        startActivity(intent);
                    } else {
                        User user = new User("john", "email", "military", "54", "user","help");
                        Intent intent = new Intent(LoginActivity.this ,HomeScreenActivity.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }





                    /*StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                String userType = jsonObject.getString("userType");
                                String hereFor = jsonObject.getString("hereFor");
                                String service = jsonObject.getString("service");
                                String name = jsonObject.getString("name");
                                String age = jsonObject.getString("age");

                                // login failed, show dialog
                                if(code.equals("login_failed"))
                                {
                                    showAlert("Login failed", "Username and/or password is incorrect");
                                }
                                else if(code.equals("login_success"))
                                {
                                    if (userType.equals("user")) {

                                        // login succesful
                                        // Create User object

                                        User user = new User(name, emailText, service, age, userType,hereFor);


                                        // show HomeScreen
                                        Intent intent = new Intent(LoginActivity.this ,HomeScreenActivity.class);
                                        intent.putExtra("user", user);
                                        startActivity(intent);


                                    } else {
                                        Intent intent = new Intent(LoginActivity.this ,RespondentCallActivity.class);
                                        intent.putExtra("email", emailText);
                                        startActivity(intent);
                                    }

                                }
                            } catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showAlert("Error", "Network error has occurred, check your connection");

                        }
                    })
                    {
                        protected Map<String, String> getParams()throws AuthFailureError {
                            Map<String,String>params = new HashMap<String, String>();
                            params.put("email",emailText);
                            params.put("password",passwordText);
                            return params;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            5000,
                            5,
                            5));

                    NetworkSingleton.getInstance(LoginActivity.this).addToRequestque(stringRequest); // checks if there is a queue, if there is, puts request to it*/
                }
            }
        });
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
}
