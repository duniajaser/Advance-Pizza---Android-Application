package com.example.project_1201345_12012093.ui.userfav;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project_1201345_12012093.DataBaseHelper;
import com.example.project_1201345_12012093.Favorite;
import com.example.project_1201345_12012093.adapters.FavoritePizzaAdapter;
import com.example.project_1201345_12012093.LogInActivity;
import com.example.project_1201345_12012093.R;
import com.example.project_1201345_12012093.databinding.FragmentUserfavBinding;

import java.util.ArrayList;


public class UserfavFragment extends Fragment {

    private FragmentUserfavBinding binding;

    ListView listView;
    private FavoritePizzaAdapter adapter;
    private ArrayList<Favorite> favorites;
    private TextView emptyTextView;

    String email;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserfavBinding.inflate(inflater, container, false);

        email = LogInActivity.email_;
        View view = inflater.inflate(R.layout.fragment_userfav, container, false);

        listView = view.findViewById(R.id.listViewFavorites);
        emptyTextView = view.findViewById(R.id.textViewNoFavorites);

        DataBaseHelper db = new DataBaseHelper(getContext());
        favorites = db.getAllFavoritesForCustomer(email);
        adapter = new FavoritePizzaAdapter(getContext(),favorites, listView, email, emptyTextView);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
