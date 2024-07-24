package com.example.project_1201345_12012093;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, phoneEditText, firstNameEditText, lastNameEditText, passwordEditText, confirmPasswordEditText;
    private Spinner genderSpinner;
    private Button signUpButton;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);

        // Set up gender spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Initialize DatabaseHelper
        dbHelper = new DataBaseHelper(this);

        // Set onClickListener for Sign-Up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String gender = genderSpinner.getSelectedItem().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (!validateInput(email, phone, firstName, lastName, password, confirmPassword)) {
            return;
        }

        // Check if email already exists
        if (dbHelper.checkEmailExists(email)) {
            Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert user data into database
        long result = dbHelper.insertUser(email, phone, firstName, lastName, gender, password, "customer");
        if (result != -1) {
            // Registration successful, redirect to login page
            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validateInput(String email, String phone, String firstName, String lastName, String password, String confirmPassword) {
        if (TextUtils.isEmpty(email) || !isValidEmail(email)) {
            emailEditText.setError("Enter a valid email");
            return false;
        }

        if (TextUtils.isEmpty(phone) || !isValidPhone(phone)) {
            phoneEditText.setError("Enter a valid phone number starting with '05'");
            return false;
        }

        if (TextUtils.isEmpty(firstName) || firstName.length() < 3) {
            firstNameEditText.setError("Enter a valid first name (at least 3 characters)");
            return false;
        }

        if (TextUtils.isEmpty(lastName) || lastName.length() < 3) {
            lastNameEditText.setError("Enter a valid last name (at least 3 characters)");
            return false;
        }

        if (TextUtils.isEmpty(password) || password.length() < 8 || !isValidPassword(password)) {
            passwordEditText.setError("Password must be at least 8 characters long and contain at least 1 character and 1 number");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        // Simple email validation using regex
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        // Phone number validation: must start with "05" and have exactly 10 digits
        return Pattern.matches("^05\\d{8}$", phone);
    }

    private boolean isValidPassword(String password) {
        // Password validation: must contain at least 1 character and 1 number
        return Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).+$").matcher(password).find();
    }
}
