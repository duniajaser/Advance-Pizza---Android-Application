package com.example.project_1201345_12012093.ui.addadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project_1201345_12012093.AdminClass;
import com.example.project_1201345_12012093.DataBaseHelper;
import com.example.project_1201345_12012093.Hash;
import com.example.project_1201345_12012093.R;
import com.example.project_1201345_12012093.databinding.FragmentAddAdminBinding;

import java.util.ArrayList;

public class AddAdminFragment extends Fragment {

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText phone;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private Spinner gender;
    private Button addButton;

    private FragmentAddAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        firstName = root.findViewById(R.id.firstName);
        lastName = root.findViewById(R.id.lastName);
        email = root.findViewById(R.id.email);
        phone = root.findViewById(R.id.phone);
        passwordText = root.findViewById(R.id.pass);
        confirmPasswordText = root.findViewById(R.id.confirmPasswordText);
        gender = root.findViewById(R.id.gender);
        addButton = root.findViewById(R.id.add_button);

        // Set up gender spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInputs();
            }
        });

        return root;
    }


    public void validateInputs(){
        boolean isValid = true;

        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String mail = email.getText().toString();
        String phoneNumber = phone.getText().toString();
        String password = passwordText.getText().toString();
        String selectedGender = gender.getSelectedItem().toString();
        System.out.println("password: "+password);

        DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext());

        // Validate that the email is not used before
        // get the admin from db by email, if it is null then it is not used before.
        AdminClass adminExists = dataBaseHelper.getAdminByEmail(mail);
        if(adminExists != null){
            isValid = false;
            email.setError("Email already used.\n");
        }
        // then check if the email is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("Invalid email format.\n");
            isValid = false;
        }
        // Validate the phone number
        if(!phoneNumber.matches("05\\d{8}"))
        {
            phone.setError("Phone number must start with '05' and be 10 digits long.\n");
            isValid = false;
        }

        //VALIDATE THE FIRST NAME
        if (first.length() < 3 ) {
            firstName.setError("First name must be at least 3 characters long.\n");
            isValid = false;
        }

        if(last.length() < 3) {
            lastName.setError("Last name must be at least 3 characters long.\n");
            isValid = false;
        }


        if (password.length() < 8 || !password.matches(".*[a-zA-Z]+.*") || !password.matches(".*\\d+.*")) {
            passwordText.setError("Password must be at least 8 characters long and include at least one letter and one number.\n");
            isValid = false;
        }
        //VALIDATE CONFIRM PASSWORD
        if (!confirmPasswordText.getText().toString().equals(password)) {
            confirmPasswordText.setError("Confirm password does not match the password.\n");
            isValid = false;
        }

        if(isValid){
            AdminClass admin = new AdminClass();
            admin.setEmail(mail);
            admin.setFirstName(first);
            admin.setLastName(last);
            admin.setPhone(phoneNumber);
            admin.setHashedPassword(Hash.hashPassword(password));
            admin.setGender(selectedGender);
            admin.setRole("admin");
            dataBaseHelper.insertAdmin(admin);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}