package com.example.project_1201345_12012093.ui.specialoffer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_1201345_12012093.DataBaseHelper;
import com.example.project_1201345_12012093.PizzaType;
import com.example.project_1201345_12012093.SpecialOffer;
import com.example.project_1201345_12012093.adapters.SpecialOfferAdapter;
import com.example.project_1201345_12012093.databinding.FragmentSpecialOfferBinding;
import com.example.project_1201345_12012093.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SpecialOfferFragment extends Fragment {

    ListView listView;
    DataBaseHelper dataBaseHelper;
    SpecialOfferAdapter adapter;
    ArrayList<SpecialOffer> specialOffers;

    private FragmentSpecialOfferBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSpecialOfferBinding.inflate(inflater, container, false);

        View rootView = inflater.inflate(R.layout.fragment_special_offer, container, false);
        listView = rootView.findViewById(R.id.listview);
        dataBaseHelper = new DataBaseHelper(getContext());


        specialOffers = dataBaseHelper.getAllOffers();

        // Filter the offers that have expired
       ArrayList<SpecialOffer> validOffers = filterValidOffers(specialOffers);

        adapter = new SpecialOfferAdapter(getContext(), validOffers, listView);
        listView.setAdapter(adapter);


        return rootView;
    }

    private ArrayList<SpecialOffer> filterValidOffers(ArrayList<SpecialOffer> offers) {
        ArrayList<SpecialOffer> validOffers = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        Date currentDate = new Date();

        for (SpecialOffer offer : offers) {
            try {
                Date endDate = dateFormat.parse(offer.getEndingOfferDate());
                if (endDate != null && (endDate.after(currentDate) || endDate.equals(currentDate))) {
                    validOffers.add(offer);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return validOffers;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}