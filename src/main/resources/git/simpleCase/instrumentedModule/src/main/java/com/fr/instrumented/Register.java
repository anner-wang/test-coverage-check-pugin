package com.fr.instrumented;

/**
 * @author yaohwu
 */
public class Register {

    private String value;

    public Register push(String value) {
        if (this.value == null) {
            this.value = value;
        } else {
            this.value += "-" + value;
        }
        return this;
    }

    public String pop() {
        String value = this.value;
        this.value = null;
        return value;
    }
}
