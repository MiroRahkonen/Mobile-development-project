package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements TextWatcher {

    protected EditText username_edittext;
    protected EditText email_edittext;
    protected EditText password_edittext;
    protected Button register_button;
    protected DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // SQLite database init
        dbManager = new DBManager(this);
        dbManager.open();

        TextView title_textview = findViewById(R.id.appbar_title_textview);
        ImageButton appbar_back_button = findViewById(R.id.appbar_back_button);
        register_button = findViewById(R.id.register_account_button);
        username_edittext= findViewById(R.id.register_username_edittext);
        email_edittext = findViewById(R.id.register_email_edittext);
        password_edittext = findViewById(R.id.register_password_edittext);

        //Add TextViews into TextWatcher
        username_edittext.addTextChangedListener(this);
        email_edittext.addTextChangedListener(this);
        password_edittext.addTextChangedListener(this);

        //Changing appbar title to register activity, make back button visible
        title_textview.setText(getString(R.string.title_register));
        appbar_back_button.setVisibility(View.VISIBLE);

        // Create listener for back button
        appbar_back_button.setOnClickListener(v -> finish());

        //Create listener for register button
        register_button.setOnClickListener(v -> {
            String username = username_edittext.getText().toString();
            String email = email_edittext.getText().toString();
            String password = password_edittext.getText().toString();

            // Attempt account creation on SQLite database
            boolean insert_success = dbManager.createUser(username, email, password);
            if(insert_success){
                Toast.makeText(RegisterActivity.this,"Account successfully created",Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(RegisterActivity.this,"Registration failed, username already in use",Toast.LENGTH_SHORT).show();
            }
        });
        disableRegisterButton(); //On startup make register button un-clickable
    }

    // TextWatcher tutorial from this article https://dzone.com/articles/how-to-monitor-textview-changes-in-android
    // Required TextWatcher functions
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // Checking that every input is valid
        int password_length = password_edittext.getText().toString().length();
        if(password_length < 8){
            disableRegisterButton();
            return;
        }

        String email = email_edittext.getText().toString();
        boolean email_valid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if(!email_valid){
            disableRegisterButton();
            return;
        }

        int username_length = username_edittext.getText().toString().length();
        if(username_length < 4){
            disableRegisterButton();
            return;
        }
        // If all checks pass, enable register button
        enableRegisterButton();
    }

    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override public void afterTextChanged(Editable s) {}

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
}