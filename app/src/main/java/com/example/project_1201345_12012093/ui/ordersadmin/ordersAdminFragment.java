package com.example.project_1201345_12012093.ui.ordersadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_1201345_12012093.LogInActivity;
import com.example.project_1201345_12012093.Order;
import com.example.project_1201345_12012093.R;
import com.example.project_1201345_12012093.adapters.ViewAllOrdersAdapter;
import com.example.project_1201345_12012093.databinding.FragmentOrdersAdminBinding;

import java.util.ArrayList;
import com.example.project_1201345_12012093.DataBaseHelper;
public class ordersAdminFragment extends Fragment {

    private FragmentOrdersAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrdersAdminBinding.inflate(inflater, container, false);
        View rootView = inflater.inflate(R.layout.fragment_orders_admin, container, false);
        String userEmail = LogInActivity.email_;

        ListView listView = rootView.findViewById(R.id.all_orders_admin);
        DataBaseHelper db = new DataBaseHelper(getContext());
        ArrayList<Order> orders = db.getAllOrders();
        ViewAllOrdersAdapter viewAllOrdersAdapter = new ViewAllOrdersAdapter(getContext(),orders, listView, userEmail);
        listView.setAdapter(viewAllOrdersAdapter);


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}