package com.pay.app.enumerations;

public enum FilingStatus {

    SINGLE("Single"),
    MARRIED("Married");

    private String name;

    FilingStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
