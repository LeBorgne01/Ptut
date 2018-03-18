package com.aberg.abergestion;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Mael on 26/01/2018.
 */

public class RecapStockActivity extends AppCompatActivity {

    private Stock stock;
    private Button addProduct;
    private ListView listStock;

    private String[] dataCategory = {"Catégories : ", "Alimentaire", "Hygiène", "Animalier", "Autres"};

    private int productNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap_stock);

        this.stock = new Stock();
        loadStock(this.stock);

        addProduct = findViewById(R.id.button_addProduct);
        listStock = findViewById(R.id.listView_stockProduct);

        addProduct.setOnClickListener(btnAdd);

        showListStock();
    }

    private void showListStock(){
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
        for (int i = 0; i < stock.getStock().size(); i++) {
            matrixCursor.addRow(new Object[]{i, stock.getStock().get(i).getName(), stock.getStock().get(i).getForm() + ": " +stock.getStock().get(i).getQuantity()});
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
        listStock.setAdapter(adapter);

        listStock.setOnItemClickListener(ClicRow);
    }

    private ListView.OnItemClickListener ClicRow = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
            gestionProduct(pos);
        }
    };

    private void loadStock(Stock stock){
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = user.edit();

        String[] loadText = new String[user.getInt("NOMBRE_STOCKS", 0)];



        for(int i=0; i<loadText.length; i++){
            loadText[i] = user.getString("STOCK_"+i, null);

            editor.remove("STOCK_"+i);
        }

        String tempName;
        String tempCategory;
        int tempQuantity;
        String tempPurchaseDate;
        String tempExpirationDate;
        String tempForm;
        int tempNumbrePrevent;

        String[] temp;


        for(int i=0; i<loadText.length; i++){
            temp = loadText[i].split(";");

            tempName = temp[0];
            tempCategory = temp[1];
            tempQuantity = Integer.parseInt(temp[2]);
            tempPurchaseDate = temp[3];
            tempExpirationDate = temp[4];
            tempForm = temp[5];
            tempNumbrePrevent = Integer.parseInt(temp[6]);

            Product prod = new Product(tempName, tempCategory, tempQuantity, tempPurchaseDate, tempExpirationDate, tempForm);
            prod.setNumbrePrevent(tempNumbrePrevent);

            stock.addProduct(prod);
        }
    }

    private void saveStock(Stock stock){
        int tailleStock = stock.getStock().size();

        String[] saveText = new String[tailleStock];

        String temp;

        for(int i=0; i<tailleStock; i++){
            temp = stock.getStock().get(i).getName()+";"+stock.getStock().get(i).getCategory()+";"+stock.getStock().get(i).getQuantity()+";"+stock.getStock().get(i).getPurchaseDate()+";"+stock.getStock().get(i).getExpirationDate()+";"+stock.getStock().get(i).getForm()+";"+stock.getStock().get(i).getNumbrePrevent();

            saveText[i] = temp;
        }

        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = user.edit();

        //On indique la taille de notre liste de course
        editor.putInt("NOMBRE_STOCKS", stock.getStock().size());

        //On ajoute tous les éléments à cette liste
        for(int i=0; i<stock.getStock().size();i++){
            editor.putString("STOCK_"+i, saveText[i]);
        }

        editor.commit();
    }

    private View.OnClickListener btnAdd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            popupAddProduct();
        }
    };

    private void popupAddProduct() {

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(RecapStockActivity.this);
        final View alertDialogView = factory.inflate(R.layout.alertdialog_stockproduct, null);

        //Création de l'AlertDialog
        AlertDialog.Builder popup = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        popup.setView(alertDialogView);

        //On charge le spinner
        final Spinner productCategory = alertDialogView.findViewById(R.id.spinner_categoryAddProduct);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataCategory);
        productCategory.setAdapter(categoryAdapter);

        //On donne un titre à l'AlertDialog
        popup.setTitle(R.string.alertDialog_addProduct);

        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //popup.setIcon(android.R.drawable.ic_dialog_alert);

        //On affecte un bouton "Ajouter" à notre AlertDialog et on lui affecte un évènement
        popup.setPositiveButton(R.string.text_add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
                EditText productName = alertDialogView.findViewById(R.id.editText_nameAddProduct);
                EditText productQuantity = alertDialogView.findViewById(R.id.editText_quantityAddProduct);
                EditText productType = alertDialogView.findViewById(R.id.editText_typeAddProduct);
                EditText productPurchaseDate = alertDialogView.findViewById(R.id.editText_purchaseDateAddProduct);
                EditText productExpirationDate = alertDialogView.findViewById(R.id.editText_expirationDateAddProduct);
                EditText productNumbrePrevent = alertDialogView.findViewById(R.id.editText_numbrePreventAddProduct);
                Spinner productCategory = alertDialogView.findViewById(R.id.spinner_categoryAddProduct);

                String category = productCategory.getSelectedItem().toString();
                String name = productName.getText().toString();
                String quantity = productQuantity.getText().toString();
                String type = productType.getText().toString();
                String purchaseDate = productPurchaseDate.getText().toString();
                String expirationDate = productExpirationDate.getText().toString();
                String tempNumbrePrevent = productNumbrePrevent.getText().toString();

                if (isEmpty(name) || isEmpty(quantity) || isEmpty(type) || isEmpty(purchaseDate) || isEmpty(expirationDate) || isEmpty(tempNumbrePrevent) || category.equals("Catégories : ")) {
                    alertDialog(getString(R.string.AlertDialog_champsrempli));
                }
                else {
                    if(category.equals(getString(R.string.string_category))){
                        alertDialog(getString(R.string.alertDialog_chooseCategory));
                    }
                    else {
                        int temp = Integer.parseInt(quantity);
                        int temp2 = Integer.parseInt(tempNumbrePrevent);

                        Product newProduct = new Product(name,category,temp,purchaseDate,expirationDate,type);

                        newProduct.setNumbrePrevent(temp2);

                        stock.addProduct(newProduct);
                        saveStock(stock);

                        showListStock();

                        //On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
                       // Toast.makeText(GroceryListActivity.this, R.string.toast_productAdded, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        popup.setNegativeButton(R.string.string_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //On ferme la popup
            }
        });
        popup.show();
    }

    //Fonction pour savoir si un String est vide
    private Boolean isEmpty(String s) {
        if (s.length() != 0) {
            return false;
        }
        return true;
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

    private void gestionProduct(final int pos){
        final Product product = this.stock.getStock().get(pos);

        String sentence = product.getName()+'\n'+"Stock : "+product.getQuantity()+" "+product.getForm()+'\n'+"Péremption : "+product.getExpirationDate();


        //On crée la fenetre
        AlertDialog popup = new AlertDialog.Builder(this).create();

        //On applique le message en paramètre
        popup.setMessage(sentence);

        //On ajoute le bouton positif 'Ok' qui ferme juste la pop up
        popup.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alertDialog_ok), new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //On ajoute le bouton negatif qui permet d'ouvrir une popup pour modifier ce produit ou le supprimer
        popup.setButton(AlertDialog.BUTTON_NEGATIVE, "Modifier", new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                popopModify(product, pos);
            }
        });

        popup.setButton(AlertDialog.BUTTON_NEUTRAL, "+/-", new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                popopAddOrSubstract(stock.getStock().get(pos));
                showListStock();
            }
        });


        //On affiche la pop up
        popup.show();
    }

    private void popopModify(final Product product, final int pos){

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(RecapStockActivity.this);
        final View alertDialogView = factory.inflate(R.layout.alertdialog_stockproduct, null);

        //Création de l'AlertDialog
        AlertDialog.Builder popup = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        popup.setView(alertDialogView);

        //On charge le spinner
        final Spinner productCategory = alertDialogView.findViewById(R.id.spinner_categoryAddProduct);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, dataCategory);
        productCategory.setAdapter(categoryAdapter);

        //On donne un titre à l'AlertDialog
        popup.setTitle(R.string.alertDialog_addProduct);

        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //popup.setIcon(android.R.drawable.ic_dialog_alert);

        //Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
        final EditText productName = alertDialogView.findViewById(R.id.editText_nameAddProduct);
        final EditText productQuantity = alertDialogView.findViewById(R.id.editText_quantityAddProduct);
        final EditText productType = alertDialogView.findViewById(R.id.editText_typeAddProduct);
        final EditText productPurchaseDate = alertDialogView.findViewById(R.id.editText_purchaseDateAddProduct);
        final EditText productExpirationDate = alertDialogView.findViewById(R.id.editText_expirationDateAddProduct);
        final EditText productNumbrePrevent = alertDialogView.findViewById(R.id.editText_numbrePreventAddProduct);
        Spinner tempProductCategory = alertDialogView.findViewById(R.id.spinner_categoryAddProduct);



        productName.setText(product.getName());
        productType.setText(product.getForm());
        productPurchaseDate.setText(product.getPurchaseDate());
        productExpirationDate.setText(product.getExpirationDate());
        productNumbrePrevent.setText(Integer.toString(product.getNumbrePrevent()));
        productQuantity.setText(Integer.toString(product.getQuantity()));

        int tempCategory = 0;
        String tempCategorySelect = product.getCategory();

        for(int i=0; i<dataCategory.length;i++){
            if(dataCategory[i].equals(tempCategorySelect)){
                tempCategory = i;
                break;
            }
        }

        tempProductCategory.setSelection(tempCategory);


        //On affecte un bouton "Ajouter" à notre AlertDialog et on lui affecte un évènement
        popup.setPositiveButton(R.string.text_add, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                String category = productCategory.getSelectedItem().toString();
                String name = productName.getText().toString();
                String quantity = productQuantity.getText().toString();
                String type = productType.getText().toString();
                String purchaseDate = productPurchaseDate.getText().toString();
                String expirationDate = productExpirationDate.getText().toString();
                String tempNumbrePrevent = productNumbrePrevent.getText().toString();

                if (isEmpty(name) || isEmpty(quantity) || isEmpty(type) || isEmpty(purchaseDate) || isEmpty(expirationDate) || isEmpty(tempNumbrePrevent) || category.equals("Catégories : ")) {
                    alertDialog(getString(R.string.AlertDialog_champsrempli));
                }
                else {
                    if(category.equals(getString(R.string.string_category))){
                        alertDialog(getString(R.string.alertDialog_chooseCategory));
                    }
                    else {
                        int temp = Integer.parseInt(quantity);
                        int temp2 = Integer.parseInt(tempNumbrePrevent);

                        product.setName(name);
                        product.setCategory(category);
                        product.setQuantity(temp);
                        product.setForm(type);
                        product.setPurchaseDate(purchaseDate);
                        product.setExpirationDate(expirationDate);
                        product.setNumbrePrevent(temp2);


                        saveStock(stock);

                        showListStock();

                        //On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
                        // Toast.makeText(GroceryListActivity.this, R.string.toast_productAdded, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        popup.setNegativeButton(R.string.string_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //On ferme la popup
            }
        });

        popup.setNeutralButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                stock.getStock().remove(pos);

                saveStock(stock);

                showListStock();
            }
        });
        popup.show();
    }

    private void popopAddOrSubstract(final Product product){
        //On crée la fenetre
        AlertDialog popup = new AlertDialog.Builder(this).create();

        //On applique le message en paramètre
        popup.setMessage("Vous pouvez ajouter ou enlever une unité de stock");

        //On ajoute le bouton positif 'Ok' qui ferme juste la pop up
        popup.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alertDialog_ok), new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        popup.setButton(AlertDialog.BUTTON_NEUTRAL, "+",new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int temp = product.getQuantity();
                temp++;
                product.setQuantity(temp);

                saveStock(stock);

                showListStock();
            }
        });

        popup.setButton(AlertDialog.BUTTON_NEGATIVE, "-",new AlertDialog.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int temp = product.getQuantity();

                if(temp > 0){
                    temp--;
                    product.setQuantity(temp);



                    saveStock(stock);

                    showListStock();
                }
                else{
                    //On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
                     Toast.makeText(RecapStockActivity.this, "Il n'y a plus de stock", Toast.LENGTH_SHORT).show();
                }

            }
        });



        //On affiche la pop up
        popup.show();
    }
}
