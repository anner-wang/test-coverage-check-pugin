package com.fr.coverage.jacoco.parser;

import com.fr.coverage.utils.TestResourceUtils;
import junit.framework.TestCase;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/16
 */
public class XMLHelperTest extends TestCase {
    public void testParseJacocoXml() throws IOException, URISyntaxException {
        Map<String, Map<Integer, Boolean>> stringMapMap = XMLHelper.parserXML(TestResourceUtils.getResourceFile("jacocoXml/jacoco.xml"));
        assertEquals(19, stringMapMap.size());
        Map<Integer, Boolean> testInfoMap = stringMapMap.get("com.fr.json.adaptor.ObjectMapperAdaptor.java");
        assertTrue(testInfoMap.get(19));
        assertFalse(testInfoMap.get(25));
        assertNull(testInfoMap.get(1));
    }

    public void test(){
        Map<String, Map<Integer, Boolean>> stringMapMap = XMLHelper.parserXML(TestResourceUtils.getResourceFile("git/simpleCase/build/jacoco/subModule1/jacoco.xml"));
        System.out.println(1);
    }
}
