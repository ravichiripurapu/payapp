package com.pay.app.enumerations;

public enum PayrollFrequency {
    WEEKLY("Weekly"),
    BIWEEKLY("Bi Weely"),
    SEMIMONTHLY("Semi Monthly"),
    MONTHLY("Monthly");

    private String name;

    PayrollFrequency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
