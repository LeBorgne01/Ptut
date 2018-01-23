package com.aberg.abergestion;

import android.os.Bundle;
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
    private  ListView stockListView;



    String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };


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




    }

    public void activityStock(){



        setContentView(R.layout.activity_stock);


    }
    public void viewGroceryList(){
        setContentView(R.layout.activity_grocery_list);
        stockListView = (ListView) findViewById(R.id.listViewStock);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(StockActivity.this,android.R.layout.simple_list_item_1, prenoms);
        stockListView.setAdapter(adapter);


    }

    private View.OnClickListener BtnGroceryList = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            viewGroceryList();

        }

    };


    private View.OnClickListener BtnProductList = new View.OnClickListener(){
        @Override
        public void onClick(View v) {


        }

    };

}
