package com.not.fr;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/3
 */
public class NotInFrClass {
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
}
