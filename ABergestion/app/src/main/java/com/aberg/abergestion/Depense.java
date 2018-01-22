package com.aberg.abergestion;

/**
 * Created by Mael on 19/12/2017.
 */

public class Depense extends Intitule {
    private String intitule;
    private Date date;
    private float montant;

    public Depense(String inti, Date d, float montant){
        this.intitule = inti;
        this.date = d;
        this.montant = montant;
    }

    public String getIntitule(){
        return intitule;
    }

    public Date getDate(){
        return date;
    }

    public float getMontant(){
        return montant;
    }
}
