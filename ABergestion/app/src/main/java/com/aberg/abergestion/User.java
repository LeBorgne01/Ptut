package com.aberg.abergestion;

/**
 * Created by Mael on 18/12/2017.
 */

public class User {
    private String name;
    private String firstName;
    private String password;
    private Budget budget;

    public User(String name, String firstName, String password) {
        this.name = name;
        this.firstName = firstName;
        this.password = password;
        //this.budget = new Budget();
    }

    public boolean checkPassword(String password){
        if(this.password == password){
            return true;
        }
        else {
            return false;
        }
    }

    /*public Budget getBudget(){
        return this.budget;
    }*/

    public String getName(){
        return this.name;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getPassword(){
        return this.password;
    }
}
