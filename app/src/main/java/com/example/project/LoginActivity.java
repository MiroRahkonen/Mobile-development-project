package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements TextWatcher {
    protected EditText username_edittext;
    protected EditText password_edittext;
    protected Button login_button;
    protected DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // SQLite DB init
        dbManager = new DBManager(this);
        dbManager.open();

        // Initializing elements
        TextView title_tv= findViewById(R.id.appbar_title_textview);
        title_tv.setText(getString(R.string.title_login));
        login_button = findViewById(R.id.login_button);
        Button goto_register_button = findViewById(R.id.goto_register_button);
        username_edittext = findViewById(R.id.login_username_edittext);
        password_edittext = findViewById(R.id.login_password_edittext);

        //Adding TextViews into TextWatcher, triggers check to see if input is valid
        username_edittext.addTextChangedListener(this);
        password_edittext.addTextChangedListener(this);

        login_button.setOnClickListener(v ->{
            String username = username_edittext.getText().toString();
            String password= password_edittext.getText().toString();

            // Check if an user with supplied credentials exists
            String found_username = dbManager.attemptLogin(username,password);
            if(found_username == null){
                password_edittext.setError("Password is incorrect");
                Toast.makeText(LoginActivity.this,"Invalid credentials",Toast.LENGTH_SHORT).show();
            }
            else{
                username_edittext.getText().clear();
                password_edittext.getText().clear();

                Toast.makeText(LoginActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
                mainIntent.putExtra("username",username);
                startActivity(mainIntent);
            }
        });
        //Initially make login button un-clickable
        disableLoginButton();

        // Redirects to account registration screen
        goto_register_button.setOnClickListener(v -> {
            username_edittext.getText().clear();
            password_edittext.getText().clear();

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
        String username = username_edittext.getText().toString();
        if(username.length() < 4){
            disableLoginButton();
            return;
        }
        String password = password_edittext.getText().toString();
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