package com.aberg.abergestion;

import java.io.Serializable;

/**
 * Created by louis on 23/01/2018.
 */

public class donneesBudget implements Serializable{
    private String intitule;
    private Date date;
    private double montant;
    private boolean periodicite;
    private String categorie;

    public donneesBudget(String inti,Date d, double montant,boolean periodicite,String categorie){
        this.date = d;
        this.intitule= inti;
        this.montant = montant;
        this.periodicite = periodicite;
        this.categorie = categorie;
    }

    public String getIntitule(){
        return intitule;
    }

    public Date getDate(){
        return date;
    }

    public double getMontant(){
        return montant;
    }

    public boolean isPeriodicite() {
        return periodicite;
    }

    public String getCategorie(){
        return categorie;
    }
}
