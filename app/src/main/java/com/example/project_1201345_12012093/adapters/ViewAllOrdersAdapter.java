package com.example.project_1201345_12012093.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project_1201345_12012093.Order;
import com.example.project_1201345_12012093.PizzaType;
import com.example.project_1201345_12012093.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ViewAllOrdersAdapter  extends ArrayAdapter<Order> {
    private Context context;
    private ArrayList<Order> orders;
    private ListView listView;
    private String userEmail;

    public ViewAllOrdersAdapter(Context context, ArrayList<Order> orders,ListView listView, String userEmail ){
        super(context, 0 , orders);
        this.context = context;
        this.orders = orders;
        this.listView = listView;
        this.userEmail = userEmail;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.order,parent, false);

        TextView emailT = convertView.findViewById(R.id.emailTextView);
        TextView TotalPriceT = convertView.findViewById(R.id.totalPriceTextView);
        TextView quantityT = convertView.findViewById(R.id.quantityTextView);
        TextView pizzaTypeT = convertView.findViewById(R.id.pizzaTypeTextView);
        TextView timeT = convertView.findViewById(R.id.timeTextView);
        TextView dateT = convertView.findViewById(R.id.dateTextView);
        TextView sizeT = convertView.findViewById(R.id.sizeTextView);

        Order order = getItem(position);
        emailT.setText(order.getCustomerEmail());
        TotalPriceT.setText(String.valueOf(order.getTotalPrice()));
        quantityT.setText(String.valueOf(order.getQuantity())); // Assuming you have a method getQuantity() in Order class

        StringBuilder pizzaTypesBuilder = new StringBuilder();
        StringBuilder pizzaSizesBuilder = new StringBuilder();
        for (PizzaType pizza : order.getPizzas()) {
            pizzaTypesBuilder.append(pizza.getName()).append(", ");
            pizzaSizesBuilder.append(pizza.getSize()).append(", ");
        }

        // Remove the trailing comma and space
        if (pizzaTypesBuilder.length() > 0) {
            pizzaTypesBuilder.setLength(pizzaTypesBuilder.length() - 2);
            pizzaSizesBuilder.setLength(pizzaSizesBuilder.length() - 2);
        }

        pizzaTypeT.setText(pizzaTypesBuilder.toString());
        sizeT.setText(pizzaSizesBuilder.toString());

        if (order.getOrderDateTime() != null && !order.getOrderDateTime().isEmpty()) {
            String[] dateTimeParts = order.getOrderDateTime().split(" ");
            if (dateTimeParts.length == 2) {
                dateT.setText(dateTimeParts[0]);  // Date in YYYY-MM-DD
                timeT.setText(dateTimeParts[1]);
            }
        }
        return convertView;
    }
}
