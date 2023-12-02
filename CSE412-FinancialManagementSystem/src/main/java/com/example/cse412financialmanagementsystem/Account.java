package com.example.cse412financialmanagementsystem;
import java.math.BigInteger;

public class Account {

    private Long account_id;
    private double balance;
    private String name;
    private int zip;
    private String password;

    public Account(Long account_id, double balance, String name, int zip, String password) {
        this.account_id = account_id;
        this.balance = balance;
        this.name = name;
        this.zip = zip;
        this.password = password;
        }

    // Getters and setters

    public Long getAccount_ID() {
        return account_id;
    }

    public void setAccount_ID(Long account_id) {
        this.account_id = account_id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName(){ return name;}

    public void setName(String name){ this.name = name;}

    public int getZip(){return zip;}

    public void setZip(int zip){this.zip = zip;}

    public String getPassword(){return password;}

    public void setPassword(String password){this.password = password;}


}
