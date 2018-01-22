package com.aberg.abergestion;

import java.util.ArrayList;

/**
 * Created by Mickael on 20/12/2017.
 */

public class Stock {
    private ArrayList<Product> stock;

    public Stock() {
        this.stock = new ArrayList<>();
    }

    public ArrayList<Product> getStock() {
        return stock;
    }

    public void setStock(ArrayList<Product> stock) {
        this.stock = stock;
    }

    public Product searchProduct (String name){
        for(int i=0;i<stock.size();i++){
            if(stock.get(i).getName()==name){
                return stock.get(i);
            }
        }
        return null;

    }


}
