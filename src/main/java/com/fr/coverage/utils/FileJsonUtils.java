package com.fr.coverage.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.fr.coverage.constants.Local;

import java.io.File;

public class FileJsonUtils {

    public static void save(Object object) {
        JSON json = JSONUtil.parse(object);
        FileUtil.writeUtf8String(json.toStringPretty(), FileUtil.file(Local.JSON_PATH.getValue()));
    }

    public static void empty() {
        FileUtil.writeUtf8String(StrUtil.EMPTY, FileUtil.file(Local.JSON_PATH.getValue()));
    }

    // TODO
    public static Object load(Class clazz) {
        return null;
    }
}
