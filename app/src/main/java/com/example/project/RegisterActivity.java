package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Changing appbar title to register activity
        TextView title_tv = (TextView) findViewById(R.id.title_tv);
        ImageButton appbar_back_button = (ImageButton) findViewById(R.id.back_button);
        Button register_button = (Button) findViewById(R.id.register_account_button);
        TextView username_tv = (TextView) findViewById(R.id.register_username_tv);
        TextView email_tv = (TextView) findViewById(R.id.register_email_tv);
        TextView password_tv = (TextView) findViewById(R.id.register_password_tv);

        title_tv.setText(getString(R.string.title_register));
        appbar_back_button.setVisibility(View.VISIBLE);

        appbar_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_input = username_tv.getText().toString();
                String email_input = email_tv.getText().toString();
                String password_input = password_tv.getText().toString();
                finish();
            }
        });
        register_button.setClickable(false);
    }
}