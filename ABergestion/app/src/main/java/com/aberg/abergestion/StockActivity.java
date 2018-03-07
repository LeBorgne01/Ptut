package com.aberg.abergestion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Mickael on 20/12/2017.
 */

public class StockActivity extends AppCompatActivity {

    //On crée les différents champs en Java pour les utiliser dans le code
    private TextView stockManagement;
    private Button groceryList;
    private Button productList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        //On lie les champs XML avec les champs JAVA
        stockManagement=findViewById(R.id.textView_stockManagement);
        groceryList =findViewById(R.id.button_groceryList);
        productList=findViewById(R.id.button_productList);


        //On ajoute le listener du bouton valider
        groceryList.setOnClickListener(BtnGroceryList);
        productList.setOnClickListener(BtnProductList);

        checkGroceryList();

    }

    @Override
    protected void onResume(){
        super.onResume();

        checkGroceryList();
    }


    private View.OnClickListener BtnGroceryList = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(StockActivity.this, GroceryListActivity.class);
            startActivity(intent);


        }

    };

    private View.OnClickListener BtnProductList = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(StockActivity.this,RecapStockActivity.class);
            startActivity(intent);
        }

    };

    //Fonction pour vérifier si des produits sont en dessous de leur limite et les ajoutent à la liste de courses si oui
    private void checkGroceryList(){
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = user.edit();

        //On récupère la taille du stock et la taille de la liste de course
        int nombreStock = user.getInt("NOMBRE_STOCKS", 0);
        int nombreListe = user.getInt("ELEMENTS_LISTE_COURSE", 0);

        //On déclare nos variables
        String[] temp;  //Pour spliter la variable tempRow
        String tempRow; //Pour récupérer la ligne du produit "i" dans le fichier
        int tempStock; //Quantité du produit de tempRow
        int tempPrevent; //Nombre limite du produit tempRow
        Boolean addProduct = true; //Booléen pour savoir si notre produit est déjà dans la liste de course ou non

        //On parcourt toute la liste de stock
        for(int i=0; i<nombreStock; i++){
            //On récupère le produit "i"
            tempRow = user.getString("STOCK_"+i, null);

            //On divise tempRow
            temp = tempRow.split(";");
            tempStock = Integer.parseInt(temp[2]);
            tempPrevent = Integer.parseInt(temp[6]);

            //On compare le stock et la limite, si le stock est égal ou inférieur on rentre dans le if
            if(tempStock <= tempPrevent){
                //On récupère les champs qui nous interessent
                String tempName = temp[0];
                String tempCategory = temp[1];
                String tempForm = temp[5];

                //On prépare la ligne que l'on va ajouter au fichier
                String tempPut = tempName+";"+tempCategory+";"+tempStock+";"+tempForm;

                //On déclare une variable temporaire qui va permettre de voir si notre produit est déjà dans la liste de course
                String compare;

                //On parcourt la liste de course
                for(int j=0; j<nombreListe; j++){
                    //On récupère le produit "j" de la liste de course
                    compare = user.getString("LISTE_COURSE_"+j, null);

                    //Si le produit est déjà dans la liste de course, on passe notre boolean à false
                    if(compare.equals(tempPut)){
                        addProduct = false;
                        break;
                    }
                }

                //Si notre boolean est vrai
                if(addProduct){
                    //On ajoute le produit à la liste de course
                    editor.putString("LISTE_COURSE_"+nombreListe, tempPut);

                    //On augmente la taille de la liste de course de 1
                    nombreListe++;

                }

                //On réinitialise notre booleen
                addProduct = true;

            }

        }

        //On met à jour le nombre de produit de la liste de course
        editor.putInt("ELEMENTS_LISTE_COURSE", nombreListe);

        //On valide nos changements
        editor.commit();
    }

}
