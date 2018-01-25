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

    public donneesBudget(String inti,Date d, double montant,boolean periodicite){
        this.date = d;
        this.intitule= inti;
        this.montant = montant;
        this.periodicite = periodicite;
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

    public void displayDonneesBudget(){
        System.out.println("Intitule : " + intitule+"\nDate : "+date.toDate()+"\nMontant :"+montant+"\nPeriodicite :"+periodicite);
    }
}
