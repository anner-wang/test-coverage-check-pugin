package com.fr.coverage.jacoco.report;

import com.fr.coverage.bean.CodeLine;
import com.fr.coverage.jacoco.CodeLineCoverageReporter;
import com.fr.coverage.check.CheckService;
import com.fr.coverage.check.MemoryMapCheckService;

import java.io.File;
import java.util.Map;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class JacocoXmlCheckService implements CheckService {

    private File jacocoDir;
    private CheckService checkService;


    public JacocoXmlCheckService(File jacocoDir) {
        this.jacocoDir = jacocoDir;
        init(jacocoDir);
    }

    private void init(File jacocoDir) {
        Map<CodeLine, Boolean> map = new CodeLineCoverageReporter(jacocoDir).getCoverageMap();
        checkService = new MemoryMapCheckService(map);
    }


    @Override
    public boolean isTested(CodeLine codeLineBean) {
        return checkService.isTested(codeLineBean);
    }

    @Override
    public boolean isValid(CodeLine codeLineBean) {
        return checkService.isValid(codeLineBean);
    }

}
