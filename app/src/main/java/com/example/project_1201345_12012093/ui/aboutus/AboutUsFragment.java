package com.example.project_1201345_12012093.ui.aboutus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.project_1201345_12012093.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_1201345_12012093.databinding.FragmentAboutUsBinding;

public class AboutUsFragment extends Fragment {

    private FragmentAboutUsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutUsBinding.inflate(inflater, container, false);


        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        // find the buttons
        Button callButton = rootView.findViewById(R.id.buttonCall);
        Button mapsButton = rootView.findViewById(R.id.buttonMaps);
        Button emailButton = rootView.findViewById(R.id.buttonEmail);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0599000000"));
                startActivity(callIntent);
            }
        });

        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:31.961013,35.190483?q=31.961013,35.190483(Label+Name)");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:AdvancePizza@Pizza.com"));
                startActivity(Intent.createChooser(emailIntent, "Send email via..."));
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