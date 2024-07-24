package com.example.project_1201345_12012093.ui.slideshow;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.project_1201345_12012093.R;


public class Slidesow extends Fragment {

    // Define parameter key consistent with what's passed in navigation
    private static final String ARG_PIZZA_TYPE = "pizzaType";

    // Variable to store the passed parameter
    private String pizzaType;

    public Slidesow() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameter.
     *
     * @param type Parameter for pizza type.
     * @return A new instance of fragment PizzaDetailsFragment.
     */
    public static Slidesow newInstance(String type) {
        Slidesow fragment = new Slidesow();
        Bundle args = new Bundle();
        args.putString(ARG_PIZZA_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pizzaType = getArguments().getString(ARG_PIZZA_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        // Find the TextView
        TextView nameView = view.findViewById(R.id.pizzaName);

        // Set the pizza type
        nameView.setText(pizzaType);

        // Set up the back button
        Button button_return = view.findViewById(R.id.button_return);
        button_return.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            if (navController.getCurrentDestination().getId() == R.id.nav_pizza_details) {
                navController.navigateUp();
            }
        });

        return view;
    }
}