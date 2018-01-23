package com.aberg.abergestion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by louis on 22/01/2018.
 */

public class spinnerDR extends AppCompatActivity {
    //Les views de l'interface
    Spinner sp;
    TextView tvMontant,tvDate,tvInti;
    //Un adapteur pour gérer les données à afficher dans le spinner
    ArrayAdapter<String> adapt;
    //Liste de données à passer dans l'adapteur afin de les voir afficher dans le spinner
    String[] data = {"Course","Restaurant","Sortie"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dr);
        sp = (Spinner)findViewById(R.id.spinnerDR);
        tvMontant = (TextView) findViewById(R.id.textView_montant);
        tvDate = (TextView) findViewById(R.id.textView_date);
        tvInti = (TextView) findViewById(R.id.textView_intitule);
        adapt = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, data);
        sp.setAdapter(adapt);
    }
}
