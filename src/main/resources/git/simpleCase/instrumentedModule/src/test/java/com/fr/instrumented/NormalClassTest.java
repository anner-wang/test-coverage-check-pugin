package com.fr.instrumented;

import junit.framework.TestCase;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/2
 */
public class NormalClassTest extends TestCase {
    public void testFunctionTested(){
        new NormalClass().functionTested();
    }
}
