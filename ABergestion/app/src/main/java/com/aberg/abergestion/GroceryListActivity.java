package com.aberg.abergestion;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;

import android.widget.AdapterView;

import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Mickael on 23/01/2018.
 */



public class GroceryListActivity extends AppCompatActivity {

    private Button back;
    private ListView stockListView;
    private String[] dataTri = {"Trier par :","Noms","Catégories","Quantités"};
    private ArrayList <Product> al_groceryList;
    private Product p;
    private Product p2;
    private Product p3;
    private Button add;
    private Spinner spnSort;
    private ArrayAdapter<String> adapt;

    private String tri;

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ABergestion";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        //On charge notre liste de course
        al_groceryList = new ArrayList<>();
        loadGroceryList(al_groceryList);

        nameSort(al_groceryList);

        back = findViewById(R.id.button_back);
        add = findViewById(R.id.button_add);
        stockListView = (ListView) findViewById(R.id.listViewStock);

        spnSort= findViewById(R.id.spinner_sort);

        add.setOnClickListener(BtnAdd);
        back.setOnClickListener(BtnBack);
        stockListView.setOnItemClickListener(ClicRow);


        adapt = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataTri);
        spnSort.setAdapter(adapt);

        spnSort.setOnItemSelectedListener(spinnerSort);



        showListView();

    }

    private void showListView() {
        // Définition des colonnes
        // SimpleCursorAdapter a besoin obligatoirement d'un ID nommé "_id"
        // Ensuite on met le nombre de colonnes que l'on veut
        String[] colums = new String[]{"_id", "col1", "col2"};

        // Définition des données du tableau
        // On affecte au matrixCursor les colonnes que l'on vient de créer
        // On démarre le MatrixCursor
        MatrixCursor matrixCursor = new MatrixCursor(colums);
        startManagingCursor(matrixCursor);

        //On ajoute des objets au MatrixCursor
        for (int i = 0; i < al_groceryList.size(); i++) {
            matrixCursor.addRow(new Object[]{i, al_groceryList.get(i).getName(), al_groceryList.get(i).getForm() + ": " +al_groceryList.get(i).getQuantity()});
        }


        // matrixCursor.addRow(new Object[]{0, al_groceryList.get(0).getName(), al_groceryList.get(0).getQuantity()+" "+al_groceryList.get(0).getForm()});
        // matrixCursor.addRow(new Object[]{2, "col1:ligne2", "col2:ligne2"});

        // On prend les données des colonnes 1 et 2 ...
        String[] from = new String[]{"col1", "col2"};
        // ... pour les placer dans les TextView définis dans "row_item.xml"
        int[] to = new int[]{R.id.textView_Col1, R.id.textView_Col2};

        // On crée l'objet SimpleCursorAdapter
        // On met le context (this ici), ensuite la définition des lignes de la liste, ensuite on ajoute les lignes, on définit les colonnes, on les lient aux textView, on met le flag à 0
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_item, matrixCursor, from, to, 0);



        // On lie la liste avec l'adapter
        stockListView.setAdapter(adapter);

        stockListView.setOnItemClickListener(ClicRow);
    }

    private View.OnClickListener BtnBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GroceryListActivity.this, StockActivity.class);
            startActivity(intent);
            GroceryListActivity.this.finish();
        }

    };

    private View.OnClickListener BtnAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupAddProduct();
        }

    };


    private ListView.OnItemClickListener ClicRow = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
            popupDeleteProduct(pos);
        }
    };



    private void popupAddProduct() {

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(GroceryListActivity.this);
        final View alertDialogView = factory.inflate(R.layout.alertdialog_add_product, null);

        //Création de l'AlertDialog
        AlertDialog.Builder popup = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        popup.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        popup.setTitle(R.string.alertDialog_addProduct);

        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //popup.setIcon(android.R.drawable.ic_dialog_alert);

        //On affecte un bouton "Ajouter" à notre AlertDialog et on lui affecte un évènement
        popup.setPositiveButton(R.string.text_add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
                EditText productName = alertDialogView.findViewById(R.id.editText_popupProductName);
                EditText productQuantity = alertDialogView.findViewById(R.id.numericText_popupQuantity);
                EditText productType = alertDialogView.findViewById(R.id.editText_popupProductType);


                String name = productName.getText().toString();
                String quantity = productQuantity.getText().toString();
                String type = productType.getText().toString();

                if (isEmpty(name) || isEmpty(quantity) || isEmpty(type)) {
                    alertDialog(getString(R.string.AlertDialog_champsrempli));
                } else {
                    int temp = Integer.parseInt(quantity);



                    al_groceryList.add(new Product(name,null,temp,null,null,type));
                    saveGroceryList(al_groceryList);

                    showListView();


                    //On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
                    Toast.makeText(GroceryListActivity.this, R.string.toast_productAdded, Toast.LENGTH_SHORT).show();

                }

            }
        });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        popup.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //On ferme la popup
            }
        });
        popup.show();
    }

    private void popupDeleteProduct(final int position){
        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(GroceryListActivity.this);
        final View alertDialogView = factory.inflate(R.layout.alertdialog_delete_product, null);

        //Création de l'AlertDialog
        AlertDialog.Builder popup = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        popup.setView(alertDialogView);



        //On récupère les EditText de notre popup
        final EditText productName = alertDialogView.findViewById(R.id.editText_popupDelProductName);
        final EditText productQuantity = alertDialogView.findViewById(R.id.numericText_popupDelQuantity);
        final EditText productType = alertDialogView.findViewById(R.id.editText_popupDelProductType);

        //On met un text dans ces EditTexts
        productName.setText(al_groceryList.get(position).getName());
        productQuantity.setText(Integer.toString(al_groceryList.get(position).getQuantity()));
        productType.setText(al_groceryList.get(position).getForm());

        //On donne un titre à l'AlertDialog
        popup.setTitle("Modifier/Supprimer produit");


        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        popup.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                al_groceryList.remove(position);

                saveGroceryList(al_groceryList);
                showListView();
                Toast.makeText(GroceryListActivity.this, "Produit supprimé", Toast.LENGTH_SHORT).show();
            } });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        popup.setPositiveButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //On ferme la popup
            } });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        popup.setNeutralButton("Modifier", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String prodName = productName.getText().toString();
                String prodQuantity = productQuantity.getText().toString();
                String prodType = productType.getText().toString();

                if(isEmpty(prodName) || isEmpty(prodQuantity) || isEmpty(prodType)){
                    alertDialog(getString(R.string.AlertDialog_champsrempli));
                }
                else{
                    int temp = Integer.parseInt(prodQuantity);

                    al_groceryList.get(position).setName(prodName);
                    al_groceryList.get(position).setQuantity(temp);
                    al_groceryList.get(position).setForm(prodType);

                    saveGroceryList(al_groceryList);

                    showListView();

                    Toast.makeText(GroceryListActivity.this, "Produit modifié", Toast.LENGTH_SHORT).show();
                }

            } });
        popup.show();
    }

    private void nameSort(ArrayList<Product> p) {
        // Tri par bulles.
        Product temp;
        boolean flag = true;

        while (flag) {
            flag = false;

            for (int i = 0; i < p.size() - 1; i++) {
                if (p.get(i).getName().compareTo(p.get(i + 1).getName()) > 1)
                {
                    p.set(i, p.set(i + 1, p.get(i)));

                    flag = true;
                }
            }
        }


    }

    private void quantitySort(ArrayList<Product> p) {
        Product temp;
        boolean flag = true;

        while (flag) {
            flag = false;

            for (int i = 0; i < p.size() - 1; i++) {
                if (p.get(i).getQuantity() > p.get(i + 1).getQuantity()) {
                    p.set(i, p.set(i + 1, p.get(i)));

                    flag = true;
                }
            }
        }

    }

    private void categorySort (ArrayList <Product> p) {
        // Tri par bulles.
        Product temp;                                    // Valeur pour la permutation.
        boolean flag = true;                            // True si il y a eu une permutation lors du passage.

        while (flag) {                                // Tant qu'il y a eu au moins une permutation de nombre alors le tableau n'est pas trié donc on continue.
            flag = false;                            // On informe que pour le moment aucune permutation n'as eu lieu.

            for (int i = 0; i < p.size() - 1; i++) {    // Pour chaque case du tableau - 1.
                if (p.get(i).getCategory().compareTo(p.get(i + 1).getCategory()) > 1)            // Si la case suivante est supérieur à la case actuelle.
                {
                    p.set(i, p.set(i + 1, p.get(i)));

                    flag = true;                    // On informe qu'une permutation à eu lieu.

                }
            }
        }
    }


    //Fonction pour afficher un pop up avec un message et un bouton "Ok"
    private void alertDialog(String message){

        //On crée la fenetre
        AlertDialog bugAlert = new AlertDialog.Builder(this).create();

        //On applique le message en paramètre
        bugAlert.setMessage(message);

        //On ajoute le bouton positif 'Ok' qui ferme juste la pop up
        bugAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alertDialog_ok), new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //On affiche la pop up
        bugAlert.show();
    }

    //Fonction pour savoir si un String est vide
    private Boolean isEmpty(String s) {
        if (s.length() != 0) {
            return false;
        }
        return true;
    }



    //private void initDataSort(){

    private void saveGroceryList(ArrayList<Product> liste){
        //On récupère la taille de la liste puis on crée un tableau de string aussi grand
        int tailleArray = liste.size();
        String [] savedText = new String[tailleArray];

        //On déclare la variable temporaire pour chaque ligne
        String temp;

        //On parcourt le tableau pour y ajouter chaque element
        for(int i=0; i < tailleArray; i++){
            //Ici on écrit un élément et on sépare deux éléments avec des points virgule
            temp = liste.get(i).getName()+";"/*+liste.get(i).getCategory()+";"*/+liste.get(i).getQuantity()+";"+liste.get(i).getForm();

            //On affecte cette chaine au tableau sauvegarder
            savedText[i] = temp;
        }


        //On ouvre l'écriture dans notre fichier utilisateur
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = user.edit();


//            dataTri.add("Trier par: Noms");
//            dataTri.add("Trier par:Categories");
//            dataTri.add("Trier par:Quantités");

        //On indique la taille de notre liste de course
        editor.putInt("ELEMENTS_LISTE_COURSE", liste.size());

        //On ajoute tous les éléments à cette liste
        for(int i=0; i<liste.size();i++){
            editor.putString("LISTE_COURSE_"+i,savedText[i]);
        }

        //On met à jour le fichier
        editor.commit();
    }

    private void loadGroceryList(ArrayList<Product> liste){
        //On ouvre le fichier de preference
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = user.edit();

        //On prend le nombre d'éléments de la liste de courses (par défaut 0)
        String[] loadText = new String[user.getInt("ELEMENTS_LISTE_COURSE",0)];

        //On rempli notre tableau de string avec les éléments des préférences de notre utilisateur
        for(int i=0; i < loadText.length;i++){
            loadText[i] = user.getString("LISTE_COURSE_"+i,null);

            //On supprime la ligne pour ne pas avoir de problème plus tard
            editor.remove("LISTE_COURSE_"+i);
        }

        //On déclare nos variables pour créer nos elements de liste de course
        String tempName;
        String tempCategory;
        int tempQuantity;
        String tempForm;

        //Ce tableau permet de récupérer le splitage de la chaine du tableau loadText
        String[] temp;

        for(int i=0; i < loadText.length;i++){
            //Si notre ligne est null on ne fait rien
            if(loadText[i] != null){
                //On split notre chaine de caractère grâce aux ; qu'on a mis à la sauvegarde
                temp = loadText[i].split(";");

                //On récupère nos variables
                tempName = temp[0];
                //tempCategory = temp[1];
                tempQuantity = Integer.parseInt(temp[1]);
                tempForm = temp[2];

                //On ajoute notre produit à l'arrayList
                liste.add(new Product(tempName,null,tempQuantity,null,null,tempForm));
            }

        }

    }

    private Spinner.OnItemSelectedListener spinnerSort = new Spinner.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> av, View v, int pos, long l) {

            String selected = (spnSort.getSelectedItem().toString());

            Log.i("test", "onClick: sort "+selected);

           switch (selected) {
               case "Noms":
                   nameSort(al_groceryList);
                   break;
               case "Catégories":
                   //categorySort(al_groceryList);
                   break;
               case "Quantités":
                   quantitySort(al_groceryList);
                   break;
               default:
                   break;


           }
           GroceryListActivity.this.showListView();
        }

        @Override
        public void onNothingSelected(AdapterView<?> v){

        }

    };


}







