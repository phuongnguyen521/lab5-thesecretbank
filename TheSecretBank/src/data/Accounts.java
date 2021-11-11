/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 *
 * @author Nguyen Ngoc Phuong - SE150998 - SE1506 -LAB211 - LAB0005 - SPR2021 
 */
public class Accounts implements Serializable {
    
    //attributes of account
    private String id, name, password;
    private double balanceAmount;
    // format currency 
    private final DecimalFormat currency = new DecimalFormat ("VNĐ #,###,###");
    
    // constructs of an account 
    public Accounts(String id, String name, String password, double balanceAmount) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.balanceAmount = balanceAmount;
    }
    
    public Accounts(String id, double balanceAmount) {
        this.id = id;
        this.balanceAmount = balanceAmount;
    }

    public Accounts(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Accounts() {
    }

    public Accounts(String id) {
        this.id = id;
    }
    
    // getters, setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
    
    // showing the information of an accounts
    public String toString(int choice) {
        String result = "";
        switch (choice) {
            case 1:
                result = "ID: " + id + "\nName: " + name + "\nBalance Amount: " + currency.format(balanceAmount);
                break;
            case 2:
                result = id + ", " + name + ", " + password + ", " + 0;
                break;
            case 3:
                result = id + "," + name + "," + password + "," + currency.format(balanceAmount);
                break;
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    // finding the account as require from searchAccount (a method of accountList)
    @Override
    public boolean equals(Object obj) {
        Accounts another = (Accounts) obj;
        return this.id.equals(another.id);
    }

}
