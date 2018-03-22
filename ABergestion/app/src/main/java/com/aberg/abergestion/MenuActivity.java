package com.aberg.abergestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mael on 20/12/2017.
 */

public class MenuActivity extends AppCompatActivity {

    //On crée les différents composants de l'activité
    private Button gestionBudget;
    private Button gestionStock;
    private Button gestionPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //On lie le JAVA et le XML
        gestionBudget = findViewById(R.id.button_gestionBudget);
        gestionStock = findViewById(R.id.button_gestionStock);
        gestionPW = (Button) findViewById(R.id.button_gestionPassword);

        //On ajoute les listeners sur les trois boutons
        gestionBudget.setOnClickListener(btnGestionBudget);
        gestionStock.setOnClickListener(btnGestionStock);
        gestionPW.setOnClickListener(btnGestionPW);

    }

    //Pour le budget, on redirige vers la gestion de budget
    private View.OnClickListener btnGestionBudget = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, BudgetActivity.class);
            startActivity(intent);
        }
    };

    //Pour le stock, on redirige vers la gestion de stock
    private View.OnClickListener btnGestionStock = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, StockActivity.class);
            startActivity(intent);
        }
    };

    //Pour le mot de passe, on redirige sur la page de modification de mot de passe
    private View.OnClickListener btnGestionPW = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuActivity.this, ModifyPasswordActivity.class);
            startActivity(intent);
        }
    };
}
