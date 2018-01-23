package com.aberg.abergestion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by louis on 23/01/2018.
 */

public class DepenseRevenuActivity extends AppCompatActivity {

    private Budget budget;
    private ListView mListView;
    private donneesBudget test1,test2,test3;
    private ArrayList<donneesBudget> test;

    private ArrayList<donneesBudget> initAL(){
        Date d = new Date(25,07,1998);
        test = new ArrayList<donneesBudget>();
        test1 = new donneesBudget("test1",d,19.2);
        test2 = new donneesBudget("test2",d,152.25);
        test3 = new donneesBudget("test3",d,2.07);
        test.add(test1);
        test.add(test2);
        test.add(test3);
        return test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test = initAL();
        budget = new Budget(test);
        ArrayList<String> stringDatas = budget.getIntituleData();
        setContentView(R.layout.activity_depense_revenu);
        mListView = (ListView) findViewById(R.id.lv);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DepenseRevenuActivity.this, android.R.layout.simple_list_item_1, stringDatas);
        mListView.setAdapter(adapter);
    }
}
