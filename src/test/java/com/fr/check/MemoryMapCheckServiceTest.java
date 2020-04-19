package com.fr.check;

import com.fr.bean.CodeLine;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class MemoryMapCheckServiceTest extends TestCase {

    private MemoryMapCheckService checkService;

    @Before
    public void setUp(){
        Map<CodeLine,Boolean> map = new HashMap<CodeLine, Boolean>(){{
            put(new CodeLine("com.fr.A.java", 3), true);
            put(new CodeLine("com.fr.A.java", 5), true);
            put(new CodeLine("com.fr.A.java", 7), false);
            put(new CodeLine("com.fr.A2.java", 9), true);
            put(new CodeLine("com.fr.A2.java", 7), false);
        }};
        checkService = new MemoryMapCheckService(map);
    }

    public void testIsTested(){
        assertFalse(checkService.isTested(new CodeLine("com.fr.A.java", 7)));
        assertFalse(checkService.isTested(new CodeLine("com.fr.A.java", 6)));
        assertTrue(checkService.isTested(new CodeLine("com.fr.A.java", 3)));
        assertTrue(checkService.isTested(new CodeLine("com.fr.A2.java", 9)));
    }

    public void testIsValid(){
        assertFalse(checkService.isValid(new CodeLine("com.fr.A.java", 1)));
        assertFalse(checkService.isValid(new CodeLine("com.fr.A.java", -1)));
        assertFalse(checkService.isValid(new CodeLine("com.fr.A.java", 0)));
        assertTrue(checkService.isValid(new CodeLine("com.fr.A.java", 3)));
        assertTrue(checkService.isValid(new CodeLine("com.fr.A.java", 7)));
        assertTrue(checkService.isValid(new CodeLine("com.fr.A2.java", 7)));
        assertTrue(checkService.isValid(new CodeLine("com.fr.A2.java", 9)));
    }



}
