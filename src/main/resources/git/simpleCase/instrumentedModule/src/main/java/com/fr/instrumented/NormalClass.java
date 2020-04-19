package com.fr.instrumented;

import org.gradle.api.internal.file.collections.DefaultConfigurableFileCollection;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/2
 */
public class NormalClass {
    private String msg;

    public void functionTested() {
        msg = "functionTested:line1";
    }

    public void functionNotTested() {
        msg = "functionNotTested:line1";
    }
}
