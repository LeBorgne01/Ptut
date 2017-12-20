package com.aberg.abergestion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mickael on 20/12/2017.
 */

public class StockActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityStock();


    }

    public void activityStock(){
        setContentView(R.layout.activity_stock);

    }

}
