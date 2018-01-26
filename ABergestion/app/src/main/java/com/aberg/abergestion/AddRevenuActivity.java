package com.aberg.abergestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by louis on 23/01/2018.
 */

public class AddRevenuActivity extends AppCompatActivity implements Serializable {
    private Spinner spJ,spM,spA,spCat;
    private EditText eTIntitule,eTMontant;
    private ArrayAdapter<String> adapt1,adapt2,adapt3,adaptCat;
    private ArrayList<String> dataJour,dataAnnee,dataMois;
    private Switch switch_periodicite;
    private Button button_valider;
    private ArrayList<donneesBudget> listDR;
    private String[] listCat ={"Salaire","Jeu","Investissement"};

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ABergestion";

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
        listDR = new ArrayList<>();
        loadListDR(listDR);
        setContentView(R.layout.activity_add_depense);

        /*if(listDR != null){
            System.out.println(listDR.size());
        }
        else System.out.println("ok");*/
        initDataMois();
        initDataJour();
        initDataAnnee();
        button_valider = (Button)findViewById(R.id.button_valider);
        button_valider.setOnClickListener(BtnValider);
        switch_periodicite=(Switch)findViewById(R.id.switch_periodicite);
        spJ = (Spinner)findViewById(R.id.spinner_jour);
        spM = (Spinner)findViewById(R.id.spinner_mois);
        spA = (Spinner)findViewById(R.id.spinner_annee);
        spCat = (Spinner)findViewById(R.id.spinner_categorie);
        eTIntitule=(EditText)findViewById(R.id.editText_intitule);
        eTMontant=(EditText)findViewById(R.id.editText_montant);
        adapt1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataJour);
        adapt2= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataAnnee);
        adapt3 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataMois);
        adaptCat = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listCat);
        spJ.setAdapter(adapt1);
        spM.setAdapter(adapt3);
        spA.setAdapter(adapt2);
        spCat.setAdapter(adaptCat);
    }

    private View.OnClickListener BtnValider = new View.OnClickListener(){

        private Boolean isEmpty(String s){
            if(s.length() != 0){
                return false;
            }
            return true;
        }

        private void alertDialog(String message){
            //On crée la fenetre
            AlertDialog bugAlert = new AlertDialog.Builder(AddRevenuActivity.this).create();

            //On applique le message en paramètre
            bugAlert.setMessage(message);

            //On ajoute le bouton positif 'Ok' qui ferme juste la pop up
            bugAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alertDialog_ok), new AlertDialog.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });

            //On affiche la pop up
            bugAlert.show();
        }

        @Override
        public void onClick(View v){
            if(isEmpty(eTIntitule.getText().toString()) || isEmpty(eTMontant.getText().toString())){
                alertDialog(getString(R.string.AlertDialog_champsrempli));
            }
            else {
                String contenuIntitule = eTIntitule.getText().toString();
                double contenuMontant = Double.parseDouble(eTMontant.getText().toString());
                int contenuJour = Integer.parseInt(spJ.getSelectedItem().toString());
                int contenuMois = Integer.parseInt(spM.getSelectedItem().toString());
                int contenuAnnee = Integer.parseInt(spA.getSelectedItem().toString());
                boolean contenuSwitchPerio = switch_periodicite.isChecked();
                String contenuCategorie = spCat.getSelectedItem().toString();
                Date d = new Date(contenuJour,contenuMois,contenuAnnee);
                donneesBudget data= new donneesBudget(contenuIntitule,d,contenuMontant,contenuSwitchPerio,contenuCategorie);
                listDR.add(data);
                saveListDR(listDR);
                Intent intent = new Intent(AddRevenuActivity.this,DepenseRevenuActivity.class);
                intent.putExtra("listDR",listDR);
                startActivity(intent);
                AddRevenuActivity.this.finish();
            }
        }
    };

    public Date toDate(String s){
        String[] tabS = s.split("/");
        Date d = new Date(Integer.parseInt(tabS[0]),Integer.parseInt(tabS[1]),Integer.parseInt(tabS[2]));
        System.out.println(d);
        return d;
    }

    private void saveListDR(ArrayList<donneesBudget> liste){
        //On récupère la taille de la liste puis on crée un tableau de string aussi grand
        int tailleArray = liste.size();
        String [] savedText = new String[tailleArray];

        //On déclare la variable temporaire pour chaque ligne
        String temp;

        //On parcourt le tableau pour y ajouter chaque element
        for(int i=0; i < tailleArray; i++){
            //Ici on écrit un élément et on sépare deux éléments avec des points virgule
            temp = liste.get(i).getIntitule()+";"+liste.get(i).getDate().dateToString()+";"+liste.get(i).getMontant()+";"+liste.get(i).isPeriodicite()+";"+liste.get(i).getCategorie();

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
                Intent intent = new Intent(AddRevenuActivity.this,BudgetActivity.class);
            startActivity(intent);
            return true;
        }
        return false;

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

        //Ce tableau permet de récupérer le splitage de la chaine du tableau loadText
        String[] temp;

        for(int i=0; i < loadText.length;i++){
            //Si notre ligne est null on ne fait rien
            if(loadText[i] != null){
                //On split notre chaine de caractère grâce aux ; qu'on a mis à la sauvegarde
                temp = loadText[i].split(";");

                //On récupère nos variables
                tempIntitule = temp[0];
                System.out.println(tempIntitule+"\n");
                tempDate = new Date(0,0,0);
                System.out.println(temp[1]);
                tempMontant = Double.parseDouble(temp[2]);
                tempPeriodicite = Boolean.parseBoolean(temp[3]);
                tempCategorie = temp[4];

                //On ajoute notre produit à l'arrayList
                liste.add(new donneesBudget(tempIntitule,tempDate,tempMontant,tempPeriodicite,tempCategorie));
            }

        }
    }


}