package com.example.project_1201345_12012093;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class ChangePasswordActivity extends AppCompatActivity {
    EditText newPasswordText;
    EditText confirmNewPasswordText;
    TextView passwordT;
    Button updatePasswordButton;

    String password;
    String confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newPasswordText = findViewById(R.id.newPassword);
        confirmNewPasswordText = findViewById(R.id.confirmNewPassword);
        updatePasswordButton = findViewById(R.id.saveNewPassword);
        passwordT = findViewById(R.id.passwordT);

        String loggedInPassword = LogInActivity.email_;

        passwordT.setText(loggedInPassword);
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChangingPassword();
            }
        });
    }

    public void handleChangingPassword(){
        password = newPasswordText.getText().toString();
        confirmPassword = confirmNewPasswordText.getText().toString();

        boolean isValid = validateInputs();

    }

    public boolean validateInputs(){
        boolean isValid = true;
        StringBuilder errors = new StringBuilder();

        // check the PASSWORD
        if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
            errors.append("Password must be at least 8 characters long and include at least one letter and one number.\n");
            isValid = false;
        }

        if (!confirmPassword.equals(password)) {
            errors.append("Confirm password does not match the password.\n");
            isValid = false;
        }

        if(!isValid){
            Toast.makeText(this, errors.toString(), Toast.LENGTH_LONG).show();
        }else{

            String loggedInEmail = LogInActivity.email_;
            String encryptedPassword = Hash.hashPassword(password);

            DataBaseHelper dataBaseHelper =new DataBaseHelper(this);
            dataBaseHelper.updateClientPassword(loggedInEmail, encryptedPassword);
        }


        Intent intent = new Intent(ChangePasswordActivity.this, LogInActivity.class);
        startActivity(intent);
        return isValid;

    }


}