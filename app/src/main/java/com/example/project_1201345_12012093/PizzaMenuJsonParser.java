package com.example.project_1201345_12012093;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;

public class PizzaMenuJsonParser {

    public static ArrayList<String> parseFeed(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray typesArray = jsonObject.getJSONArray("types");
            ArrayList<String> pizzaTypes = new ArrayList<>();

            for (int i = 0; i < typesArray.length(); i++) {
                pizzaTypes.add(typesArray.getString(i));
            }
            return pizzaTypes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
