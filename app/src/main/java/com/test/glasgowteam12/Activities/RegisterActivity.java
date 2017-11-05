package com.test.glasgowteam12.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    String imHereForText = "";
    String whoIAmText = "";
    final String REGISTER_URL = "52.214.117.48/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get UI objects
        final Spinner whoAmIDropdown = (Spinner)findViewById(R.id.whoAmIDropdown);
        final Spinner imHereForDropdown = (Spinner)findViewById(R.id.imHereForDropdown);
        Button signUp = (Button)findViewById(R.id.signUp);
        final EditText usernameEditText = (EditText)findViewById(R.id.UserName);
        final EditText emailEditText = (EditText)findViewById(R.id.Email);
        final EditText phoneNoEditText = (EditText)findViewById(R.id.PhoneNo);
        final EditText passwordEditText = (EditText)findViewById(R.id.Password);
        final EditText passwordConfirmEditText = (EditText)findViewById(R.id.PasswordConfirm);
        final EditText otherInput = (EditText)findViewById(R.id.other);


        // Set Object Listeners
        imHereForDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imHereForText = parent.getSelectedItem().toString();
                // If user can't find what he is looking for - show him input box
                if(imHereForText.equals("other")){
                    otherInput.setVisibility(View.VISIBLE);
                }else{
                    otherInput.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        whoAmIDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                whoIAmText = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get info from fields

                final String username = usernameEditText.getText().toString();
                final String email = emailEditText.getText().toString();
                final String phone = phoneNoEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final String passwordConfirm = passwordConfirmEditText.getText().toString();


                /** if imHereForDropdown was not changed, get it's initial value to a text variable,
                 *  if "other" was selected, set imHereForText to the text they have inputed in the input provided
                 * */
                if(imHereForText.equals("")){
                    imHereForText = imHereForDropdown.getSelectedItem().toString();
                } else if(imHereForText.equals("other")) {
                    imHereForText = otherInput.getText().toString();
                }


                if(username.equals("")||email.equals("")||username.equals("")||password.equals("")||
                        passwordConfirm.equals("")||imHereForText.equals("")||whoIAmText.equals(""))
                {

                    showAlert("Missing fields", "Some of the fields are missing");

                }
                else
                {
                    if(!password.equals(passwordConfirm))
                    {

                        showAlert("Failure", "Passwords do not match");

                    }
                    else
                    {
                        // Make Network request and send info from inputs to register a person
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            // get response from the server
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");

                                            // check if register was successful
                                            if(code.equals("register_success")){
                                                // Registration successfull - show HomeScreen
                                            } else if(code.equals("register_failed")){
                                                // registration failed, show alert dialog

                                                showAlert("Error", "Registration failed, please try again");
                                            }

                                        } catch (JSONException e){
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener(){
                            public void onErrorResponse(VolleyError error){
                                error.printStackTrace();
                                showAlert("Network Error", "Network error has occurred, please check your connection");
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String>params = new HashMap<String, String>();

                                params.put("username",username);
                                params.put("email",email);
                                params.put("phone",phone);
                                params.put("password", password);
                                params.put("imHereFor", imHereForText);
                                params.put("whoIAm", whoIAmText);

                                return params;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                5000,
                                5,
                                5));
                        NetworkSingleton.getInstance(RegisterActivity.this).addToRequestque(stringRequest);
                    }
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
