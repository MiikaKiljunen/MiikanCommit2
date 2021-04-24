package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity4 extends AppCompatActivity {

    Button confirmButton;

    TextView infoText;
    TextView dietText;
    TextView carbonText;
    TextView beefText;
    TextView fishText;
    TextView porkText;
    TextView dairyText;
    TextView cheeseText;
    TextView riceText;
    TextView eggText;
    TextView restaurantText;

    EditText beefEditText;
    EditText fishEditText;
    EditText porkEditText;
    EditText dairyEditText;
    EditText cheeseEditText;
    EditText riceEditText;
    EditText eggEditText;
    EditText restaurantEditText;

    Spinner dietSpinner;
    Spinner carbonSpinner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        //BUTTON

        confirmButton = (Button) findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SecondActivity();
            }
        });
        //TEXT
        infoText = findViewById(R.id.text_info);
        carbonText = findViewById(R.id.text_lowCarbon);
        dietText = findViewById(R.id.text_diet);
        beefText = findViewById(R.id.text_beefLevel);
        fishText = findViewById(R.id.text_fishLevel);
        porkText = findViewById(R.id.text_porkPoultry);
        dairyText = findViewById(R.id.text_dairyLevel);
        cheeseText = findViewById(R.id.text_cheeseLevel);
        riceText = findViewById(R.id.text_riceLevel);
        eggText = findViewById(R.id.text_eggLevel);
        restaurantText = findViewById(R.id.text_RestaurantSpend);

        //EDIT TEXT

        beefEditText = findViewById(R.id.editTextBeef);
        fishEditText = findViewById(R.id.editTextFish);
        porkEditText = findViewById(R.id.editTextPork);
        dairyEditText = findViewById(R.id.editTextDairy);
        cheeseEditText = findViewById(R.id.editTextCheese);
        riceEditText = findViewById(R.id.editTextRice);
        eggEditText = findViewById(R.id.editTextEgg);
        restaurantEditText = findViewById(R.id.editTextRestaurant);

        //SPINNER
        dietSpinner = findViewById(R.id.spinnerDiet);
        carbonSpinner = findViewById(R.id.spinnerCarbon);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity4.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Diets));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietSpinner.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(MainActivity4.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Confirm));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carbonSpinner.setAdapter(myAdapter2);




    }



    private void SecondActivity(){
        Intent intent = new Intent(MainActivity4.this, MainActivity2.class);
        this.startActivity(intent);
    }



}
