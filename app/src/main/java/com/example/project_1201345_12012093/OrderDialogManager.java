package com.example.project_1201345_12012093;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class OrderDialogManager {
    private Context context;
    private Dialog dialog;
    private Spinner spinnerSize;
    private EditText editTextQuantity;
    private TextView priceText;
    private String[] PIZZA_SIZES = {"Small", "Medium", "Large"};
    private String loggedInEmail;
    private String pizzaType;

    Button cancelDialog;
    Button submitDialog;
    private DataBaseHelper db;

    public OrderDialogManager(Context context, String loggedInEmail, String pizzaType) {
        this.context = context;
        this.loggedInEmail = loggedInEmail;
        this.pizzaType = pizzaType;
        initDialog();
        db = new DataBaseHelper(context, 5);
    }

    private void initDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_order_pizza);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.custom_dialog_background));
        dialog.setCancelable(false);

        cancelDialog = dialog.findViewById(R.id.buttonCancelOrder);
        submitDialog = dialog.findViewById(R.id.buttonSubmitOrder);

        spinnerSize = dialog.findViewById(R.id.spinnerPizzaSize);
        editTextQuantity = dialog.findViewById(R.id.editTextQuantity);
        priceText = dialog.findViewById(R.id.priceTextView);
        editTextQuantity.setText("1");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, PIZZA_SIZES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapter);

        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePrice();  // Update the price based on the size selected
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        editTextQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                updatePrice();  // Update the price whenever the quantity changes
            }
        });

        cancelDialog.setOnClickListener(cancel -> dialog.dismiss());
        submitDialog.setOnClickListener(v -> handleSubmitOrder());
    }

    public void show() {
        dialog.show();
    }

    private void handleSubmitOrder() {
        int quantity = Integer.parseInt(editTextQuantity.getText().toString());
        if (quantity > 0) {
            double totalPrice = getCurrentPricePerUnit() * quantity;
            PizzaType pizza = new PizzaType(pizzaType, spinnerSize.getSelectedItem().toString(), quantity);
            ArrayList<PizzaType> pizzas = new ArrayList<>();
            pizzas.add(pizza);

            // Use the database instance to add the order
            long insertion = db.addOrder(loggedInEmail, pizzas, quantity, getCurrentDateTime(), totalPrice, 0);
            dialog.dismiss();
            if (insertion != -1) {
                Toast.makeText(context, "Order placed successfully!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Invalid order insertion. Please try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Quantity must be greater than 0", Toast.LENGTH_SHORT).show();
        }
    }


    private void updatePrice() {
        int quantity = 1;
        try {
            quantity = Integer.parseInt(editTextQuantity.getText().toString());
        } catch (NumberFormatException e) {
            quantity = 1;
        }
        double total = getCurrentPricePerUnit() * quantity;
        priceText.setText(String.format("Price: $%.2f", total));
    }

    private double getCurrentPricePerUnit() {
        String selectedSize = spinnerSize.getSelectedItem().toString();
        switch (selectedSize) {
            case "Small": return 7.0;
            case "Medium": return 10.5;
            case "Large": return 15.0;
            default: return 7.0;
        }
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
