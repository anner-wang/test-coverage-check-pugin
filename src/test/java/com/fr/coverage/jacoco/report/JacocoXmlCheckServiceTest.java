package com.fr.coverage.jacoco.report;

import com.fr.coverage.bean.CodeLine;
import com.fr.coverage.utils.TestResourceUtils;
import junit.framework.TestCase;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class JacocoXmlCheckServiceTest extends TestCase {

    public void testIntegration(){
        JacocoXmlCheckService checkService = new JacocoXmlCheckService(TestResourceUtils.getResourceFile("git/simpleCase/build/jacoco"));
        String executorClassName = "com.fr.instrumented.Executor.java";
        assertTrue(checkService.isTested(new CodeLine(executorClassName, 20)));
        assertTrue(checkService.isTested(new CodeLine(executorClassName, 9)));
        assertFalse(checkService.isValid(new CodeLine(executorClassName, 1)));
        assertTrue(checkService.isValid(new CodeLine(executorClassName, 9)));

        String normalClassName = "com.fr.instrumented.NormalClass.java";
        assertTrue(checkService.isTested(new CodeLine(normalClassName, 14)));
        assertFalse(checkService.isTested(new CodeLine(normalClassName, 18)));
        assertFalse(checkService.isValid(new CodeLine(normalClassName, 17)));
    }

}
