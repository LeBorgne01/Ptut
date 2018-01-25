package com.aberg.abergestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by louis on 23/01/2018.
 */

public class DepenseRevenuActivity extends AppCompatActivity implements Serializable {

    private Budget budget;
    private ListView mListView;
    private ArrayList<String> datas;
    private donneesBudget test1,test2,test3;
    private ArrayList<donneesBudget> listDR;

    private ArrayList<donneesBudget> initAL(){
        Date d = new Date(25,07,1998);
        listDR = new ArrayList<donneesBudget>();
        test1 = new donneesBudget("test1",d,19.2,true);
        test2 = new donneesBudget("test2",d,152.25,false);
        test3 = new donneesBudget("test3",d,2.07,true);
        listDR.add(test1);
        listDR.add(test2);
        listDR.add(test3);
        return listDR;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depense_revenu);
        Intent intent = getIntent();
        listDR = (ArrayList<donneesBudget>)intent.getSerializableExtra("listDR");
        mListView = (ListView) findViewById(R.id.lv);
        budget = new Budget(listDR);
        ArrayList<String> stringDatas = budget.getIntituleData();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DepenseRevenuActivity.this, android.R.layout.simple_list_item_1, stringDatas);
        mListView.setAdapter(adapter);
    }

    /*@Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(DepenseRevenuActivity.this,BudgetActivity.class);
        intent.putExtra("listDR",listDR);
        startActivity(intent);
    }*/
}
