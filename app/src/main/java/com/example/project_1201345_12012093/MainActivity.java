package com.example.project_1201345_12012093;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button getStartedButton;
    private DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper = new DataBaseHelper(this);
        //dataBaseHelper.resetAllTables();

        getStartedButton = findViewById(R.id.getStartedButton);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConnectionAsyncTask(MainActivity.this).execute("https://18fbea62d74a40eab49f72e12163fe6c.api.mockbin.io/");
            }
        });
    }

    public void onConnectionSuccess() {
        Intent intent = new Intent(this, LoginRegistrationSectionActivity.class);
        startActivity(intent);
    }

    public void onConnectionFailure() {
        Toast.makeText(this, "Failed to connect to server. Please try again.", Toast.LENGTH_LONG).show();
    }
}
