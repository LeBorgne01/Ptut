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

    private EditText lastPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private Button valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        lastPassword = findViewById(R.id.passwordNum_lastPassword);
        newPassword = findViewById(R.id.passwordNum_newPassword);
        confirmPassword = findViewById(R.id.passwordNum_confirmPassword);
        valider = findViewById(R.id.button_validerModifyPW);

        valider.setOnClickListener(btnValider);

    }

    private View.OnClickListener btnValider = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String lastPW = lastPassword.getText().toString();
            String newPW = newPassword.getText().toString();
            String confirmPW = confirmPassword.getText().toString();

            SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(ModifyPasswordActivity.this);
            String password = user.getString("PASSWORD","n/a");

            if(isEmpty(lastPW) || isEmpty(newPW) || isEmpty(confirmPW)){
                alertDialog("Tous les champs doivent être remplis");
            }
            else{
                if(isDigit(lastPW) && isDigit(newPW) && isDigit(confirmPW)){
                    if(password.equals(lastPW)){
                        if(comparePassword(newPW,confirmPW)){
                            SharedPreferences.Editor editor = user.edit();
                            editor.putString("PASSWORD", newPW);
                            editor.commit();
                            lastPassword.setText("");
                            newPassword.setText("");
                            confirmPassword.setText("");
                            alertDialog("Votre mot de passe a été modifié");
                        }
                        else{
                            alertDialog("La confirmation de mot de passe est différente du mot de passe");
                        }
                    }
                    else{
                        alertDialog("Votre ancien mot de passe est faux");
                    }
                }
                else{
                    alertDialog("Vos mots de passe doivent contenir que des chiffres");
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
        bugAlert.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new AlertDialog.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        //On affiche la pop up
        bugAlert.show();
    }

}
