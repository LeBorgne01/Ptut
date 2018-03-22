package com.aberg.abergestion;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarContainer;
import android.view.KeyEvent;
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
import java.util.Calendar;

public class AfficherStatsNumActivity extends Activity {

    private ArrayList<donneesBudget> listDR;

    public void onCreate(Bundle savedInstanceState) {
        //TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_afficher_stats_num);

        listDR = new ArrayList<>();
        loadListDR(listDR);

        TextView tv_contenu_mlpd = (TextView)findViewById(R.id.textView_content_mlpd);
        tv_contenu_mlpd.setText(calcMoisLePlusDe(listDR));
        TextView tv_contenu_alpd = (TextView)findViewById(R.id.textView_content_alpd);
        tv_contenu_alpd.setText(calcAnneeLaPlusDep(listDR));
        TextView tv_moydpm = (TextView)findViewById(R.id.textView_content_moydpm);
        tv_moydpm.setText(String.valueOf(calcMoyenneDepParMois(listDR)));
        TextView tv_moydpa = (TextView)findViewById(R.id.textView_content_moydpa);
        tv_moydpa.setText(String.valueOf(calcMoyenneDepParAnnee(listDR)));
        TextView tv_contenu_mlpb = (TextView)findViewById(R.id.textView_content_mpdr);
        tv_contenu_mlpb.setText(calcMoisAvecLePlusDeRevenu(listDR));
        TextView tv_contenu_alpb = (TextView)findViewById(R.id.textView_content_apdr);
        tv_contenu_alpb.setText(calcAnneeLaPlusBenef(listDR));
        TextView tv_contenu_moyrpm = (TextView)findViewById(R.id.textView_content_moyrpm);
        tv_contenu_moyrpm.setText(String.valueOf(calcMoyenneRevParMois(listDR)));
        TextView tv_contenu_moyrpa = (TextView)findViewById(R.id.textView_content_moyrpa);
        tv_contenu_moyrpa.setText(String.valueOf(calcMoyenneRevParAnnee(listDR)));
        TextView tv_contenu_domlpd = (TextView)findViewById(R.id.textView_content_domlpd);
        tv_contenu_domlpd.setText(calcDomaineLePlusDep(listDR));
        TextView tv_contenu_domlpb = (TextView)findViewById(R.id.textView_content_domrlp);
        tv_contenu_domlpb.setText(calcDomaineLePlusBenef(listDR));


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(AfficherStatsNumActivity.this,BudgetActivity.class);
            startActivity(intent);
            AfficherStatsNumActivity.this.finish();
            return true;
        }
        return false;

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

    private String calcDomaineLePlusDep(ArrayList<donneesBudget> listDR){
        String domaineLePlusDep = new String();
        String domaines[] ={"Jeu","Nourriture&Hygiène","Restaurant","Sortie","Shopping"};
        double nbDepParDomaine[] = new double[5];
        for(int i = 0; i<listDR.size();i++){
            if(listDR.get(i).getMontant()<0) {
                for(int j = 0; j<domaines.length;j++){
                    if (domaines[j].equals(listDR.get(i).getCategorie())) {
                        System.out.println("ok");
                        nbDepParDomaine[j] += (-listDR.get(i).getMontant());
                    }
                }
            }
        }
        double max=nbDepParDomaine[0];
        int indiceMax =0;
        for(int i = 0; i < nbDepParDomaine.length;i++){
            if(max < nbDepParDomaine[i]){
                indiceMax = i;
                max=nbDepParDomaine[i];
            }
        }
        domaineLePlusDep = domaines[indiceMax];
        return domaineLePlusDep;
    }

    private String calcDomaineLePlusBenef(ArrayList<donneesBudget> listDR){
        String domaineLePlusDep = new String();
        String domaines[] ={"Salaire","Jeu","Investissement"};
        double nbDepParDomaine[] = new double[3];
        for(int i = 0; i<listDR.size();i++){
            if(listDR.get(i).getMontant()>0) {
                for(int j = 0; j<domaines.length;j++){
                    if (domaines[j].equals(listDR.get(i).getCategorie())) {
                        nbDepParDomaine[j] += listDR.get(i).getMontant();
                    }
                }
            }
        }
        double max=nbDepParDomaine[0];
        int indiceMax =0;
        for(int i = 0; i < nbDepParDomaine.length;i++){
            if(max < nbDepParDomaine[i]){
                indiceMax = i;
                max=nbDepParDomaine[i];
            }
        }
        domaineLePlusDep = domaines[indiceMax];
        return domaineLePlusDep;
    }

    private double calcMoyenneRevParAnnee(ArrayList<donneesBudget> listDR){
        double moyenne = 0.0;
        double somme = 0.0;
        double nbRevParAnnee[] = new double[2020-1900];
        for(int i = 0; i < listDR.size(); i++){
            if(listDR.get(i).getMontant()>0) {
                int indiceAnnee = listDR.get(i).getDate().getAnnee()-1900;
                nbRevParAnnee[indiceAnnee - 1] += listDR.get(i).getMontant();
            }
        }

        int indiceDebut = 0;
        int indiceFin = 0;
        Calendar rightNow = Calendar.getInstance();
        indiceFin = rightNow.get(Calendar.YEAR)-1900;

        for(int j = 0; j < nbRevParAnnee.length; j++){
            if(nbRevParAnnee[j]!=0){
                indiceDebut = j;
                break;
            }
        }
        for(int k = indiceDebut; k < indiceFin;k++){
            somme+=nbRevParAnnee[k];
        }
        //System.out.println(indiceDebut);
        //System.out.println((nbRevParAnnee.length-indiceDebut));
        moyenne = (int)((somme/(indiceFin-indiceDebut))*100)/100;
        return moyenne;
    }

    private double calcMoyenneDepParAnnee(ArrayList<donneesBudget> listDR){
        double moyenne = 0.0;
        double somme = 0.0;
        double nbDepParAnnee[] = new double[2020-1900];
        for(int i = 0; i < listDR.size(); i++){
            if(listDR.get(i).getMontant()<0) {
                int indiceAnnee = listDR.get(i).getDate().getAnnee()-1900;
                nbDepParAnnee[indiceAnnee - 1] += (-listDR.get(i).getMontant());
            }
        }

        int indiceDebut = 0;
        int indiceFin = 0;
        Calendar rightNow = Calendar.getInstance();
        indiceFin=rightNow.get(Calendar.YEAR)-1900;
        //System.out.println("if" + indiceFin);

        for(int j = 0; j < nbDepParAnnee.length; j++){
            if(nbDepParAnnee[j]!=0){
                indiceDebut = j;
                break;
            }
        }
        for(int k = indiceDebut; k < indiceFin;k++){
            somme+=nbDepParAnnee[k];
        }
        //System.out.println(indiceDebut);
        //System.out.println((nbDepParAnnee.length-indiceDebut));
        moyenne = (int)((somme/(indiceFin-indiceDebut))*100)/100;
        return moyenne;
    }

    private double calcMoyenneRevParMois(ArrayList<donneesBudget> listDR){
        double moyenne = 0.0;
        double somme = 0.0;
        double nbRevParMois[] = new double[11];

        Calendar rightNow= Calendar.getInstance();
        int anneeEnCours = rightNow.get(Calendar.YEAR);

        for(int i = 0; i < listDR.size(); i++){
            if(listDR.get(i).getDate().getAnnee() == anneeEnCours) {
                if (listDR.get(i).getMontant() > 0) {
                    int indiceMois = listDR.get(i).getDate().getMois();
                    nbRevParMois[indiceMois - 1] += listDR.get(i).getMontant();
                }
            }
        }

        int indiceFin = rightNow.get(Calendar.MONTH);

        for(int j = 0; j < nbRevParMois.length; j++){
            somme += nbRevParMois[j];
        }
        moyenne = ((int)somme/(indiceFin+1)*100)/100;
        return moyenne;
    }

    private double calcMoyenneDepParMois(ArrayList<donneesBudget> listDR){
        double moyenne = 0.0;
        double somme = 0.0;
        double nbDepParMois[] = new double[11];

        Calendar rightNow= Calendar.getInstance();
        int anneeEnCours = rightNow.get(Calendar.YEAR);

        for(int i = 0; i < listDR.size(); i++){
            if(listDR.get(i).getDate().getAnnee() == anneeEnCours) {
                if (listDR.get(i).getMontant() < 0) {
                    int indiceMois = listDR.get(i).getDate().getMois();
                    nbDepParMois[indiceMois - 1] += (-listDR.get(i).getMontant());
                }
            }
        }

        int indiceFin=rightNow.get(Calendar.MONTH);

        for(int j = 0; j < nbDepParMois.length; j++){
            somme += nbDepParMois[j];
        }
        moyenne = ((int)somme/(indiceFin+1)*100)/100;
        return moyenne;
    }

    private String calcMoisLePlusDe(ArrayList<donneesBudget> listDR){
        double nbDepParMois[] = new double[11];
        String mois[]={"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"};
        String moisLePlusDepensier= new String();
        for(int i =0; i < listDR.size();i++){
            if(listDR.get(i).getMontant() < 0) {
                int indiceMois = listDR.get(i).getDate().getMois();
                nbDepParMois[indiceMois - 1] += -(listDR.get(i).getMontant());
            }
        }
        double max = nbDepParMois[0];
        int indiceMax = 0;
        for(int i =0; i < nbDepParMois.length;i++){
            //System.out.println(i + ":"+nbDepParMois[i]);
            if(max<nbDepParMois[i]){
                indiceMax = i;
                max = nbDepParMois[indiceMax];
            }
        }
        moisLePlusDepensier = mois[indiceMax];
        return moisLePlusDepensier;
    }

    private String calcMoisAvecLePlusDeRevenu(ArrayList<donneesBudget> listDR){
        double nbRevParMois[] = new double[11];
        String mois[]={"Janvier","Février","Mars","Avril","Mai","Juin","Juillet","Août","Septembre","Octobre","Novembre","Décembre"};
        String moisLePlusBenef= new String();
        for(int i =0; i < listDR.size();i++){
            if(listDR.get(i).getMontant() > 0) {
                int indiceMois = listDR.get(i).getDate().getMois();
                nbRevParMois[indiceMois - 1] += listDR.get(i).getMontant();
            }
        }
        double max = nbRevParMois[0];
        int indiceMax = 0;
        for(int i =0; i < nbRevParMois.length;i++){
            //System.out.println(i + ":"+nbDepParMois[i]);
            if(max<nbRevParMois[i]){
                indiceMax = i;
                max = nbRevParMois[indiceMax];
            }
        }
        moisLePlusBenef = mois[indiceMax];
        return moisLePlusBenef;
    }

    private String calcAnneeLaPlusDep(ArrayList<donneesBudget> listDR){
        int nbDepParAnnee[] = new int[2020-1900];
        String anneeLaPlusDepensiere;
        String annee[] = new String[2020-1900];
        for(int i = 1900; i < 2020;i++){
            annee[i-1900] = String.valueOf(i);
            //System.out.println(annee[i-1900]);
        }
        for(int i =0; i < listDR.size();i++){
            if(listDR.get(i).getMontant() < 0) {
                int indiceAnnee = listDR.get(i).getDate().getAnnee() - 1900;
                nbDepParAnnee[indiceAnnee - 1] += -(listDR.get(i).getMontant());
            }
        }
        int max = nbDepParAnnee[0];
        int indiceMax = 0;
        for(int i =0; i < nbDepParAnnee.length;i++){
            //System.out.println(i + ":"+nbDepParAnnee[i]);
            if(max<nbDepParAnnee[i]){
                indiceMax = i;
                max=nbDepParAnnee[indiceMax];
                System.out.println(max);
            }
        }
        //System.out.println(indiceMax);
        anneeLaPlusDepensiere = annee[indiceMax+1];
        //System.out.println(anneeLaPlusDepensiere);
        return anneeLaPlusDepensiere;
    }

    private String calcAnneeLaPlusBenef(ArrayList<donneesBudget> listDR){
        int nbRevParAnnee[] = new int[2020-1900];
        String anneeLaPlusBenef;
        String annee[] = new String[2020-1900];
        for(int i = 1900; i < 2020;i++){
            annee[i-1900] = String.valueOf(i);
            //System.out.println(annee[i-1900]);
        }
        for(int i =0; i < listDR.size();i++){
            if(listDR.get(i).getMontant() > 0) {
                int indiceAnnee = listDR.get(i).getDate().getAnnee() - 1900;
                nbRevParAnnee[indiceAnnee - 1] += listDR.get(i).getMontant();
            }
        }
        int max = nbRevParAnnee[0];
        int indiceMax = 0;
        for(int i =0; i < nbRevParAnnee.length;i++){
            //System.out.println(i + ":"+nbDepParAnnee[i]);
            if(max<nbRevParAnnee[i]){
                indiceMax = i;
                max=nbRevParAnnee[indiceMax];
                System.out.println(max);
            }
        }
        //System.out.println(indiceMax);
        anneeLaPlusBenef = annee[indiceMax+1];
        //System.out.println(anneeLaPlusDepensiere);
        return anneeLaPlusBenef;
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
