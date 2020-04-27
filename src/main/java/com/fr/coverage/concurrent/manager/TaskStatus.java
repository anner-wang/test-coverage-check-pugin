package com.fr.coverage.concurrent.manager;

public enum TaskStatus {
    WAIT("WAIT"),RUNNING("RUNNING"),FAIL("FAIL"),FINISH("FINISH");

    private String value;

    TaskStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
