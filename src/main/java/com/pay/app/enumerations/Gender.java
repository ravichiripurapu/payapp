package com.pay.app.enumerations;

public enum Gender {

    MALE("Male"),
    FEMALE("Female");

    private String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
