package com.example.project_1201345_12012093.ui.adminhome;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_1201345_12012093.Admin;
import com.example.project_1201345_12012093.AdminClass;
import com.example.project_1201345_12012093.DataBaseHelper;
import com.example.project_1201345_12012093.Hash;
import com.example.project_1201345_12012093.LogInActivity;
import com.example.project_1201345_12012093.R;
import com.example.project_1201345_12012093.databinding.FragmentAdminHomeBinding;

public class AdminHomeFragment extends Fragment {


    Button saveButton;
    String  hashedPassword;

    EditText emailText;
    EditText phoneText;
    EditText  fNameText;
    EditText lNameText;
    EditText genderText;

    EditText passwordText;

    private FragmentAdminHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);

        View rootView = inflater.inflate(R.layout.fragment_admin_home, container, false);

        ImageView birdAnimationView = rootView.findViewById(R.id.bird_animation_view);
        AnimationDrawable birdAnimation = (AnimationDrawable) birdAnimationView.getDrawable();
        birdAnimation.start();

        String loggedInEmail = LogInActivity.email_;
        DataBaseHelper dataBaseHelper =new DataBaseHelper(getContext());

        AdminClass admin = dataBaseHelper.getAdminByEmail(loggedInEmail);
        if (admin != null) {
            // Admin data retrieved successfully
            System.out.println("Admin Name: " + admin.getFirstName() + " " + admin.getLastName());
        } else {
            // No admin found with the given email
            System.out.println("No admin found with the given email.");
        }

        Admin adminConfiguration = new Admin(loggedInEmail, rootView, getContext());

        adminConfiguration.defineTheElements(rootView);

        emailText = rootView.findViewById(R.id.editTextEmail);
        emailText.setText(loggedInEmail);

        fNameText = rootView.findViewById(R.id.editTextFirstName);
        fNameText.setText(admin.getFirstName());

        lNameText = rootView.findViewById(R.id.editTextLastName);
        lNameText.setText(admin.getLastName());

        phoneText = rootView.findViewById(R.id.editTextPhone);
        phoneText.setText(admin.getPhone());

        genderText = rootView.findViewById(R.id.editTextGender);
        genderText.setText(admin.getGender());

        passwordText = rootView.findViewById(R.id.editTextPassword);


        saveButton  = rootView.findViewById(R.id.buttonSaveChanges);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isValid = adminConfiguration.validateInput();
                if(!isValid){
                    Toast.makeText(getContext(), "Invalid Update!", Toast.LENGTH_LONG).show();
                }else{
                    // Save The Admin Info Into the data base
                    AdminClass admin = new AdminClass();
                    admin.setEmail(loggedInEmail);
                    admin.setFirstName(fNameText.getText().toString());
                    admin.setLastName(lNameText.getText().toString());
                    admin.setPhone(phoneText.getText().toString());
                    hashedPassword = Hash.hashPassword(passwordText.getText().toString());
                    admin.setHashedPassword(hashedPassword);
                    admin.setGender(genderText.getText().toString());
                    admin.setRole("admin");

                    if(dataBaseHelper.updateAdmin(admin)){
                        Toast.makeText(getContext(), "Data updated sucssesfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "There is an error in database", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}