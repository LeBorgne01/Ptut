package com.aberg.abergestion;

import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mickael on 23/01/2018.
 */



public class GroceryListActivity extends AppCompatActivity {

    private Button back;
    private ListView stockListView;
    private ArrayList <Product>al_groceryList;
    private Product p;
    private Product p2;
    private Product p3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        p= new Product("ravioli","alimentaire",2,null,null,"conserve");
        p2= new Product("chips","alimentaire",4,null,null,"sachet");
        p3= new Product("lessive","menager",1,null,null,"bidon");

        al_groceryList = new ArrayList<Product>();
        al_groceryList.add(p);
        al_groceryList.add(p2);
        al_groceryList.add(p3);



        back=findViewById(R.id.button_back);
        stockListView = (ListView) findViewById(R.id.listViewStock);
        back.setOnClickListener(BtnBack);



        // Définition des colonnes
        // SimpleCursorAdapter a besoin obligatoirement d'un ID nommé "_id"
        // Ensuite on met le nombre de colonnes que l'on veut
        String[] colums = new String[]{"_id","col1","col2"};

        // Définition des données du tableau
        // On affecte au matrixCursor les colonnes que l'on vient de créer
        // On démarre le MatrixCursor
        MatrixCursor matrixCursor = new MatrixCursor(colums);
        startManagingCursor(matrixCursor);

        //On ajoute des objets au MatrixCursor
        for(int i=0;i<al_groceryList.size();i++){
            matrixCursor.addRow(new Object[]{i, al_groceryList.get(i).getName(), al_groceryList.get(i).getQuantity()+" "+al_groceryList.get(i).getForm()});
        }


       // matrixCursor.addRow(new Object[]{0, al_groceryList.get(0).getName(), al_groceryList.get(0).getQuantity()+" "+al_groceryList.get(0).getForm()});
       // matrixCursor.addRow(new Object[]{2, "col1:ligne2", "col2:ligne2"});

        // On prend les données des colonnes 1 et 2 ...
        String[]from = new String []{"col1","col2"};
        // ... pour les placer dans les TextView définis dans "row_item.xml"
        int[] to = new int[] { R.id.textView_Col1, R.id.textView_Col2};

        // On crée l'objet SimpleCursorAdapter
        // On met le context (this ici), ensuite la définition des lignes de la liste, ensuite on ajoute les lignes, on définit les colonnes, on les lient aux textView, on met le flag à 0
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.row_item,matrixCursor,from,to,0);

        // On lie la liste avec l'adapter
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
