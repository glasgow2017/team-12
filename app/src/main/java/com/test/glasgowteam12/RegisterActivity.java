package com.test.glasgowteam12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    String imHereForText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Spinner whoAmIDropdown = (Spinner)findViewById(R.id.whoAmIDropdown);
        Spinner imHereForDropdown = (Spinner)findViewById(R.id.imHereForDropdown);
        String whoamiText = whoAmIDropdown.getSelectedItem().toString();
        final EditText otherInput = (EditText)findViewById(R.id.other);

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
    }
}
