package com.fr.constants;

public enum Server {

    IP("localhost"), PORT("7999"), PROTOCOL("ssh");

    private String value;

    Server(String value) {
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
