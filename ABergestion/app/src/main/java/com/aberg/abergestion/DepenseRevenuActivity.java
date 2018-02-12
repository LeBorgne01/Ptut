package com.aberg.abergestion;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by louis on 23/01/2018.
 */

public class DepenseRevenuActivity extends AppCompatActivity implements Serializable {

    private ArrayAdapter<String> adapt1,adapt2,adapt3,adaptCat;
    private ArrayList<String> dataJour,dataAnnee,dataMois;
    private Budget budget;
    private ListView mListView;
    private ArrayList<String> datas;
    private donneesBudget test1,test2,test3;
    private ArrayList<donneesBudget> listDR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_revenu);
        Intent intent = getIntent();
        listDR = new ArrayList<>();
        loadListDR(listDR);
        mListView = (ListView) findViewById(R.id.lv);
        this.showListView();
        mListView.setOnItemClickListener(ClicRow);
    }

    public void showListView(){
        budget = new Budget(listDR);
        ArrayList<String> montantDatas = budget.getMontantData();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DepenseRevenuActivity.this, android.R.layout.simple_list_item_1, montantDatas);
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
                tempDate = toDate(temp[1]);
                tempMontant = Double.parseDouble(temp[2]);
                tempPeriodicite = Boolean.parseBoolean(temp[3]);
                tempCategorie = temp[4];

                //On ajoute notre produit à l'arrayList
                liste.add(new donneesBudget(tempIntitule,tempDate,tempMontant,tempPeriodicite,tempCategorie));
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(DepenseRevenuActivity.this,BudgetActivity.class);
            startActivity(intent);
            DepenseRevenuActivity.this.finish();
            return true;
        }
        return false;

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

     private ListView.OnItemClickListener ClicRow = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
            popupAfficherDR(pos);
        }
    };

    private void popupAfficherDR(final int pos){
        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(DepenseRevenuActivity.this);
        final View alertDialogView = factory.inflate(R.layout.alertdialog_afficher_detail_dr, null);

        //Création de l'AlertDialog
        AlertDialog.Builder popup = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        popup.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        popup.setTitle(R.string.alertDialog_afficher_detail_dr);

        TextView textView_intitule = (TextView)alertDialogView.findViewById(R.id.textView_intitule);
        TextView textView_montant = (TextView)alertDialogView.findViewById(R.id.textView_montant);
        TextView textView_date = (TextView)alertDialogView.findViewById(R.id.textView_Date);
        TextView textView_categorie = (TextView)alertDialogView.findViewById(R.id.textView_categorie);
        TextView textView_periodicite = (TextView)alertDialogView.findViewById(R.id.textView_periodicite);

        TextView textView_contenu_intitule = (TextView)alertDialogView.findViewById(R.id.textView_contenu_intitule);
        textView_contenu_intitule.setText(listDR.get(pos).getIntitule());
        TextView textView_contenu_montant = (TextView)alertDialogView.findViewById(R.id.textView_contenu_montant);
        textView_contenu_montant.setText(String.valueOf(listDR.get(pos).getMontant()));
        TextView textView_contenu_date = (TextView)alertDialogView.findViewById(R.id.textView_contenu_date);
        textView_contenu_date.setText(listDR.get(pos).getDate().dateToString());
        TextView textView_contenu_categorie = (TextView)alertDialogView.findViewById(R.id.textView_contenu_categorie);
        textView_contenu_categorie.setText(listDR.get(pos).getCategorie());
        TextView textView_contenu_periodicite = (TextView)alertDialogView.findViewById(R.id.textView_contenu_periodicite);
        if(listDR.get(pos).isPeriodicite()) {
            textView_contenu_periodicite.setText("Oui");
        }
        else textView_contenu_periodicite.setText("Non");


        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        popup.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //On ferme la popup
            } });

        popup.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                boolean tmp;
                if(listDR.get(pos).getMontant() < 0) {
                   tmp = false;
                }
                else tmp = true;
                listDR.remove(pos);
                saveListDR(listDR);
                showListView();
                if(!tmp) {
                    Toast.makeText(DepenseRevenuActivity.this, "Dépense supprimée", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(DepenseRevenuActivity.this, "Revenu supprimé", Toast.LENGTH_SHORT).show();
            } });

        popup.setNeutralButton("Modifier", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                LayoutInflater factory = LayoutInflater.from(DepenseRevenuActivity.this);

                String[] listCatR ={"Salaire","Jeu d'argent","Investissement"};
                final String[] listCatD= {"Jeu","Nourriture&Hygiène","Restaurant","Sortie","Shopping"};

                if(listDR.get(pos).getMontant() < 0) {
                    adaptCat = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listCatD);
                }
                else {
                    adaptCat = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, listCatR);
                }


                    initDataJour();
                    initDataMois();
                    initDataAnnee();

                    adapt1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataJour);
                    adapt2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataAnnee);
                    adapt3 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataMois);


                    final View alertDialogView_modifier_dr = factory.inflate(R.layout.activity_add_depense, null);


                    //Création de l'AlertDialog
                    AlertDialog.Builder popup2 = new AlertDialog.Builder(alertDialogView.getContext());

                    //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
                    popup2.setView(alertDialogView_modifier_dr);

                    //On donne un titre à l'AlertDialog
                    popup2.setTitle(R.string.alertDialog_afficher_detail_dr);

                    Button button_valider = (Button)alertDialogView_modifier_dr.findViewById(R.id.button_valider);
                    button_valider.setVisibility(View.INVISIBLE);
                    final EditText editText_intitule = (EditText) alertDialogView_modifier_dr.findViewById(R.id.editText_intitule);
                    editText_intitule.setText(listDR.get(pos).getIntitule());
                    final EditText editText_montant = (EditText) alertDialogView_modifier_dr.findViewById(R.id.editText_montant);
                    editText_montant.setText(String.valueOf(listDR.get(pos).getMontant()));
                    final Spinner spinner_jour = (Spinner) alertDialogView_modifier_dr.findViewById(R.id.spinner_jour);
                    spinner_jour.setAdapter(adapt1);
                    spinner_jour.setSelection(listDR.get(pos).getDate().getJour() - 1);
                    final Spinner spinner_mois = (Spinner) alertDialogView_modifier_dr.findViewById(R.id.spinner_mois);
                    spinner_mois.setAdapter(adapt3);
                    spinner_mois.setSelection(listDR.get(pos).getDate().getMois() - 1);
                    final Spinner spinner_annee = (Spinner) alertDialogView_modifier_dr.findViewById(R.id.spinner_annee);
                    spinner_annee.setAdapter(adapt2);
                    spinner_annee.setSelection(listDR.get(pos).getDate().getAnnee() - 2019);
                    final Spinner spinner_cat = (Spinner) alertDialogView_modifier_dr.findViewById(R.id.spinner_categorie);
                    spinner_cat.setAdapter(adaptCat);
                    int positionSpinnerCat = 0;
                    String categorieChoisi = listDR.get(pos).getCategorie();
                    switch(categorieChoisi){
                        case "Salaire":
                            positionSpinnerCat = 0;
                            break;
                        case "Jeu d'argent":
                            positionSpinnerCat = 1;
                            break;
                        case "Investissement":
                            positionSpinnerCat = 2;
                            break;
                        case "Jeu":
                            positionSpinnerCat = 0;
                            break;
                        case "Nourriture&Hygiène":
                            positionSpinnerCat = 1;
                            break;
                        case "Restaurant":
                            positionSpinnerCat = 2;
                            break;
                        case "Sortie":
                            positionSpinnerCat = 3;
                            break;
                        case "Shopping":
                            positionSpinnerCat = 4;
                            break;
                    }
                    spinner_cat.setSelection(positionSpinnerCat);
                    final Switch switch_perio = (Switch) alertDialogView_modifier_dr.findViewById(R.id.switch_periodicite);
                    switch_perio.setChecked(listDR.get(pos).isPeriodicite());

                    popup2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Date dtmp = new Date(Integer.parseInt(spinner_jour.getSelectedItem().toString()),Integer.parseInt(spinner_mois.getSelectedItem().toString()),Integer.parseInt(spinner_annee.getSelectedItem().toString()));
                        donneesBudget tmp = new donneesBudget(editText_intitule.getText().toString(),dtmp,Double.parseDouble(editText_montant.getText().toString()),switch_perio.isChecked(),spinner_cat.getSelectedItem().toString());
                        listDR.remove(listDR.get(pos));
                        listDR.add(tmp);
                        //System.out.println("pos: "+ listDR.get(pos).getMontant());
                        saveListDR(listDR);
                        showListView();
                    } });

                    popup2.show();
            } });

        popup.show();
        }

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

}

