package com.pay.app.enumerations;

public enum  State {

    CA("California", "CA"),
    TX("Texas", "TX");

    private String name;
    private String code;

    State(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
