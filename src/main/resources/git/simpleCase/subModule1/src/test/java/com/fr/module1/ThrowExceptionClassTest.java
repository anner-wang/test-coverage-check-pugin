package com.fr.module1;

import junit.framework.TestCase;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class ThrowExceptionClassTest extends TestCase {
    public void testThrowException(){
        //gradle test ignore
        new ThrowExceptionClass().throwException();
    }
}
