package com.example.project_1201345_12012093;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Admin {
    private String loggedInEmail;
    private View rootView;
    private Context context;

    EditText emailText;
    EditText phoneText;
    EditText  fNameText;
    EditText lNameText;
    EditText genderText;

    EditText passwordText;



    public Admin( ){
    }

    public Admin(String loggedInEmail, View rootView, Context context ){
        this.loggedInEmail = loggedInEmail;
        this.rootView = rootView;
        this.context = context;
    }


    public void defineTheElements(View rootView){
        emailText = rootView.findViewById(R.id.editTextEmail);
        fNameText = rootView.findViewById(R.id.editTextFirstName);
        lNameText = rootView.findViewById(R.id.editTextLastName);
        phoneText = rootView.findViewById(R.id.editTextPhone);
        genderText = rootView.findViewById(R.id.editTextGender);
        passwordText = rootView.findViewById(R.id.editTextPassword);
    }

    public boolean validateInput() {
        if (TextUtils.isEmpty(emailText.getText().toString()) || !isValidEmail(emailText.getText().toString())) {
            emailText.setError("Enter a valid email");
            return false;
        }

        if (TextUtils.isEmpty(phoneText.getText().toString()) || !isValidPhone(phoneText.getText().toString())) {
            phoneText.setError("Enter a valid phone number starting with '05'");
            return false;
        }

        if (TextUtils.isEmpty(fNameText.getText().toString()) || (fNameText.getText().toString()).length() < 3) {
            fNameText.setError("Enter a valid first name (at least 3 characters)");
            return false;
        }

        if (TextUtils.isEmpty(lNameText.getText().toString()) || (lNameText.getText().toString()).length() < 3) {
            lNameText.setError("Enter a valid last name (at least 3 characters)");
            return false;
        }

        if (TextUtils.isEmpty(passwordText.getText().toString()) || (passwordText.getText().toString()).length() < 8 || !isValidPassword(passwordText.getText().toString())) {
            passwordText.setError("Password must be at least 8 characters long and contain at least 1 character and 1 number");
            return false;
        }

        return true;
    }

    boolean isValidEmail(String email) {
        // Simple email validation using regex
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isValidPhone(String phone) {
        // Phone number validation: must start with "05" and have exactly 10 digits
        return Pattern.matches("^05\\d{8}$", phone);
    }

    boolean isValidPassword(String password) {
        // Password validation: must contain at least 1 character and 1 number
        return Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).+$").matcher(password).find();
    }



}
