package com.aberg.abergestion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mael on 20/12/2017.
 */

public class MenuActivity extends AppCompatActivity {

    private TextView name;
    private Button gestionBudget;
    private Button gestionStock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        //name = findViewById(R.id.textView5);
        gestionBudget = findViewById(R.id.button_gestionBudget);
        gestionStock = findViewById(R.id.button_gestionStock);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String nom = preferences.getString("NAME","XXX");

        //name.setText(nom);






    }
}
