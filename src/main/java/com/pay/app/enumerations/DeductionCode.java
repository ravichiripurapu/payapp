package com.pay.app.enumerations;

public enum DeductionCode {
    MEDICAL("Medical"), RETIREMENT("401K"),
    FSA("FSA"),
    LIFE_INSURANCE ("Life Insurace"),
    OTHER_BENEFITS1("Other Benefits1"),
    OTHER_BENEFITS2("Other Benefits2"),
    OTHER_BENEFITS3("Other Benefits3"),
    OTHER_BENEFITS4("Other Benefits4"),
    OTHER_BENEFITS5("Other Benefits5");


    private String name;

    DeductionCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
