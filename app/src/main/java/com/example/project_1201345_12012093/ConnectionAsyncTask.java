package com.example.project_1201345_12012093;

import android.content.Context;
import android.os.AsyncTask;
import java.util.ArrayList;

public class ConnectionAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;
    ArrayList<String> pizzaTypes;


    public ConnectionAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        return HttpManager.getData(url);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            pizzaTypes = PizzaMenuJsonParser.parseFeed(result);
            assert pizzaTypes != null;
            for (String type: pizzaTypes) PizzaType.addPizzaType(type);

            if (pizzaTypes != null && !pizzaTypes.isEmpty()) {
                ((MainActivity) context).onConnectionSuccess();
            } else {
                ((MainActivity) context).onConnectionFailure();
            }
        } else {
            ((MainActivity) context).onConnectionFailure();
        }
    }
}
