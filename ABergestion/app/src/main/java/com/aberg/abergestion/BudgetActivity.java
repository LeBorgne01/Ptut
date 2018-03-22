package com.aberg.abergestion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by louis on 20/12/2017.
 */

public class BudgetActivity extends AppCompatActivity implements View.OnClickListener,Serializable{

    private TextView budget_restant,textView_budget_restant;
    private Button button_voir_detail, add_depense, add_revenu,buttonAfficherStats;
    private donneesBudget initList;
    private ArrayList<donneesBudget> listDR;
    double budgetMontantTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBudget();
        Intent intent = getIntent();
        listDR = new ArrayList<>();
        loadListDR(listDR);
        for(int i =0; i < listDR.size();i++){
            budgetMontantTotal += listDR.get(i).getMontant();
        }
        budget_restant.setText(String.valueOf(budgetMontantTotal));
        add_depense = findViewById(R.id.add_depense);
        add_revenu = (Button)findViewById(R.id.add_revenu);
        button_voir_detail = findViewById(R.id.button_voir_detail);
        button_voir_detail.setOnClickListener(this);
        add_depense.setOnClickListener(this);
        add_revenu.setOnClickListener(this);
        buttonAfficherStats = findViewById(R.id.button_afficher_stats);
        buttonAfficherStats.setOnClickListener(this);
    }

    private void activityBudget() {
        setContentView(R.layout.activity_budget);
        budget_restant= findViewById(R.id.budget_restant);
        textView_budget_restant = findViewById(R.id.textView_budget_restant);
    }

    //Interaction
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_voir_detail){
            Intent intent = new Intent(BudgetActivity.this, DepenseRevenuActivity.class);
            startActivity(intent);
            BudgetActivity.this.finish();
        }
        if(view.getId() == R.id.add_depense){
            Intent intent= new Intent(BudgetActivity.this,AddDepenseActivity.class);
            startActivity(intent);
            BudgetActivity.this.finish();
        }
        if(view.getId() == R.id.add_revenu){
            Intent intent = new Intent(BudgetActivity.this, AddRevenuActivity.class);
            startActivity(intent);
            BudgetActivity.this.finish();
        }
        if(view.getId() == R.id.button_afficher_stats){
            Intent intent = new Intent(BudgetActivity.this, AfficherStatActivity.class);
            startActivity(intent);
            BudgetActivity.this.finish();
        }
    }

    /*@Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.button_voir_detail){

        }
        return true;
    }*/

    public Date toDate(String s){
        String[] tabS = s.split("/");
        Date d = new Date(Integer.parseInt(tabS[0]),Integer.parseInt(tabS[1]),Integer.parseInt(tabS[2]));
        System.out.println(d);
        return d;
    }

    private void loadListDR(ArrayList<donneesBudget> liste){
        //On ouvre le fichier de preference
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);

        //On prend le nombre d'éléments de la liste de courses (par défaut 0)
        String[] loadText = new String[user.getInt("NOMBRE_BUDGETS",0)];

        //On rempli notre tableau de string avec les éléments des préférences de notre utilisateur
        for(int i=0; i < loadText.length;i++){
            loadText[i] = user.getString("LISTE_DR_"+i,null);
        }

        //On déclare nos variables pour créer nos elements de liste de course
        String tempIntitule;
        Date tempDate;
        double tempMontant;
        boolean tempPeriodicite;
        String tempCategorie;
        String tempTypePeriodicite;

        //Ce tableau permet de récupérer le splitage de la chaine du tableau loadText
        String[] temp;

        for(int i=0; i < loadText.length;i++){
            //Si notre ligne est null on ne fait rien
            if(loadText[i] != null){
                //On split notre chaine de caractère grâce aux ; qu'on a mis à la sauvegarde
                temp = loadText[i].split(";");

                //On récupère nos variables
                tempIntitule = temp[0];
                tempDate = toDate(temp[1]);
                tempMontant = Double.parseDouble(temp[2]);
                tempPeriodicite = Boolean.parseBoolean(temp[3]);
                tempTypePeriodicite = temp[4];
                tempCategorie = temp[5];

                //On ajoute notre produit à l'arrayList
                liste.add(new donneesBudget(tempIntitule,tempDate,tempMontant,tempPeriodicite,tempTypePeriodicite,tempCategorie));
            }

        }
    }
}
