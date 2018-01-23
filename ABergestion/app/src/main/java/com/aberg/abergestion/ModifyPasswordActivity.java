package com.aberg.abergestion;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Mael on 23/01/2018.
 */

public class ModifyPasswordActivity extends AppCompatActivity {

    //On crée les différents composants du XML
    private EditText lastPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        //On lie le JAVA et le XML
        lastPassword = findViewById(R.id.passwordNum_lastPassword);
        newPassword = findViewById(R.id.passwordNum_newPassword);
        confirmPassword = findViewById(R.id.passwordNum_confirmPassword);
        valider = findViewById(R.id.button_validerModifyPW);

        //On ajoute un listener pour le bouton
        valider.setOnClickListener(btnValider);

    }

    private View.OnClickListener btnValider = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            //On récupère ce que contiennent les trois champs mot de passe
            String lastPW = lastPassword.getText().toString();
            String newPW = newPassword.getText().toString();
            String confirmPW = confirmPassword.getText().toString();

            //On récupère le mot de passe dans le fichier
            SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(ModifyPasswordActivity.this);
            String password = user.getString("PASSWORD","n/a");

            //On vérifie si les champs sont remplis
            if(isEmpty(lastPW) || isEmpty(newPW) || isEmpty(confirmPW)){
                alertDialog(getString(R.string.AlertDialog_champsrempli));
            }
            else{
                //On vérifie que les champs contiennent que des chiffres
                if(isDigit(lastPW) && isDigit(newPW) && isDigit(confirmPW)){

                    //On vérifie que l'ancien mot de passe est égal au mot de passe du fichier
                    if(password.equals(lastPW)){

                        //On compare les deux nouveaux mots de passe
                        if(comparePassword(newPW,confirmPW)){

                            //On met à jour le mot de passe dans le fichier
                            SharedPreferences.Editor editor = user.edit();
                            editor.putString("PASSWORD", newPW);
                            editor.commit();

                            //On réinitialise les champs
                            lastPassword.setText("");
                            newPassword.setText("");
                            confirmPassword.setText("");

                            //On alerte l'utilisateur
                            alertDialog(getString(R.string.alertDialog_modifyPW));
                        }
                        else{
                            alertDialog(getString(R.string.alertDialog_confirmationPWDifferent));
                        }
                    }
                    else{
                        alertDialog(getString(R.string.alertDialog_falseLastPW));
                    }
                }
                else{
                    alertDialog(getString(R.string.AlertDialog_queChiffres));
                }
            }
        }
    };

    //Fonction pour savoir si un String ne contient que des chiffres
    private Boolean isDigit(String s){
        for(int i=0; i<s.length(); i++){
            if(!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }

    //Fonction pour savoir si un String est vide
    private Boolean isEmpty(String s){
        if(s.length() != 0){
            return false;
        }
        return true;
    }

    //Fonction pour comparer 2 mots de passe
    private Boolean comparePassword(String first, String second){
        if(first.length() != second.length()){
            return false;
        }
        if(Integer.parseInt(first) != Integer.parseInt(second)){
            return false;
        }
        return true;
    }

    //Fonction pour afficher un pop up avec un message et un bouton "Ok"
    private void alertDialog(String message){
        //On crée la fenetre
        AlertDialog bugAlert = new AlertDialog.Builder(ModifyPasswordActivity.this).create();

        //On applique le message en paramètre
        bugAlert.setMessage(message);

        //On ajoute le bouton positif 'Ok' qui ferme juste la pop up
        bugAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alertDialog_ok), new AlertDialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        //On affiche la pop up
        bugAlert.show();
    }

}
