package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {

    TextView Text_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageButton logoutButton;
        logoutButton = (ImageButton) findViewById(R.id.imageButton_logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstActivity();
            }
        });
        Text_data = (TextView) findViewById((R.id.DataTextview));
        Button showGraphButton;
        showGraphButton = (Button) findViewById(R.id.btn_ShowGraph);

        showGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);
                String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=omnivore&query.lowCarbonPreference=true&query.beefLevel=111&query.fishLevel=111&query.porkPoultryLevel=111&query.dairyLevel=111&query.cheeseLevel=111&query.riceLevel=111&query.eggLevel=111&query.winterSaladLevel=111&query.restaurantSpending=132&api_key=10";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                //Text_data.setText("Response is: "+ response.substring(0,500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Text_data.setText("That didn't work!");
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

        Button editDataButton;
        editDataButton = (Button) findViewById(R.id.btn_EditData);

        editDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FourthActivity();
            }
        });

    }
    private void FirstActivity(){
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        this.startActivity(intent);
    }

    private void FourthActivity(){
        Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
        this.startActivity(intent);
    }
}