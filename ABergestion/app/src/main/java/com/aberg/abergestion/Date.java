package com.aberg.abergestion;

/**
 * Created by louis on 22/01/2018.
 */

public class Date {
    private int jour,mois,annee;

    public Date(int j,int m,int a){
        this.jour = j;
        this.mois = m;
        this.annee=a;
    }

    public int getJour(){
        return jour;
    }

    public int getMois(){
        return mois;
    }

    public int getAnnee(){
        return annee;
    }
}
