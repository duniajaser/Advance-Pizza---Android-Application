package com.example.project_1201345_12012093;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private CheckBox rememberCheckBox;
    private Button loginButton;
    private DataBaseHelper dbHelper;
    private SharedPreferences sharedPreferences;
    private static final String PREF_EMAIL_KEY = "email";

    static String name;
    public static String email_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberCheckBox = findViewById(R.id.rememberMeCheckBox);
        loginButton = findViewById(R.id.logInButton);

        // Initialize DatabaseHelper
        dbHelper = new DataBaseHelper(this);


        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("", MODE_PRIVATE);

        // Set email if remembered
        if (sharedPreferences.contains(PREF_EMAIL_KEY)) {
            String email = sharedPreferences.getString(PREF_EMAIL_KEY, "");
            emailEditText.setText(email);
            rememberCheckBox.setChecked(true);
        }

        // Set onClickListener for Login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        TextView signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to home screen
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if email exists in the database
        if (dbHelper.checkEmailExists(email)) {
            // Check if the provided password matches the password associated with the email
            if (dbHelper.checkPassword(email, password)) {
                String role = dbHelper.getRole(email);
                // Save email in SharedPreferences if "Remember Me" is checked
                if (rememberCheckBox.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PREF_EMAIL_KEY, email);
                    editor.apply();
                } else {
                    // Clear saved email if "Remember Me" is not checked
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(PREF_EMAIL_KEY);
                    editor.apply();
                }
                 name = dbHelper.getName(email);
                 email_ = dbHelper.getEmail(email);

                // Redirect based on role
                if (role.equalsIgnoreCase("customer")) {
                    // Redirect to customer home screen
                    Intent intent = new Intent(LogInActivity.this, CustomerHomeActivity.class);
                    startActivity(intent);
                    finish();
                } else if (role.equalsIgnoreCase("admin")) {
                    // Redirect to admin home screen
                    Intent intent = new Intent(LogInActivity.this, AdminHome.class);
                    startActivity(intent);
                    finish();
                }

            } else {
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Email not registered", Toast.LENGTH_SHORT).show();
        }
    }



}
