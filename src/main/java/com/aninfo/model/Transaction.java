package com.aninfo.model;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionID;

    private Long cbu;

    private Double value;

    private String type;

    public Transaction(){}

    public Transaction(Account account, Double transactionValue, String type){
        this.cbu = account.getCbu();
        this.value = transactionValue;
        this.type = type;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public long getCbu() {
        return cbu;
    }

    public void setCbu(Long accountCBU) {
        this.cbu = accountCBU;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}