package com.fr.module1;

import junit.framework.TestCase;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class Module1ClassTest extends TestCase {
    public void testFunctionAllLineTested(){
        new Module1Class().functionAllLineTested();
    }

    public void testFunctionTestedWithCondition(){
        new Module1Class().functionTestedWithCondition(false);
    }

    public void testNewAddFunctionTested(){
        new Module1Class().newAddFunctionTested();
    }
}
