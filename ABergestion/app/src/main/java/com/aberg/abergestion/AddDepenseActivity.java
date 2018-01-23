package com.aberg.abergestion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;

/**
 * Created by louis on 23/01/2018.
 */

public class AddDepenseActivity extends AppCompatActivity {
     private Spinner spJ,spM,spA;
     private EditText eTIntitule,eTMontant;
     private ArrayAdapter<String> adapt1,adapt2,adapt3;
     private ArrayList<String> dataJour,dataAnnee,dataMois;
     private Switch switch_periodicite;
     private Button button_valider;

    private void initDataJour(){
        dataJour = new ArrayList<>();
        for(int i = 1; i < 32; i++){
            dataJour.add(Integer.toString(i));
        }
    }

    private void initDataMois(){
        dataMois = new ArrayList<>();
        for(int i = 1; i < 13; i++){
            dataMois.add(Integer.toString(i));
        }
    }

    private void initDataAnnee(){
        dataAnnee = new ArrayList<>();
        for(int i = 2019; i > 1900; i--){
            dataAnnee.add(Integer.toString(i));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_depense);
        initDataMois();
        initDataJour();
        initDataAnnee();
        button_valider = (Button)findViewById(R.id.button_valider);
        button_valider.setOnClickListener(BtnValider);
        switch_periodicite=(Switch)findViewById(R.id.switch_periodicite);
        spJ = (Spinner)findViewById(R.id.spinner_jour);
        spM = (Spinner)findViewById(R.id.spinner_mois);
        spA = (Spinner)findViewById(R.id.spinner_annee);
        eTIntitule=(EditText)findViewById(R.id.editText_intitule);
        eTMontant=(EditText)findViewById(R.id.editText_montant);
        adapt1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataJour);
        adapt2= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataAnnee);
        adapt3 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataMois);
        spJ.setAdapter(adapt1);
        spM.setAdapter(adapt3);
        spA.setAdapter(adapt2);
    }

    private View.OnClickListener BtnValider = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            String contenuIntitule = eTIntitule.getText().toString();
            String contenuMontant = eTMontant.getText().toString();
            int contenuJour = Integer.parseInt(spJ.getSelectedItem().toString());
            int contenuMois = Integer.parseInt(spM.getSelectedItem().toString());
            int contenuAnnee = Integer.parseInt(spA.getSelectedItem().toString());
            boolean contenuSwitchPerio = switch_periodicite.isChecked();
            System.out.println(contenuIntitule);
            System.out.println(contenuMontant);
            System.out.println(contenuJour);
            System.out.println(contenuMois);
            System.out.println(contenuAnnee);
            System.out.println(contenuSwitchPerio);
        }
    }

}