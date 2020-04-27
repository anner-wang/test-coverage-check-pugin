package com.fr.coverage.constants;

import cn.hutool.core.util.StrUtil;

public enum Local {
    //    BASE_PATH(StrUtil.format("{}{}temp", System.getProperty("user.name"), File.separator)),
    BASE_PATH(StrUtil.EMPTY),
    DEFAULT_REMOTE_NAME("origin"),
    JSON_PATH(StrUtil.EMPTY);
    private String value;

    Local(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
