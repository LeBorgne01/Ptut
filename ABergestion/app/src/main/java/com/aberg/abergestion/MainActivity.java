package com.aberg.abergestion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMain();

        //user = new User((EditText)findViewById(R.id.editText_nom),(EditText)findViewById(R.id.editText_prenom),(EditText)findViewById(R.id.passwordNum_motdepasse));
    }


    private Button valider;
    private void activityMain(){
        setContentView(R.layout.activity_main);
        valider = (Button) findViewById(R.id.button_valider1);


    }
}
