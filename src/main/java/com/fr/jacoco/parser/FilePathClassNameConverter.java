package com.fr.jacoco.parser;


import com.fr.stable.StringUtils;

public class FilePathClassNameConverter implements ClassNameConverter {
    private String filePath;
    private static final String[] PRE_PACKAGE = {"com.fr", "com.finebi"};

    public FilePathClassNameConverter(String filePath) {
        this.filePath = filePath;
    }

    // 处理文件路径
    @Override
    public String convert() {
        String result = format();
        for (String str : PRE_PACKAGE) {
            int index = result.indexOf(str);
            if (index >= 0) {
                return result.substring(index);
            }
        }
        return StringUtils.EMPTY;
    }

    private String format() {
        return filePath.replace("/", ".").replace("\\", ".");
    }


}
