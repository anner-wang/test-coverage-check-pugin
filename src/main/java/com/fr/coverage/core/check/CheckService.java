package com.fr.coverage.core.check;

import com.fr.coverage.core.bean.CodeLine;

public interface CheckService {
    // 返回是否被测试到了
    boolean isTested(CodeLine codeLineBean);

    // 返回是否是有效到代码
    boolean isValid(CodeLine codeLineBean);
}
