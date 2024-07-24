package com.example.project_1201345_12012093.ui.userprofile;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_1201345_12012093.ChangePasswordActivity;
import com.example.project_1201345_12012093.Customer;
import com.example.project_1201345_12012093.CustomerHomeActivity;
import com.example.project_1201345_12012093.DataBaseHelper;
import com.example.project_1201345_12012093.R;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.project_1201345_12012093.LogInActivity;
import com.example.project_1201345_12012093.databinding.FragmentUserProfileBinding;

public class UserProfileFragment extends Fragment {

    Button saveButton;
    Button changePasswordButton;
    Spinner genderSpinner;
    EditText emailText;
    EditText fNameText;
    EditText lNameText;
    EditText phoneText;
    String email, fName, lName, phone, gender, hashedPassword;
    Button changePictureButton;

    private FragmentUserProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String loggedInEmail = LogInActivity.email_;

        // Find the root view from binding
        View rootView = binding.getRoot();
        ((EditText)(rootView.findViewById(R.id.editTextEmail))).setText(loggedInEmail);

        // Find the Spinner using rootView
        Spinner genderSpinner = rootView.findViewById(R.id.genderSpinner);

        // Set up gender spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

       changePasswordButton = rootView.findViewById(R.id.buttonChangePassword);

        displayAllCustomers(loggedInEmail, rootView, genderSpinner, adapter);


        saveButton = rootView.findViewById(R.id.buttonSaveChanges);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextPlains(rootView);
                getNewInfo();
                validateInputs();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getTextPlains(View rootView){
        emailText = rootView.findViewById(R.id.editTextEmail);
        fNameText = rootView.findViewById(R.id.editTextFirstName);
        lNameText = rootView.findViewById(R.id.editTextLastName);
        phoneText = rootView.findViewById(R.id.editTextPhone);
        genderSpinner = rootView.findViewById(R.id.genderSpinner);
    }

    public void getNewInfo(){
        email = emailText.getText().toString();
        fName = fNameText.getText().toString();
        lName = lNameText.getText().toString();
        phone = phoneText.getText().toString();
        gender = genderSpinner.getSelectedItem().toString();
    }




    public void displayClientInfo(Cursor cursor, String loggedInEmail, View rootView, Spinner spinnerGender, ArrayAdapter<CharSequence> adapter){
        int phoneColIndex = cursor.getColumnIndex("phone");
        int firstNameColIndex = cursor.getColumnIndex("first_name");
        int lastNameColIndex = cursor.getColumnIndex("last_name");
        int genderColIndex = cursor.getColumnIndex("gender");
        int encryptedPasswordIndex = cursor.getColumnIndex("password");

        Customer client = new Customer();
        client.setEmail(loggedInEmail);
        client.setPhone(cursor.getString(phoneColIndex));
        client.setGender(cursor.getString(genderColIndex));
        client.setFirstName(cursor.getString(firstNameColIndex));
        client.setLastName(cursor.getString(lastNameColIndex));
        hashedPassword = cursor.getString(encryptedPasswordIndex);


        // Now set these values to EditTexts using rootView
        ((EditText) rootView.findViewById(R.id.editTextEmail)).setText(client.getEmail());
        ((EditText) rootView.findViewById(R.id.editTextPhone)).setText(client.getPhone());
        ((EditText) rootView.findViewById(R.id.editTextFirstName)).setText(client.getFirstName());
        ((EditText) rootView.findViewById(R.id.editTextLastName)).setText(client.getLastName());
        String currentGender = client.getGender(); // Assuming you retrieved this from the database
        if (currentGender != null) {
            int spinnerPosition = adapter.getPosition(currentGender);
            spinnerGender.setSelection(spinnerPosition);
        }

    }

    public void displayAllCustomers(String loggedInEmail, View rootView, Spinner spinnerGender, ArrayAdapter<CharSequence> adapter) {
        DataBaseHelper db = new DataBaseHelper(getContext());
        Cursor cursor = db.getAllCustomers();
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    int emailColIndex = cursor.getColumnIndex("EMAIL");
                    if (emailColIndex != -1 && cursor.getString(emailColIndex).equals(loggedInEmail)) {
                        displayClientInfo(cursor, loggedInEmail, rootView, spinnerGender, adapter);
                    }
                }
            } finally {
                cursor.close(); // Close the cursor to avoid memory leaks
            }
        }
    }

    public void validateInputs(){
        boolean isValid = true;
        StringBuilder errors = new StringBuilder();
        // check
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.append("Invalid email format.\n");
            isValid = false;
        }

        //check
        if(!phone.matches("05\\d{8}"))
        {
            errors.append("Phone number must start with '05' and be 10 digits long.\n");
            isValid = false;
        }

        //check
        if (fName.length() < 3 || lName.length() < 3) {
            errors.append("First name and Last name must be at least 3 characters long.\n");
            isValid = false;
        }

        if(!isValid){
            Toast.makeText(getContext(), errors.toString(), Toast.LENGTH_LONG).show();
        }else{
            //save the new data into DB
            DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext());
            Customer client = new Customer();
            client.setEmail(email);
            client.setFirstName(fName);
            client.setLastName(lName);
            client.setPhone(phone);
            client.setHashedPassword(hashedPassword);
            client.setGender(gender);

            dataBaseHelper.updateCustomer(client);
            Intent intent = new Intent(getContext(), CustomerHomeActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(), "Data updated successfully!", Toast.LENGTH_SHORT).show();
        }

    }



}