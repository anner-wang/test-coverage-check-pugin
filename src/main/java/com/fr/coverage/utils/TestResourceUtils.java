package com.fr.coverage.utils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class TestResourceUtils {
    public static File getResourceFile(String path) {
        try {
            return new File(Thread.currentThread().getContextClassLoader().getResource(path).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
