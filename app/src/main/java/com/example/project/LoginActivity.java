package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        username_tv.addTextChangedListener(this);
        password_tv.addTextChangedListener(this);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_input = username_tv.getText().toString();
                String password_input = password_tv.getText().toString();
                Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(mainIntent);
            }
        });
        login_button.setClickable(false);

        goto_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });


    }

    protected void checkInputs(String username,String password){
        // If input is valid, make login clickable and change button colors
        if(username.length() >= 4 && password.length() >= 8){
            login_button.setClickable(true);
            login_button.setBackgroundTintList(getColorStateList(R.color.secondary));
            login_button.setTextColor(getColor(R.color.white));
        }else{
            login_button.setClickable(false);
            login_button.setBackgroundTintList(getColorStateList(R.color.inactive));
            login_button.setTextColor(getColor(R.color.black));
        }
    }

    // Help with TextWatcher from this article https://dzone.com/articles/how-to-monitor-textview-changes-in-android
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String username = username_tv.getText().toString();
        String password = password_tv.getText().toString();
        checkInputs(username,password);
    }
    @Override
    public void afterTextChanged(Editable s) {

    }
}