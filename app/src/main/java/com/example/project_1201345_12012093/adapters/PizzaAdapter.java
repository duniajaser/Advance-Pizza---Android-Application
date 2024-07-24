package com.example.project_1201345_12012093.adapters;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.project_1201345_12012093.DataBaseHelper;
import com.example.project_1201345_12012093.LogInActivity;
import com.example.project_1201345_12012093.OrderDialogManager;
import com.example.project_1201345_12012093.PizzaType;
import com.example.project_1201345_12012093.R;

import java.util.ArrayList;
import java.util.List;


public class PizzaAdapter extends ArrayAdapter<PizzaType> {
    private List<PizzaType> pizzaList;
    private Context context;
    private String loggedInEmail;

    private ArrayList<PizzaType> pizzaTypeList;
    private ArrayList<PizzaType> pizzaSearch;
    private ListView listView;
    private DataBaseHelper dbHelper;


    public PizzaAdapter(Context context, ArrayList<PizzaType> pizzaList, ListView listView) {
        super(context, 0, pizzaList);
        this.context = context;
        this.pizzaTypeList = new ArrayList<>(pizzaList);
        this.pizzaSearch = new ArrayList<>(pizzaList); // Initialize the search list with the original list
        this.listView = listView;

        // Initialize the dbHelper object
        dbHelper = new DataBaseHelper(context);
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.pizza_item, parent, false);
        }
        PizzaType pizza = getItem(position);
        TextView pizzaName = convertView.findViewById(R.id.txtPizzaName);
        pizzaName.setText(pizza.getName());

        loggedInEmail = LogInActivity.email_;


        Button buttonFavorite = convertView.findViewById(R.id.favorite_button);
        Button buttonOrder = convertView.findViewById(R.id.order_button);


        buttonFavorite.setOnClickListener(v -> {
            handleAddingPizzaToFavorite(loggedInEmail, pizza.getName());
        });

        buttonOrder.setOnClickListener(v -> {
            handleAddingPizzaToOrder(loggedInEmail, pizza.getName());
        });

        convertView.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_customer_home);
            Bundle bundle = new Bundle();
            bundle.putString("pizzaType", pizza.getName());
            navController.navigate(R.id.action_nav_pizza_menu_to_nav_pizza_details, bundle);
        });




        return convertView;
    }

    private void handleAddingPizzaToFavorite(String loggedInEmail, String name) {
        long result = dbHelper.insertFavoritePizza(loggedInEmail, name);
        if (result != -1) {
            Toast.makeText(context, "Added to favorites!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Already in favorites!", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleAddingPizzaToOrder(String loggedInEmail, String name) {
        OrderDialogManager orderDialogManager = new OrderDialogManager(context, loggedInEmail, name);
        orderDialogManager.show();

    }

    public void handleSearch(String text) {
        pizzaSearch.clear();
        if (text == null || text.isEmpty()) {
            pizzaSearch.addAll(pizzaTypeList);
        } else {
            for (PizzaType pizza : pizzaTypeList) {
                if (pizza.getName().toLowerCase().contains(text.toLowerCase()) /*||
                        String.valueOf(pizza.getPrice()).contains(text) ||
                        pizza.getCategory().toLowerCase().contains(text.toLowerCase())
                */) {
                    pizzaSearch.add(pizza);
                }
            }
        }
        clear();
        addAll(pizzaSearch);
        notifyDataSetChanged();
    }



}
