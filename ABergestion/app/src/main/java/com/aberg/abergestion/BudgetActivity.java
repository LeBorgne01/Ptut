package com.aberg.abergestion;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by louis on 20/12/2017.
 */

public class BudgetActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{

    private TextView budget_restant,textView_budget_restant;
    private Button button_voir_detail;
    private ListView mListView;
    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBudget();

        button_voir_detail = findViewById(R.id.button_voir_detail);
        button_voir_detail.setOnClickListener(this);

        //user = new User((EditText)findViewById(R.id.editText_nom),(EditText)findViewById(R.id.editText_prenom),(EditText)findViewById(R.id.passwordNum_motdepasse));
    }

    private void activityBudget() {
        setContentView(R.layout.activity_budget);

        budget_restant= findViewById(R.id.budget_restant);
        textView_budget_restant = findViewById(R.id.textView_budget_restant);
    }

    private void initLv(){
        setContentView(R.layout.activity_depense_revenu);
        mListView = (ListView) findViewById(R.id.lv);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(BudgetActivity.this, android.R.layout.simple_list_item_1, prenoms);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_voir_detail){
            this.initLv();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.button_voir_detail){
            setContentView(R.layout.activity_depense_revenu);
        }
        return true;
    }
}
