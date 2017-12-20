package com.aberg.abergestion;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMain();

        //user = new User((EditText)findViewById(R.id.editText_nom),(EditText)findViewById(R.id.editText_prenom),(EditText)findViewById(R.id.passwordNum_motdepasse));
    }


    private Button valider;
    private EditText nom;
    private EditText prenom;
    private EditText password;
    private TextView debug;
    private EditText confirmPassword;

    private void activityMain(){
        setContentView(R.layout.activity_main);

        valider = findViewById(R.id.button_valider1);
        nom = findViewById(R.id.editText_nom);
        prenom = findViewById(R.id.editText_prenom);
        password = findViewById(R.id.passwordNum_motdepasse);
        debug = findViewById(R.id.textView_Debug);
        confirmPassword = findViewById(R.id.passwordNum_confirmerMotdepasse);

        valider.setOnClickListener(BtnValider);


    }

    private View.OnClickListener BtnValider = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String contenuNom = nom.getText().toString();
            String contenuPrenom = prenom.getText().toString();
            String contenuPassword = password.getText().toString();
            String contenuConfirmPassword = confirmPassword.getText().toString();

            if(isText(contenuNom) && isText(contenuPrenom) && isDigit(contenuPassword) && contenuPassword == contenuConfirmPassword){
                user = new User(nom.getText().toString(), prenom.getText().toString(), password.getText().toString());
                nom.setText("Hello");
            }
            else{
               nom.setText("Salute");

            }

        }
    };

    private Boolean isText(String s){
        for(int i=0; i<s.length();i++){
            if(!(((s.charAt(i) > 'a') && (s.charAt(i) < 'z')) || ((s.charAt(i) > 'A') && (s.charAt(i) < 'Z')))){
                return false;
            }
        }
        return true;
    }

    private Boolean isDigit(String s){
        for(int i=0; i<s.length(); i++){
            if(!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }

    private Boolean isEmpty(String s){
        if(s.length() != 0){
            return false;
        }
        return true;
    }
}
