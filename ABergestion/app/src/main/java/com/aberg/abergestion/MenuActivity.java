package com.aberg.abergestion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        gestionBudget = findViewById(R.id.button_gestionBudget);
        gestionStock = findViewById(R.id.button_gestionStock);

        gestionBudget.setOnClickListener(btnGestionBudget);
        gestionStock.setOnClickListener(btnGestionStock);
        
    }

    private View.OnClickListener btnGestionBudget = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, BudgetActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener btnGestionStock = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, StockActivity.class);
            startActivity(intent);
        }
    };
}
