package com.example.project_1201345_12012093.ui.pizzamenu;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_1201345_12012093.PizzaType;
import com.example.project_1201345_12012093.adapters.PizzaAdapter;
import com.example.project_1201345_12012093.databinding.FragmentPizzamenuBinding;

import java.util.ArrayList;

public class PizzamenuFragment extends Fragment {

    private FragmentPizzamenuBinding binding;
    private ListView listView;
    private PizzaAdapter adapter;
    private EditText searchEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPizzamenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = binding.listViewPizzas;
        searchEditText = binding.searchEditText;

        // PizzaType.getPizzaTypes() method exists and returns a list of pizzas
        ArrayList<PizzaType> pizzaList = PizzaType.getPizzaTypes();
        adapter = new PizzaAdapter(getContext(), pizzaList, listView);
        listView.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.handleSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed after text is changed
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
