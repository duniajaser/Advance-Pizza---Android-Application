package com.example.project_1201345_12012093.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project_1201345_12012093.DataBaseHelper;
import com.example.project_1201345_12012093.LogInActivity;
import com.example.project_1201345_12012093.PizzaType;
import com.example.project_1201345_12012093.R;
import com.example.project_1201345_12012093.SpecialOffer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SpecialOfferAdapter  extends ArrayAdapter<SpecialOffer> {

    private Context context;
    private ArrayList<SpecialOffer> specialOffers;
    private ListView listView;

    TextView textViewPizzaType;
    TextView textViewPizzaSize;

    TextView textViewTotalPrice;
    TextView textViewStartOfferDate;
    TextView textViewEndOfferDate;
    TextView textViewPizzaQuantity;


    Button buttonOrder;

    String loggedInEmail;

    public SpecialOfferAdapter(@NonNull Context context, ArrayList<SpecialOffer> specialOffers, ListView listView) {
        super(context, 0,specialOffers );
        this.context = context;
        this.specialOffers = specialOffers;
        this.listView = listView;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.offer_item, parent, false);

        loggedInEmail = LogInActivity.email_;

        SpecialOffer offer = getItem(position);

        textViewPizzaType = convertView.findViewById(R.id.textViewPizzaType);
        textViewPizzaSize = convertView.findViewById(R.id.textViewPizzaSize);
        textViewPizzaQuantity = convertView.findViewById(R.id.textViewPizzaQuantity);

        textViewTotalPrice = convertView.findViewById(R.id.textViewTotalPrice);
        textViewStartOfferDate = convertView.findViewById(R.id.textViewStartOfferDate);
        textViewEndOfferDate = convertView.findViewById(R.id.textViewEndOfferDate);
        buttonOrder = convertView.findViewById(R.id.buttonOrder);

        // Concatenate all pizza types and sizes into respective strings
        StringBuilder pizzaTypesBuilder = new StringBuilder();
        StringBuilder pizzaSizesBuilder = new StringBuilder();
        StringBuilder pizzaQuantitiesBuilder = new StringBuilder();


        for (PizzaType pizza : offer.getPizzas()) {
            pizzaTypesBuilder.append(pizza.getName()).append(", ");
            pizzaSizesBuilder.append(pizza.getSize()).append(", ");
            pizzaQuantitiesBuilder.append(pizza.getQuantity()).append(", ");

        }

        // Remove the trailing comma and space
        if (pizzaTypesBuilder.length() > 0) {
            pizzaTypesBuilder.setLength(pizzaTypesBuilder.length() - 2);
            pizzaSizesBuilder.setLength(pizzaSizesBuilder.length() - 2);
            pizzaQuantitiesBuilder.setLength(pizzaQuantitiesBuilder.length() - 2);

        }

        textViewPizzaType.setText(pizzaTypesBuilder.toString());
        textViewPizzaSize.setText(pizzaSizesBuilder.toString());
        textViewPizzaQuantity.setText(pizzaQuantitiesBuilder.toString());
        textViewTotalPrice.setText(String.format("$%.2f", offer.getTotalPrice()));
        textViewStartOfferDate.setText(offer.getStartingOfferDate());
        textViewEndOfferDate.setText(offer.getEndingOfferDate());

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add the offer to the offers
                addOfferToCustomerOrders(offer);

            }
        });

        return convertView;
    }

    private void addOfferToCustomerOrders(SpecialOffer offer) {
        // Initialize database helper
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);

        // Get pizzas from the offer
        ArrayList<PizzaType> pizzas = offer.getPizzas();
        int quantity = 0;
        for(PizzaType pizza : pizzas){
            quantity += pizza.getQuantity();
        }
        // Get other order details from the offer
        String orderDateTime = getCurrentDateTime(); // Example method to get current date/time
        double totalPrice = offer.getTotalPrice();
        int isOffer = 1; // Assuming this indicates it's a special offer order

        // Add order to database
        long isInserted = dataBaseHelper.addOrder(loggedInEmail, pizzas, quantity, orderDateTime, totalPrice, isOffer);

        if (isInserted != -1) {
            Toast.makeText(context, "Order added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to add order", Toast.LENGTH_SHORT).show();
        }
    }
    private String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }




}
