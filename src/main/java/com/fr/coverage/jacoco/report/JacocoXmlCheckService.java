package com.fr.coverage.jacoco.report;

import com.fr.coverage.bean.CodeLine;
import com.fr.coverage.jacoco.parser.JacocoXmlFinder;
import com.fr.coverage.jacoco.parser.XMLTestedInfoParser;
import com.fr.coverage.check.CheckService;
import com.fr.coverage.check.MemoryMapCheckService;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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
        List<String> paths = new JacocoXmlFinder(jacocoDir).find();
        Map<CodeLine, Boolean> map = new HashMap<>();
        for (String xmlPath : paths) {
            Map<CodeLine, Boolean> testedInfoMap = new XMLTestedInfoParser(new File(xmlPath)).parse();
            map.putAll(testedInfoMap);
        }
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
