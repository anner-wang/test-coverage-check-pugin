package com.fr.coverage.core.check;

import com.fr.coverage.core.bean.CodeLine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class MemoryMapCheckService implements CheckService{
    private Map<CodeLine,Boolean> map;

    public MemoryMapCheckService(Map<CodeLine, Boolean> map) {
        this.map = new HashMap<>(map);
    }

    @Override
    public boolean isTested(CodeLine codeLineBean) {
        if (!isValid(codeLineBean)) {
            return false;
        }
        return map.get(codeLineBean);
    }

    @Override
    public boolean isValid(CodeLine codeLineBean) {
        return map.containsKey(codeLineBean);
    }
}
