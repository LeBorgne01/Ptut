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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private RadioButton rb_mensuel_popup, rb_annuel_popup, rb_hebdo_popup,rb_trimestriel_popup;

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

        private Boolean isEmpty(String s){ //Fonction qui vérifie si un champ est vide
            if(s.length() != 0){ //On test la taille de la chaîne entré
                return false; //Si elle est égale à 0, on retourne false
            }
            return true; //Sinon, on retourne true
        }

        private void alertDialog(String message){ //Popup
            //On crée la fenetre
            AlertDialog bugAlert = new AlertDialog.Builder(AddRevenuActivity.this).create();

            //On applique le message en paramètre
            bugAlert.setMessage(message);

            //On ajoute le bouton positif 'Ok' qui ferme juste la pop up
            bugAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alertDialog_ok), new AlertDialog.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //ça enlève la Popup
                }
            });

            //On affiche la pop up
            bugAlert.show();
        }

        @Override
        public void onClick(View v){ //Clic sur le bouton Valider
            if(isEmpty(eTIntitule.getText().toString()) || isEmpty(eTMontant.getText().toString())){ //On vérifie qu'aucun champ et vide
                alertDialog(getString(R.string.AlertDialog_champsrempli)); //Si ce n'est pas le cas on afficher une popup d'alerte
            }

            else { //Sinon
                boolean contenuSwitchPerio = switch_periodicite.isChecked();
                if(contenuSwitchPerio == true){
                    afficherPopUpPerio();
                }
                else{
                    String contenuPerio = "null";
                    System.out.println(contenuPerio);
                    ///On va venir mettre toutes les informations rentrées par l'utilisateur dans des variables :
                    String contenuIntitule = eTIntitule.getText().toString();
                    double contenuMontant = Double.parseDouble(eTMontant.getText().toString());
                    int contenuJour = Integer.parseInt(spJ.getSelectedItem().toString());
                    int contenuMois = Integer.parseInt(spM.getSelectedItem().toString());
                    int contenuAnnee = Integer.parseInt(spA.getSelectedItem().toString());
                    String contenuCategorie = spCat.getSelectedItem().toString();
                    //On créé une date grâce au contenu des différents spinners
                    Date d = new Date(contenuJour,contenuMois,contenuAnnee);
                    //On crée une nouvelles donnée (Dépense ou Revenus) avec les informations
                    donneesBudget data= new donneesBudget(contenuIntitule,d,contenuMontant,contenuSwitchPerio,contenuPerio,contenuCategorie);
                    //data.displayDonneesBudget();
                    listDR.add(data); //On ajoute cette données à notre liste de données
                    saveListDR(listDR); //On sauvegarde la liste mise à jour
                    Intent intent = new Intent(AddRevenuActivity.this,DepenseRevenuActivity.class); //On créer une nouvelle intent vers la vue qui afficher la liste
                    intent.putExtra("listDR",listDR); //On envoie notre liste vers cette vue
                    startActivity(intent); //On lance a nouvelle activité
                    AddRevenuActivity.this.finish(); //Et on la termine
                }
            }
        }
    };

    public Date toDate(String s){
        String[] tabS = s.split("/");
        Date d = new Date(Integer.parseInt(tabS[0]),Integer.parseInt(tabS[1]),Integer.parseInt(tabS[2]));
        System.out.println(d);
        return d;
    }

    private void saveListDR(ArrayList<donneesBudget> liste){ //Fonction qui permet la sauvegarde de la liste dans un fichier .txt
        //On récupère la taille de la liste puis on crée un tableau de string aussi grand
        int tailleArray = liste.size();
        String [] savedText = new String[tailleArray];

        //On déclare la variable temporaire pour chaque ligne
        String temp;

        //On parcourt le tableau pour y ajouter chaque element
        for(int i=0; i < tailleArray; i++){
            //Ici on écrit un élément et on sépare deux éléments avec des points virgule
            temp = liste.get(i).getIntitule()+";"+liste.get(i).getDate().dateToString()+";"+liste.get(i).getMontant()+";"+liste.get(i).isPeriodicite()+";"+liste.get(i).getTypePeriodicite()+";"+liste.get(i).getCategorie();
            System.out.println(temp);
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
                AddRevenuActivity.this.finish();
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

    public void afficherPopUpPerio(){
        LayoutInflater factory = LayoutInflater.from(AddRevenuActivity.this);
        final View alertDialogView = factory.inflate(R.layout.alertdialog_afficher_choix_perio, null);

        //Création de l'AlertDialog
        AlertDialog.Builder popup = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        popup.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        popup.setTitle(R.string.alertdialog_choix_perio);

        rb_annuel_popup=(RadioButton)alertDialogView.findViewById(R.id.radioButton_annuel);
        rb_mensuel_popup=(RadioButton)alertDialogView.findViewById(R.id.radioButton_mensuel);
        rb_trimestriel_popup=(RadioButton)alertDialogView.findViewById(R.id.radioButton_trimestriel);
        rb_hebdo_popup=(RadioButton)alertDialogView.findViewById(R.id.radioButton_hebdo);

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        popup.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                boolean contenuSwitchPerio = switch_periodicite.isChecked();
                String contenuPerio = new String();
                if(rb_mensuel_popup.isChecked()){
                    contenuPerio = rb_mensuel_popup.getText().toString();
                }
                else if(rb_annuel_popup.isChecked()){
                    contenuPerio = rb_annuel_popup.getText().toString();
                }
                else if(rb_hebdo_popup.isChecked()){
                    contenuPerio = rb_hebdo_popup.getText().toString();
                }
                else if(rb_trimestriel_popup.isChecked()){
                    contenuPerio = rb_trimestriel_popup.getText().toString();
                }
                ///On va venir mettre toutes les informations rentrées par l'utilisateur dans des variables :
                String contenuIntitule = eTIntitule.getText().toString();
                double contenuMontant = Double.parseDouble(eTMontant.getText().toString());
                int contenuJour = Integer.parseInt(spJ.getSelectedItem().toString());
                int contenuMois = Integer.parseInt(spM.getSelectedItem().toString());
                int contenuAnnee = Integer.parseInt(spA.getSelectedItem().toString());
                String contenuCategorie = spCat.getSelectedItem().toString();
                //On créé une date grâce au contenu des différents spinners
                Date d = new Date(contenuJour,contenuMois,contenuAnnee);
                //On crée une nouvelles donnée (Dépense ou Revenus) avec les informations
                donneesBudget data= new donneesBudget(contenuIntitule,d,contenuMontant,contenuSwitchPerio,contenuPerio,contenuCategorie);
                //data.displayDonneesBudget();
                listDR.add(data); //On ajoute cette données à notre liste de données
                saveListDR(listDR); //On sauvegarde la liste mise à jour
                Intent intent = new Intent(AddRevenuActivity.this,DepenseRevenuActivity.class); //On créer une nouvelle intent vers la vue qui afficher la liste
                intent.putExtra("listDR",listDR); //On envoie notre liste vers cette vue
                startActivity(intent); //On lance a nouvelle activité
                AddRevenuActivity.this.finish(); //Et on la termine
            } });

        popup.show();
    }
}