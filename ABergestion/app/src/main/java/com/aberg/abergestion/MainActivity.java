package com.aberg.abergestion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//MainActivity est en lien avec la vue de la création de compte
public class MainActivity extends AppCompatActivity {

    //On déclare un utilisateur
    private User user;

    //On crée les différents champs en Java pour les utiliser dans le code
    private Button valider;
    private EditText nom;
    private EditText prenom;
    private EditText password;
    private TextView debug;
    private EditText confirmPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(loadUser()){
            Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
            startActivity(intent);
            this.finish();
        }



        //SharedPreferences.getSharedPreferences("user.txt", Context.MODE_PRIVATE);
        



        //loadUser(user);
        //On redirige l'utilisateur sur la menu des gestions

       // setContentView(R.layout.activity_menu);
        //setContentView(R.layout.activity_menu);


       // setContentView(R.layout.activity_menu);

        //setContentView(R.layout.activity_menu);




        //On lie les champs XML avec les champs JAVA
        valider = findViewById(R.id.button_valider1);
        nom = findViewById(R.id.editText_nom);
        prenom = findViewById(R.id.editText_prenom);
        password = findViewById(R.id.passwordNum_motdepasse);
        confirmPassword = findViewById(R.id.passwordNum_confirmerMotdepasse);

        //On ajoute le listener du bouton valider
        valider.setOnClickListener(BtnValider);



    }




    private View.OnClickListener BtnValider = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            //On récupère ce que contiennent les champs du XML
            String contenuNom = nom.getText().toString();
            String contenuPrenom = prenom.getText().toString();
            String contenuPassword = password.getText().toString();
            String contenuConfirmPassword = confirmPassword.getText().toString();

            //On vérifie les données entrées dans les champs
            //Ici on vérifie que les champs ne sont pas vides
            if(isEmpty(contenuNom) || isEmpty(contenuPrenom) || isEmpty(contenuPassword) || isEmpty(contenuConfirmPassword)){

                //On affiche une Pop up pour prévenir l'utilisateur
                alertDialog(getString(R.string.AlertDialog_champsrempli));
            }
            else{
                //Ici on vérifie que les champs 'nom' et 'prenom' ne contiennent que des lettres, le caractère ' ' et le tiret '-'
                if(isText(contenuNom) && isText(contenuPrenom)){

                    //Ici on vérifie que les champs 'password' et 'confirmPassword' ne contiennent que des chiffres
                    if(isDigit(contenuPassword)){

                        //Ici on vérifie que les champs 'password' et 'confirmPassword' sont identiques
                        if(comparePassword(contenuPassword,contenuConfirmPassword)){

                            //On crée l'utilisateur grâce aux champs de l'activité
                            user = new User(contenuNom, contenuPrenom, contenuPassword);

                            //On sauvegarde l'utilisateur dans un fichier
                            try {
                                saveUser(user);

                                //On prévient l'utilisateur que l'utilisateur entré a été créé
                                alertDialog("L'utilisateur "+contenuPrenom+" "+contenuNom+" a été créer !");

                            } catch (IOException e) {
                                alertDialog("Impossible de sauver l'utilisateur");
                            }



                            //On redirige l'utilisateur sur l'activité du menu
                            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                            startActivity(intent);
                            MainActivity.this.finish();
                        }
                        else{
                            //On prévient que les mots de passe de sont pas identiques
                            alertDialog(getString(R.string.AlertDialog_passwordIdentiques));
                        }
                    }
                    else{
                        //On prévient que les mot de passe n'ont pas que des chiffres
                        alertDialog(getString(R.string.AlertDialog_queChiffres));
                    }
                }
                else{
                    //On prévient que les champs Nom et prénom de contiennent pas que des lettres ou '-' ou ' '
                    alertDialog(getString(R.string.AlertDialog_champsNomPrenom));

                }
            }

        }
    };

    //Fonction pour savoir si un String ne contient que des lettres ou '-' ou ' '
    private Boolean isText(String s){
        for(int i=0; i<s.length();i++){
            if(!(((s.charAt(i) >= 'a') && (s.charAt(i) <= 'z')) || ((s.charAt(i) >= 'A') && (s.charAt(i) <= 'Z')) || s.charAt(i) == '-' || s.charAt(i) == ' ')){
                return false;
            }
        }
        return true;
    }

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
        AlertDialog bugAlert = new AlertDialog.Builder(MainActivity.this).create();

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

    //Fonction pour enregistrer l'utilisateur dans un fichier
    private void saveUser(User u) throws IOException {

        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = user.edit();
        editor.putString("NAME", u.getName());
        editor.putString("FIRSTNAME", u.getFirstName());
        editor.putString("PASSWORD", u.getPassword());
        editor.commit();
    }

    //Fonction pour lire les données de l'utilisateur
    private boolean loadUser() {
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);
        String nom = user.getString("NAME","n/a");

        if(nom == "n/a"){
            return false;
        }
        else{
            return true;
        }
    }
}
