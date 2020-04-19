package com.fr.constants;

public enum Local {
    BASE_PATH("/Users/anner/temp"),

    DEFAULT_REMOTE_NAME("origin");

    private String value;

    Local(String path) {
        this.value = path;
    }

    public String getValue() {
        return value;
    }


}
