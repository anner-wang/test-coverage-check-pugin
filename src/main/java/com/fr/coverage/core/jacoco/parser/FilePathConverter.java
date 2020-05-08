package com.fr.coverage.core.jacoco.parser;


import cn.hutool.core.util.StrUtil;

public class FilePathConverter implements ClassNameConverter {
    private String filePath;
    private static final String[] PRE_PACKAGE = {"com.fr", "com.finebi"};
    private static final String[] TYPE = {"java"};

    public FilePathConverter(String filePath) {
        this.filePath = filePath;
    }

    // 处理文件路径
    @Override
    public String convert() {
        String result = format();
        for (String str : PRE_PACKAGE) {
            int index = result.indexOf(str);
            if (index >= 0) {
                return typeFilter(result.substring(index));
            }
        }
        return StrUtil.EMPTY;
    }

    private String format() {
        return filePath.replace("/", ".").replace("\\", ".");
    }

    private String typeFilter(String transformedPath) {
        for (String str : TYPE) {
            if (transformedPath.endsWith(str)) {
                return transformedPath;
            }
        }
        return StrUtil.EMPTY;
    }

}
