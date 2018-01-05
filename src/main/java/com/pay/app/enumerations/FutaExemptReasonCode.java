package com.pay.app.enumerations;


import javax.persistence.Entity;
import javax.persistence.Table;

public enum FutaExemptReasonCode  {

    SAVINGS("Savings"),
    CHECKINGS("Checkings");

    private String name;

    FutaExemptReasonCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
