package com.example.cse412financialmanagementsystem;

import java.util.Date;

public class Card {

    private String number;
    private String exp;
    private Integer cvv;
    private Long account_id;

    public Card(String number, String exp, Integer cvv, Long accountId) {
        this.number = number;
        this.exp = exp;
        this.cvv = cvv;
        this.account_id = accountId;
    }

    // Getters and setters
    public String getCardNumber(){return number;}
    public void setNumber(String number){this.number = number;}

    public String getCardExp(){return exp;}
    public void setCardExp(String exp){this.exp = exp;}

    public Integer getCardCVV(){return cvv;}
    public void setCardCVV(Integer cvv){this.cvv = cvv;}

    public Long getCardAccount() {
        return account_id;
    }

    public void setCardAccount(Long account_id) {
        this.account_id = account_id;
    }
}
