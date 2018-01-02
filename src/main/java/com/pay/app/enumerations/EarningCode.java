package com.pay.app.enumerations;

public enum EarningCode {
    REGAULAR("Regular"),
    OVERTIME("Overtime"),
    JOINING_BONUS("Joining Bonux"),
    BONUS("Regular Bonus"),
    COMMISSION ("Commission"),
    OTHER_PAY1("Other Pay1"),
    OTHER_PAY2("Other Pay2"),
    OTHER_PAY3("Other Pay3"),
    OTHER_PAY4("Other Pay4"),
    OTHER_PAY5("Other Pay5");

    private String name;

    EarningCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
