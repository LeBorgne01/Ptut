package com.aberg.abergestion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by louis on 20/12/2017.
 */

public class BudgetActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener,Serializable{

    private TextView budget_restant,textView_budget_restant;
    private Button button_voir_detail, add_depense, add_revenu;
    private donneesBudget initList;
    private ArrayList<donneesBudget> listDR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBudget();
        Intent intent = getIntent();
        listDR = (ArrayList<donneesBudget>)intent.getSerializableExtra("listDR");
        if(listDR == null){
            listDR = new ArrayList<>();
        }
        add_depense = findViewById(R.id.add_depense);
        add_revenu = (Button)findViewById(R.id.add_revenu);
        button_voir_detail = findViewById(R.id.button_voir_detail);
        button_voir_detail.setOnClickListener(this);
        add_depense.setOnClickListener(this);
        add_revenu.setOnClickListener(this);
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
            Intent intent= new Intent(BudgetActivity.this,AddDepenseActivity.class);
            if(listDR != null){
                System.out.println(listDR.size());
            }
            else System.out.println("ok");
            intent.putExtra("listDR",listDR);
            startActivity(intent);
        }
        if(view.getId() == R.id.add_revenu){
            Intent intent = new Intent(BudgetActivity.this, AddRevenuActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId() == R.id.button_voir_detail){

        }
        return true;
    }
}
