package com.fr.coverage.core.jacoco.parser;

import java.io.File;
import java.util.List;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class JacocoXmlFinder {
    private File dir;

    public JacocoXmlFinder(File dir) {
        this.dir = dir;
    }

    public List<String> find() {
        return new FileTraverser(dir).find("jacoco.xml",false);
    }
}
