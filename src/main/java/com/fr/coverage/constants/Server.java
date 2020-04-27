package com.fr.coverage.constants;

public enum Server {

    IP, PORT;

    private String value;


    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
