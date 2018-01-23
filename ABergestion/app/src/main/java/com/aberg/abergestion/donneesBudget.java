package com.aberg.abergestion;

/**
 * Created by louis on 23/01/2018.
 */

public class donneesBudget {
    private String intitule;
    private Date date;
    private double montant;

    public donneesBudget(String inti,Date d, double montant){
        this.date = d;
        this.intitule= inti;
        this.montant = montant;
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
}
