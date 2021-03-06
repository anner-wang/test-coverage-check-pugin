package com.fr.coverage.jacoco.parser;

import com.fr.coverage.core.bean.CodeLine;
import com.fr.coverage.core.jacoco.parser.XMLTestedInfoParser;
import com.fr.coverage.core.utils.TestResourceUtils;
import junit.framework.TestCase;

import java.util.Map;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class XMLTestedInfoParserTest extends TestCase {
    public void testParse() {
        String className = "com.fr.json.adaptor.ObjectMapperAdaptor.java";
        Map<CodeLine, Boolean> result = new XMLTestedInfoParser(TestResourceUtils.getResourceFile("jacocoXml/jacoco.xml")).parse();
        assertTrue(result.get(new CodeLine(className,19)));
        assertFalse(result.get(new CodeLine(className,25)));
        assertNull(result.get(new CodeLine(className,1)));
    }
}
