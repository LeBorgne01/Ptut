package com.aberg.abergestion;

import java.util.Collections;

/**
 * Created by Mickael on 20/12/2017.
 */

public  class Product {
    private String name;
    private String category;
    private int quantity;
    private String purchaseDate;
    private String expirationDate;
    private String form;
    private int numbrePrevent;


    public Product(String name, String category, int quantity, String purchaseDate, String expirationDate, String form) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.expirationDate = expirationDate;
        this.form = form;
        this.numbrePrevent = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setNumbrePrevent(int numbrePrevent){
        this.numbrePrevent = numbrePrevent;
    }

    public int getNumbrePrevent(){
        return this.numbrePrevent;
    }


}
