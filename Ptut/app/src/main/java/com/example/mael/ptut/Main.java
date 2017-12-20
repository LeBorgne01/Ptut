package com.example.mael.ptut;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      TextView text = new TextView (this);
        text.setText ("Combeubeu tous nu!");
        setContentView(text);
    }
}
