package com.fr.coverage.core.constants;

import cn.hutool.core.util.StrUtil;

public enum Local {
    BASE_PATH(StrUtil.EMPTY);
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
