package com.aberg.abergestion;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TabHost;

import java.util.ArrayList;

public class AfficherStatActivity extends TabActivity {

    private ArrayList<donneesBudget> listDR;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_afficher_stats_main);

        Resources res = getResources();
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec; // reusable tabspec for each tab
        Intent intent;

        intent = new Intent(this, AfficherStatsNumActivity.class);
        spec = tabHost.newTabSpec("Stat_num").setIndicator("Numérique").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent(this, AddDepenseActivity.class);
        spec = tabHost.newTabSpec("research")
                .setIndicator("Graphique")
                .setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
    }

    public void saveListDR(ArrayList<donneesBudget> liste){ //Fonction qui permet la sauvegarde de la liste dans un fichier .txt
        //On récupère la taille de la liste puis on crée un tableau de string aussi grand
        int tailleArray = liste.size();
        String [] savedText = new String[tailleArray];

        //On déclare la variable temporaire pour chaque ligne
        String temp;

        //On parcourt le tableau pour y ajouter chaque element
        for(int i=0; i < tailleArray; i++){
            //Ici on écrit un élément et on sépare deux éléments avec des points virgule
            temp = liste.get(i).getIntitule()+";"+liste.get(i).getDate().dateToString()+";"+liste.get(i).getMontant()+";"+liste.get(i).isPeriodicite()+";"+liste.get(i).getTypePeriodicite()+";"+liste.get(i).getCategorie();
            //System.out.println(tailleArray);
            //On affecte cette chaine au tableau sauvegarder
            savedText[i] = temp;
        }

        //On ouvre l'écriture dans notre fichier utilisateur
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = user.edit();

        //On indique la taille de notre liste de course
        editor.putInt("NOMBRE_BUDGETS", liste.size());

        //On ajoute tous les éléments à cette liste
        for(int i=0; i<liste.size();i++){
            editor.putString("LISTE_DR_"+i,savedText[i]);
        }

        //On met à jour le fichier
        editor.commit();
    }

    public void loadListDR(ArrayList<donneesBudget> liste){
        //On ouvre le fichier de preference
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);

        //On prend le nombre d'éléments de la liste de courses (par défaut 0)
        String[] loadText = new String[user.getInt("NOMBRE_BUDGETS",0)];
        System.out.println(loadText.length);
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
                //System.out.println(loadText[i]+"\n");
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

    public Date toDate(String s){ //Fonction de conversion d'un string vers une date
        String[] tabS = s.split("/"); //On creér un tableau de String qui va prendre les valeurs comprise entre les "/"
        Date d = new Date(Integer.parseInt(tabS[0]),Integer.parseInt(tabS[1]),Integer.parseInt(tabS[2])); //On creér une date à partir de ce tableau
        return d; //On return la date
    }

}
