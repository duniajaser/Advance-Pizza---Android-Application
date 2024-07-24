package com.example.project_1201345_12012093;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginRegistrationSectionActivity extends AppCompatActivity {
    Button logInButton;
    Button SignUnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration_section);

        logInButton = (Button)findViewById(R.id.logInButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginRegistrationSectionActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        SignUnButton = (Button)findViewById(R.id.registrationButton);
        SignUnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginRegistrationSectionActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}