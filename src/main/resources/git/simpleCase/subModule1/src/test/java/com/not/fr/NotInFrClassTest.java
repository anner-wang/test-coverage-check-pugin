package com.not.fr;

import com.fr.module1.Module1Class;
import junit.framework.TestCase;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class NotInFrClassTest extends TestCase {
    public void testFunctionAllLineTested(){
        new NotInFrClass().functionAllLineTested();
    }

    public void testFunctionTestedWithCondition(){
        new NotInFrClass().functionTestedWithCondition(false);
    }
}
