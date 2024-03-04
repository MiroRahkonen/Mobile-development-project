package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements TextWatcher {

    protected TextView username_tv;
    protected TextView email_tv;
    protected TextView password_tv;
    protected Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView title_tv = (TextView) findViewById(R.id.title_tv);
        ImageButton appbar_back_button = (ImageButton) findViewById(R.id.back_button);
        register_button = (Button) findViewById(R.id.register_account_button);
        username_tv = (TextView) findViewById(R.id.register_username_tv);
        email_tv = (TextView) findViewById(R.id.register_email_tv);
        password_tv = (TextView) findViewById(R.id.register_password_tv);

        //Add TextViews into TextWatcher
        username_tv.addTextChangedListener(this);
        email_tv.addTextChangedListener(this);
        password_tv.addTextChangedListener(this);

        //Changing appbar title to register activity, make back button visible
        title_tv.setText(getString(R.string.title_register));
        appbar_back_button.setVisibility(View.VISIBLE);

        // Create listener for back button
        appbar_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Create listener for register button
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_input = username_tv.getText().toString();
                String email_input = email_tv.getText().toString();
                String password_input = password_tv.getText().toString();
                finish();
            }
        });
        disableRegisterButton(); //On startup make register button unclickable
    }

    protected void disableRegisterButton(){
        register_button.setClickable(false);
        register_button.setBackgroundTintList(getColorStateList(R.color.inactive));
        register_button.setTextColor(getColor(R.color.black));
    }

    protected void enableRegisterButton(){
        register_button.setClickable(true);
        register_button.setBackgroundTintList(getColorStateList(R.color.secondary));
        register_button.setTextColor(getColor(R.color.white));
    }

    // TextWatcher tutorial from this article https://dzone.com/articles/how-to-monitor-textview-changes-in-android
    // Required TextWatcher functions
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Checking that every input is valid
        int username_length = username_tv.getText().toString().length();
        if(username_length < 4){
            disableRegisterButton();
            return;
        }

        String email = email_tv.getText().toString();
        boolean email_is_valid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if(!email_is_valid){
            disableRegisterButton();
            return;
        }

        int password_length = password_tv.getText().toString().length();
        if(password_length < 8){
            disableRegisterButton();
            return;
        }
        // If all checks pass, enable register button
        enableRegisterButton();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}