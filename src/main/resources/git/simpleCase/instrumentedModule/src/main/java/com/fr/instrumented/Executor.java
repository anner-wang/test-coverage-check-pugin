package com.fr.instrumented;

/**
 * 模板渲染后的结果
 * 该结果默认情况不是完整的结果, 可能只是渲染了前1000行的结果.
 *
 * @author yaohwu
 */
public class Executor {

    public String execute(String template) {
        Register builder = new Register();
        int sheetSize = template.length();
        for (int i = 0; i < sheetSize; i++) {
            char templateSheet = template.charAt(i);
            if (templateSheet == 'a') {
                builder.push(String.valueOf(templateSheet));
            }
        }
        return builder.pop();
    }
}
