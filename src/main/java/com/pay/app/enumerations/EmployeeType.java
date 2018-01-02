package com.pay.app.enumerations;

public enum EmployeeType {
    FULLTIME("Full time"),
    CONTRACTOR("Contractor");

    private String name;

    EmployeeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
