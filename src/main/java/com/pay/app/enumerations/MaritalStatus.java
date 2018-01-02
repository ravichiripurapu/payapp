package com.pay.app.enumerations;

public enum MaritalStatus {

    SINGLE("Single"),
    MARRIED("Married"),
    DIVORCED("Divorced");

    private String name;

    MaritalStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
