package com.pay.app.enumerations;

public enum  DepositType {
    DIRECT("Direct Deposit"),MANUAL("Manual Check");

    private String name;

    DepositType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
