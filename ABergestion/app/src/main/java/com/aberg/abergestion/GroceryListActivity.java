package com.aberg.abergestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mickael on 23/01/2018.
 */



public class GroceryListActivity extends AppCompatActivity {
    private Button back;
    private ListView stockListView;
    private List<String> al_groceryList;



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
        setContentView(R.layout.activity_grocery_list);
        al_groceryList= new ArrayList<String>();
        back=findViewById(R.id.button_back);
        back.setOnClickListener(BtnBack);
        al_groceryList.add("ricard");
        al_groceryList.add("chips");
        al_groceryList.add("ravioli");

        viewGroceryList();
    }

    public void viewGroceryList(){

        stockListView = (ListView) findViewById(R.id.listViewStock);

        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(GroceryListActivity.this,android.R.layout.simple_list_item_1, al_groceryList);
        ListAdapter adapter = new SimpleAdapter(GroceryListActivity.this, al_groceryList, R.layout.activity_grocery_list, new String[]{"id", "nom"}, new int[] { 1, R.id.name });
        stockListView.setAdapter(adapter);



    }


    private View.OnClickListener BtnBack = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(GroceryListActivity.this, StockActivity.class);
            startActivity(intent);
            GroceryListActivity.this.finish();
        }

    };

}
