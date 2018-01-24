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
    private List<String> al_groceryList;






    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        al_groceryList= new ArrayList<String>();
        back=findViewById(R.id.button_back);
        back.setOnClickListener(BtnBack);

        al_groceryList.add("eau");
        al_groceryList.add("chips");
        al_groceryList.add("ravioli");

        String[] colums = new String[]{"_id","col1","col2"};
        MatrixCursor matrixCursor = new MatrixCursor(colums);

        startManagingCursor(matrixCursor);
        matrixCursor.addRow(new Object []{1,"col1:ligne1","col2:ligne1"});
        matrixCursor.addRow(new Object []{2,"col1:ligne2","col2:ligne2"});

        String[]from = new String []{"col1","col2"};
        int[] to = new int []{R.id.textView_Col1,R.id.textView_Col2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.row_item,matrixCursor,from,to,0);
        stockListView = (ListView) findViewById(R.id.listViewStock);
        stockListView.setAdapter(adapter);
        viewGroceryList();
    }

    public void viewGroceryList(){



        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(GroceryListActivity.this,android.R.layout.simple_list_item_1, al_groceryList);
        //ListAdapter adapter = new SimpleAdapter(GroceryListActivity.this, al_groceryList, R.layout.activity_grocery_list, new String[]{"id", "nom"}, new int[] { 1, R.id.name });
       // stockListView.setAdapter(adapter);



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
