package com.example.project_1201345_12012093.ui.userorders;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_1201345_12012093.LogInActivity;
import com.example.project_1201345_12012093.Order;
import com.example.project_1201345_12012093.adapters.PizzaOrdersAdapter;
import com.example.project_1201345_12012093.R;
import com.example.project_1201345_12012093.DataBaseHelper;

import java.util.ArrayList;


public class UserOrdersFragment extends Fragment {

    // Define parameter key consistent with what's passed in navigation

    // Variable to store the passed parameter
    private String email;
    private ListView listView;
    private TextView emptyTextView;
    private PizzaOrdersAdapter adapter;

    public UserOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameter.
     *
     * @param type Parameter for pizza type.
     * @return A new instance of fragment PizzaDetailsFragment.
     */
    public static UserOrdersFragment newInstance(String type) {
        UserOrdersFragment fragment = new UserOrdersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_orders, container, false);
        listView = rootView.findViewById(R.id.listViewOrders);
        emptyTextView = rootView.findViewById(R.id.textViewNoOrders);

        // Connect to the database and get all orders
        DataBaseHelper db = new DataBaseHelper(getContext(),5);
        email = LogInActivity.email_;

        ArrayList<Order> orders = db.getAllOrdersByEmail(email);
        adapter = new PizzaOrdersAdapter(getContext(), orders, listView, emptyTextView);
        listView.setAdapter(adapter);

        // Set empty view if there are no orders
        if (orders.isEmpty()) {
            listView.setEmptyView(emptyTextView);
        }

        // Inflate the layout for this fragment
        return rootView;
    }
}