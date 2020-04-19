package com.fr.module1;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/3
 */
public class Module1Class {
    private String msg;

    public void functionAllLineTested() {
        msg = "functionTested:Line1";
        msg = "functionTested:Line2";
    }

    public void functionNotTested() {
        msg = "functionNotTested:Line1";
        msg = "functionNotTested:Line2";
    }

    public void functionTestedWithCondition(boolean testLine2) {
        msg = "functionTestedWithCondition:Line1";
        if (testLine2) {
            msg = "functionTestedWithCondition:Line2";
        }
    }

    public void newAddFunctionTested(){
        msg = "newAddFunctionTested:Line1";
        msg = "newAddFunctionTested:Line2";
    }

    public void newAddFunctionNotTested(){
        msg = "newAddFunctionNotTested:Line1";
        msg = "newAddFunctionNotTested:Line2";
    }
}
