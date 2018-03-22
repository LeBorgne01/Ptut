package com.aberg.abergestion;

import java.io.Serializable;

/**
 * Created by louis on 23/01/2018.
 */

public class donneesBudget implements Serializable{
    private String intitule;
    private Date date;
    private double montant;
    private String typePeriodicite;
    private boolean perio;
    private String categorie;

    public donneesBudget(String inti,Date d, double montant,boolean perio,String typePeriodicite,String categorie){
        this.date = d;
        this.intitule= inti;
        this.montant = montant;
        this.typePeriodicite = typePeriodicite;
        this.categorie = categorie;
        this.perio = perio;
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
        return perio;
    }

    public String getCategorie(){
        return categorie;
    }

    public String getTypePeriodicite(){return typePeriodicite;};
}
