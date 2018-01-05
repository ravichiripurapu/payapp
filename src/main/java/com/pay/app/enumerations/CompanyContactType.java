package com.pay.app.enumerations;

public enum CompanyContactType  {

    PAYROLLADMIN("Payroll Admin");

    private String name;

    CompanyContactType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
