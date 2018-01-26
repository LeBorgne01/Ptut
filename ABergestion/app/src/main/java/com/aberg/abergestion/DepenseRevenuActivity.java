package com.aberg.abergestion;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_revenu);
        Intent intent = getIntent();
        listDR = new ArrayList<>();
        loadListDR(listDR);
        mListView = (ListView) findViewById(R.id.lv);
        budget = new Budget(listDR);
        ArrayList<String> montantDatas = budget.getMontantData();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DepenseRevenuActivity.this, android.R.layout.simple_list_item_1, montantDatas);
        mListView.setCacheColorHint(13);
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
            return true;
        }
        return false;

    }

    private void popupAddProduct(){
        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(DepenseRevenuActivity.this);
        final View alertDialogView = factory.inflate(R.layout.alertdialog_add_product, null);

        //Création de l'AlertDialog
        AlertDialog.Builder popup = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        popup.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        popup.setTitle(R.string.alertDialog_addProduct);

        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //popup.setIcon(android.R.drawable.ic_dialog_alert);

        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        popup.setPositiveButton(R.string.text_add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
                EditText productName = alertDialogView.findViewById(R.id.editText_popupProductName);
                EditText productQuantity = alertDialogView.findViewById(R.id.numericText_popupQuantity);
                EditText productType = alertDialogView.findViewById(R.id.editText_popupProductType);



                String name = productName.getText().toString();
                String quantity = productQuantity.getText().toString();
                String type = productType.getText().toString();

                if(isEmpty(name) || isEmpty(quantity) || isEmpty(type)){
                    alertDialog(getString(R.string.AlertDialog_champsrempli));
                }
                else{
                    int temp = Integer.parseInt(quantity);
                    al_groceryList.add(new Product(name,null,temp,null,null,type));
                    saveGroceryList(al_groceryList);
                    showListView();

                    //On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
                    Toast.makeText(GroceryListActivity.this, R.string.toast_productAdded, Toast.LENGTH_SHORT).show();

                }



            } });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        popup.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //On ferme la popup
            } });
        popup.show();
    }
}
