package com.aberg.abergestion;

import android.content.Intent;
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

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by louis on 20/12/2017.
 */

public class BudgetActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{

    private TextView budget_restant,textView_budget_restant;
    private Button button_voir_detail, add_depense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBudget();
        add_depense = findViewById(R.id.add_depense);
        button_voir_detail = findViewById(R.id.button_voir_detail);
        button_voir_detail.setOnClickListener(this);
        add_depense.setOnClickListener(this);
    }

    private void activityBudget() {
        setContentView(R.layout.activity_budget);
        budget_restant= findViewById(R.id.budget_restant);
        textView_budget_restant = findViewById(R.id.textView_budget_restant);
    }

    //Interaction
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_voir_detail){
            Intent intent = new Intent(BudgetActivity.this, DepenseRevenuActivity.class);
            startActivity(intent);
        }
        if(view.getId() == R.id.add_depense){
            Intent intent = new Intent(BudgetActivity.this, AddDepenseActivity.class);
            startActivity(intent);
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
