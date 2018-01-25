package com.aberg.abergestion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by louis on 23/01/2018.
 */

public class DepenseRevenuActivity extends AppCompatActivity implements Serializable {

    private Budget budget;
    private ListView mListView;
    private ArrayList<String> datas;
    private donneesBudget test1,test2,test3;
    private ArrayList<donneesBudget> listDR;

    private ArrayList<donneesBudget> initAL(){
        Date d = new Date(25,07,1998);
        listDR = new ArrayList<donneesBudget>();
        test1 = new donneesBudget("test1",d,19.2,true);
        test2 = new donneesBudget("test2",d,152.25,false);
        test3 = new donneesBudget("test3",d,2.07,true);
        listDR.add(test1);
        listDR.add(test2);
        listDR.add(test3);
        return listDR;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_revenu);
        Intent intent = getIntent();
        listDR = new ArrayList<>();
        loadListDR(listDR);
        mListView = (ListView) findViewById(R.id.lv);
        budget = new Budget(listDR);
        ArrayList<String> stringDatas = budget.getIntituleData();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DepenseRevenuActivity.this, android.R.layout.simple_list_item_1, stringDatas);
        mListView.setAdapter(adapter);
    }

    public Date toDate(String s){
        String[] tabS = s.split("/");
        Date d = new Date(Integer.parseInt(tabS[0]),Integer.parseInt(tabS[1]),Integer.parseInt(tabS[2]));
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

                //On ajoute notre produit à l'arrayList
                liste.add(new donneesBudget(tempIntitule,tempDate,tempMontant,tempPeriodicite));
            }

        }
    }
}
