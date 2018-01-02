package com.pay.app.enumerations;



public enum BankAccountType {

    SAVINGS("Savings"),
    CHECKINGS("Checkings");

    private String name;

    BankAccountType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
