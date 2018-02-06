package com.aberg.abergestion;

import android.content.Intent;
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



}
