package com.fr.coverage.core.check;

import com.fr.coverage.core.bean.CodeLine;

/**
 * @description: 模拟新增代码测试的接口
 * @author: Anner
 * @time: 2020/4/10 9:48
 */
public class CheckServiceMocker implements CheckService {
    public boolean isTested(CodeLine codeLineBean) {
        return codeLineBean.getLine() % 6 == 0;
    }

    public boolean isValid(CodeLine codeLineBean) {
        return codeLineBean.getLine() % 2 == 0 ;
    }
}
