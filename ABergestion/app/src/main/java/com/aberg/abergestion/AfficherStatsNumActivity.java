package com.aberg.abergestion;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.TabActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarContainer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AfficherStatsNumActivity extends AppCompatActivity{

    private ArrayList<donneesBudget> listDR;

    public void onCreate(Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_stats_num);

        listDR = new ArrayList<>();
        loadListDR(listDR);

        TextView tv_contenu_mlpd = (TextView)findViewById(R.id.textView_content_mlpd);
        tv_contenu_mlpd.setText(calcMoisLePlusDe(listDR));

    }

    public void saveListDR(ArrayList<donneesBudget> liste){ //Fonction qui permet la sauvegarde de la liste dans un fichier .txt
        //On récupère la taille de la liste puis on crée un tableau de string aussi grand
        int tailleArray = liste.size();
        String [] savedText = new String[tailleArray];

        //On déclare la variable temporaire pour chaque ligne
        String temp;

        //On parcourt le tableau pour y ajouter chaque element
        for(int i=0; i < tailleArray; i++){
            //Ici on écrit un élément et on sépare deux éléments avec des points virgule
            temp = liste.get(i).getIntitule()+";"+liste.get(i).getDate().dateToString()+";"+liste.get(i).getMontant()+";"+liste.get(i).isPeriodicite()+";"+liste.get(i).getTypePeriodicite()+";"+liste.get(i).getCategorie();
            //System.out.println(tailleArray);
            //On affecte cette chaine au tableau sauvegarder
            savedText[i] = temp;
        }

        //On ouvre l'écriture dans notre fichier utilisateur
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = user.edit();

        //On indique la taille de notre liste de course
        editor.putInt("NOMBRE_BUDGETS", liste.size());

        //On ajoute tous les éléments à cette liste
        for(int i=0; i<liste.size();i++){
            editor.putString("LISTE_DR_"+i,savedText[i]);
        }

        //On met à jour le fichier
        editor.commit();
    }

    private String calcMoisLePlusDe(ArrayList<donneesBudget> listDR){
        int nbDepParMois[] = new int[11];
        String mois[]={"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"};
        String moisLePlusDepensier= new String();
        for(int i =0; i < listDR.size();i++){
            int indiceMois = listDR.get(i).getDate().getMois();
            nbDepParMois[indiceMois-1]++;
        }
        int max = nbDepParMois[0];
        int indiceMax = 0;
        for(int i =0; i < nbDepParMois.length;i++){
            //System.out.println(i + ":"+nbDepParMois[i]);
            if(max<nbDepParMois[i]){
                indiceMax = i;
            }
        }
        moisLePlusDepensier = mois[indiceMax];
        return moisLePlusDepensier;
    }

    public void loadListDR(ArrayList<donneesBudget> liste){
        //On ouvre le fichier de preference
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(this);

        //On prend le nombre d'éléments de la liste de courses (par défaut 0)
        String[] loadText = new String[user.getInt("NOMBRE_BUDGETS",0)];
        System.out.println(loadText.length);
        //On rempli notre tableau de string avec les éléments des préférences de notre utilisateur
        for(int i=0; i < loadText.length;i++){
            loadText[i] = user.getString("LISTE_DR_"+i,null);
        }

        //On déclare nos variables pour créer nos elements de liste de course
        String tempIntitule;
        Date tempDate;
        double tempMontant;
        boolean tempPeriodicite;
        String tempCategorie;
        String tempTypePeriodicite;

        //Ce tableau permet de récupérer le splitage de la chaine du tableau loadText
        String[] temp;

        for(int i=0; i < loadText.length;i++){
            //Si notre ligne est null on ne fait rien
            if(loadText[i] != null){
                //On split notre chaine de caractère grâce aux ; qu'on a mis à la sauvegarde
                temp = loadText[i].split(";");
                //System.out.println(loadText[i]+"\n");
                //On récupère nos variables
                tempIntitule = temp[0];
                tempDate = toDate(temp[1]);
                tempMontant = Double.parseDouble(temp[2]);
                tempPeriodicite = Boolean.parseBoolean(temp[3]);
                tempTypePeriodicite = temp[4];
                tempCategorie = temp[5];

                //On ajoute notre produit à l'arrayList
                liste.add(new donneesBudget(tempIntitule,tempDate,tempMontant,tempPeriodicite,tempTypePeriodicite,tempCategorie));
            }

        }
    }

    public Date toDate(String s){ //Fonction de conversion d'un string vers une date
        String[] tabS = s.split("/"); //On creér un tableau de String qui va prendre les valeurs comprise entre les "/"
        Date d = new Date(Integer.parseInt(tabS[0]),Integer.parseInt(tabS[1]),Integer.parseInt(tabS[2])); //On creér une date à partir de ce tableau
        return d; //On return la date
    }


}
