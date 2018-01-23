package com.aberg.abergestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mael on 22/01/2018.
 */

public class PasswordActivity extends AppCompatActivity {

    //On crée les différents composants qui se situe sur l'activité
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button zero;
    private Button valider;
    private Button effacer;
    private TextView password;

    //On crée un mot de passe que l'utilisateur va taper grâce aux boutons
    private String passwordCompared = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        //On lie le JAVA et le XML
        one = findViewById(R.id.button_1);
        two = findViewById(R.id.button_2);
        three = findViewById(R.id.button_3);
        four = findViewById(R.id.button_4);
        five = findViewById(R.id.button_5);
        six = findViewById(R.id.button_6);
        seven = findViewById(R.id.button_7);
        eight = findViewById(R.id.button_8);
        nine = findViewById(R.id.button_9);
        zero = findViewById(R.id.button_0);
        password = findViewById(R.id.textView_password);
        effacer = findViewById(R.id.button_effacer);
        valider = findViewById(R.id.button_validerPassword);


        //On ajoute les listener nécessaire pour les différents boutons de l'activité
        one.setOnClickListener(btnOne);
        two.setOnClickListener(btnTwo);
        three.setOnClickListener(btnThree);
        four.setOnClickListener(btnFour);
        five.setOnClickListener(btnFive);
        six.setOnClickListener(btnSix);
        seven.setOnClickListener(btnSeven);
        eight.setOnClickListener(btnEight);
        nine.setOnClickListener(btnNine);
        zero.setOnClickListener(btnZero);

        effacer.setOnClickListener(btnEffacer);
        valider.setOnClickListener(btnValider);

    }

    private View.OnClickListener btnValider = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PasswordActivity.this);
            String passwordUser = preferences.getString("PASSWORD","XXX");

            if(passwordUser.equals(passwordCompared)){
                //On redirige l'utilisateur sur l'activité du menu
                Intent intent = new Intent(PasswordActivity.this, MenuActivity.class);
                startActivity(intent);
                PasswordActivity.this.finish();
            }
            else{
                passwordCompared = "";
                password.setText("");
                alertDialog("Mauvais mot de passe !");
            }


        }
    };

    //Gestion des boutons numériques
    private View.OnClickListener btnOne = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared += "1";
            String layout = "";

            for(int i=0; i<passwordCompared.length();i++){
                layout += "*";
            }

            password.setText(layout);
        }
    };

    private View.OnClickListener btnTwo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared += "2";
            String layout = "";

            for(int i=0; i<passwordCompared.length();i++){
                layout += "*";
            }

            password.setText(layout);
        }
    };

    private View.OnClickListener btnThree = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared += "3";
            String layout = "";

            for(int i=0; i<passwordCompared.length();i++){
                layout += "*";
            }

            password.setText(layout);
        }
    };

    private View.OnClickListener btnFour = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared += "4";
            String layout = "";

            for(int i=0; i<passwordCompared.length();i++){
                layout += "*";
            }

            password.setText(layout);
        }
    };

    private View.OnClickListener btnFive = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared += "5";
            String layout = "";

            for(int i=0; i<passwordCompared.length();i++){
                layout += "*";
            }

            password.setText(layout);
        }
    };

    private View.OnClickListener btnSix = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared += "6";
            String layout = "";

            for(int i=0; i<passwordCompared.length();i++){
                layout += "*";
            }

            password.setText(layout);
        }
    };

    private View.OnClickListener btnSeven = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared += "7";
            String layout = "";

            for(int i=0; i<passwordCompared.length();i++){
                layout += "*";
            }

            password.setText(layout);
        }
    };

    private View.OnClickListener btnEight = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared += "8";
            String layout = "";

            for(int i=0; i<passwordCompared.length();i++){
                layout += "*";
            }

            password.setText(layout);
        }
    };

    private View.OnClickListener btnNine = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared += "9";
            String layout = "";

            for(int i=0; i<passwordCompared.length();i++){
                layout += "*";
            }

            password.setText(layout);
        }
    };

    private View.OnClickListener btnZero = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared += "0";
            String layout = "";

            for(int i=0; i<passwordCompared.length();i++){
                layout += "*";
            }

            password.setText(layout);
        }
    };

    //On gère le bouton effacer qui réinitialise le mot de passe entré
    private View.OnClickListener btnEffacer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            passwordCompared = "";
            password.setText(passwordCompared);
        }
    };

    //Fonction pour mettre une alerte si le mot de passe est faux
    private void alertDialog(String message){
        //On crée la fenetre
        AlertDialog bugAlert = new AlertDialog.Builder(PasswordActivity.this).create();

        //On applique le message en paramètre
        bugAlert.setMessage(message);

        //On ajoute le bouton positif 'Ok' qui ferme juste la pop up
        bugAlert.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new AlertDialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //On affiche la pop up
        bugAlert.show();
    }
}
