package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements TextWatcher {
    protected TextView username_tv;
    protected TextView password_tv;
    protected Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing elements
        TextView title_tv= (TextView) findViewById(R.id.title_tv);
        title_tv.setText(getString(R.string.title_login));

        login_button = (Button) findViewById(R.id.login_button);
        Button goto_register_button = (Button) findViewById(R.id.goto_register_button);

        username_tv = (TextView) findViewById(R.id.login_username_tv);
        password_tv = (TextView) findViewById(R.id.login_password_tv);

        //Add TextViews onto TextWatcher
        username_tv.addTextChangedListener(this);
        password_tv.addTextChangedListener(this);

        //Clicking login button
        login_button.setOnClickListener(v ->{
            String username_input = username_tv.getText().toString();
            String password_input = password_tv.getText().toString();
            Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(mainIntent);
        });
        disableLoginButton(); //On startup make login unclickable

        // Clicking register button, redirects to register account screen
        goto_register_button.setOnClickListener(v -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });
    }

    protected void disableLoginButton(){
        login_button.setClickable(false);
        login_button.setBackgroundTintList(getColorStateList(R.color.inactive));
        login_button.setTextColor(getColor(R.color.black));
    }

    protected void enableLoginButton(){
        login_button.setClickable(true);
        login_button.setBackgroundTintList(getColorStateList(R.color.secondary));
        login_button.setTextColor(getColor(R.color.white));
    }

    // TextWatcher tutorial from this article https://dzone.com/articles/how-to-monitor-textview-changes-in-android
    // TextWatcher functions
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Checking that every input is valid
        String username = username_tv.getText().toString();
        if(username.length() < 4){
            disableLoginButton();
            return;
        }
        String password = password_tv.getText().toString();
        if(password.length() < 8){
            disableLoginButton();
            return;
        }
        // If all checks pass, enable login button
        enableLoginButton();
    }
    @Override
    public void afterTextChanged(Editable s) {
    }
}