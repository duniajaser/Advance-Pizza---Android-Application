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
import com.example.project_1201345_12012093.Favorite;
import com.example.project_1201345_12012093.OrderDialogManager;
import com.example.project_1201345_12012093.R;

import java.util.ArrayList;

public class  FavoritePizzaAdapter extends ArrayAdapter<Favorite> {
    private Context context;
    private ArrayList<Favorite> favorites;
    private ListView listView;

    private String userEmail;

    private TextView emptyTextView;

    Button undo;
    Button order;


    public FavoritePizzaAdapter(Context context, ArrayList<Favorite> favorites, ListView listView, String userEmail, TextView emptyTextView){
        super(context, 0 ,favorites);
        this.context = context;
        this.favorites = favorites;
        this.listView = listView;
        this.userEmail = userEmail;
        this.emptyTextView = emptyTextView;
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (favorites.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
            // create a toast
            Toast.makeText(context, "No favorite pizzas found", Toast.LENGTH_SHORT).show();
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
        }
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.favorite_pizza_item,parent, false);
        Favorite favorite = getItem(position);


        TextView textViewName = convertView.findViewById(R.id.txtPizzaName);
        undo = convertView.findViewById(R.id.buttonRemoveFavorite);
        order = convertView.findViewById(R.id.buttonOrder);
        textViewName.setText(favorite.getPizzaType());


        undo.setOnClickListener(v -> {
            handleRemoveFromFavorite(favorite, position);
        });

        order.setOnClickListener(v -> {
            OrderDialogManager orderDialogManager = new OrderDialogManager(context, userEmail, favorite.getPizzaType());
            orderDialogManager.show();
        });

        return convertView;
    }


    public void handleRemoveFromFavorite(Favorite favorite, int position){

        DataBaseHelper db = new DataBaseHelper(context);
        boolean isRemoved = db.removeFavorite(userEmail,favorite.getPizzaType() );
        if(isRemoved){
            Toast.makeText(context, "Has been removed",Toast.LENGTH_SHORT).show();
            this.notifyDataSetChanged(); // Notify the adapter to refresh the list view
            this.favorites.remove(position);
        }
        else
            Toast.makeText(context, "Error Removing the pizza",Toast.LENGTH_SHORT).show();
    }


}
