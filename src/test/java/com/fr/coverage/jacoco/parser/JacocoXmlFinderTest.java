package com.fr.coverage.jacoco.parser;

import com.fr.coverage.core.jacoco.parser.JacocoXmlFinder;
import com.fr.coverage.core.utils.TestResourceUtils;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class JacocoXmlFinderTest extends TestCase {

    @Test
    public void testFind() {
        List<String> filePaths = new JacocoXmlFinder(TestResourceUtils.getResourceFile("git/simpleCase/build/jacoco")).find();
        assertEquals(3, filePaths.size());
        Set<File> files = filePaths.stream().map(path -> new File(path)).collect(Collectors.toSet());
        Set<String> parentNames = files.stream().map(f -> f.getParentFile()).map(f -> f.getName()).collect(Collectors.toSet());
        assertTrue(parentNames.contains("instrumentedModule"));
        assertTrue(parentNames.contains("subModule1"));
    }

}
