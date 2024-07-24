package com.example.project_1201345_12012093.ui.addspecialoffers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_1201345_12012093.DataBaseHelper;
import com.example.project_1201345_12012093.PizzaType;
import com.example.project_1201345_12012093.SpecialOffer;
import com.example.project_1201345_12012093.R;
import com.example.project_1201345_12012093.databinding.FragmentAddSpecialOfferBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSpecialOfferFragment extends Fragment {

    private FragmentAddSpecialOfferBinding binding;

    private LinearLayout pizzaListLayout;
    private Button startingOfferDateButton;
    private ArrayList<View> pizzaViews = new ArrayList<>();

    Button addPizzaButton;
    private Button endingOfferDateButton;
    private EditText totalPriceText;
    private Button submitButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAddSpecialOfferBinding.inflate(inflater, container, false);
        View rootView = inflater.inflate(R.layout.fragment_add_special_offer, container, false);


        pizzaListLayout = rootView.findViewById(R.id.pizzaListLayout);
        addPizzaButton = rootView.findViewById(R.id.addPizzaButton);
        startingOfferDateButton = rootView.findViewById(R.id.startingOfferPeriodEditText);
        endingOfferDateButton = rootView.findViewById(R.id.endingOfferPeriodEditText);
        totalPriceText = rootView.findViewById(R.id.totalPriceEditText);
        submitButton = rootView.findViewById(R.id.submitOfferButton);

        addPizzaButton.setOnClickListener(v -> addPizzaView());

        //Handle the dates
        startingOfferDateButton.setOnClickListener(view -> showDatePickerDialog(true));
        endingOfferDateButton.setOnClickListener(view -> showDatePickerDialog(false));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSpecialOffer();
            }
        });



        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addPizzaView(){
        View pizzaView = getLayoutInflater().inflate(R.layout.pizza_detailes, null);
        pizzaListLayout.addView(pizzaView);
        pizzaViews.add(pizzaView);

        Spinner pizzaTypeSpinner = pizzaView.findViewById(R.id.pizzaTypeSpinner);
        Spinner sizeSpinner = pizzaView.findViewById(R.id.sizeSpinner);

        ArrayList<PizzaType> pizzaTypesAll = PizzaType.getPizzaTypes();
        ArrayList<String> pizzaNames = new ArrayList<>();
        for (PizzaType pizzaType : pizzaTypesAll) {
            pizzaNames.add(pizzaType.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pizzaNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pizzaTypeSpinner.setAdapter(adapter);

        String[] sizes = {"Small", "Medium", "Large"};
        ArrayAdapter<String> sizesAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sizes);
        sizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizesAdapter);
    }

    public void showDatePickerDialog(boolean isStartingDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, day1) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year1, month1, day1);
            String formattedDate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(newDate.getTime());
            if (isStartingDate) {
                startingOfferDateButton.setText(formattedDate);
            } else {
                endingOfferDateButton.setText(formattedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void submitSpecialOffer(){
        int quantity;
        SpecialOffer specialOffer = new SpecialOffer();
        for (View pizzaView : pizzaViews) {
            Spinner pizzaTypeSpinner = pizzaView.findViewById(R.id.pizzaTypeSpinner);
            Spinner sizeSpinner = pizzaView.findViewById(R.id.sizeSpinner);
            EditText quantityForPizza = pizzaView.findViewById(R.id.quantityText);

            String pizzaType = pizzaTypeSpinner.getSelectedItem().toString();
            String pizzaSize = sizeSpinner.getSelectedItem().toString();
            quantity = Integer.parseInt(quantityForPizza.getText().toString());

            PizzaType pizza = new PizzaType(pizzaType, pizzaSize, 0, quantity);
            specialOffer.addPizza(pizza);
        }
        //GET THE DATA
        String startingOfferDate = startingOfferDateButton.getText().toString();
        String endingOfferDate = endingOfferDateButton.getText().toString();
        double totalPrice = Double.parseDouble(totalPriceText.getText().toString());

        // Check if all ok
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        try {
            Date startDate = simpleDateFormat.parse(startingOfferDate);
            Date endDate = simpleDateFormat.parse(endingOfferDate);

            // remove the time component
            startDate = parseDate(startDate);
            endDate = parseDate(endDate);
            Date today = parseDate(Calendar.getInstance().getTime());

            if (startDate.before(today) && !startDate.equals(today)) {
                Toast.makeText(getContext(), "Starting date cannot be before today", Toast.LENGTH_SHORT).show();
                return;
            }
            if (endDate.before(startDate)) {
                Toast.makeText(getContext(), "Ending date cannot be before starting date", Toast.LENGTH_SHORT).show();
                return;
            }
            if(totalPrice <= 0){
                Toast.makeText(getContext(), "Price must be more than zero", Toast.LENGTH_SHORT).show();
                return;
            }

            specialOffer.setStartingOfferDate(startingOfferDate);
            specialOffer.setEndingOfferDate(endingOfferDate);
            specialOffer.setTotalPrice(totalPrice);


            DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
            if(dataBaseHelper.addSpecialOffer( specialOffer) != -1){
                Toast.makeText(getContext(), "Special Offer is added successfully!", Toast.LENGTH_SHORT).show();

            }


        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
        }

    }

    private Date parseDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }




}