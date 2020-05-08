package com.fr.coverage.core.jacoco;

import com.fr.coverage.core.bean.CodeLine;
import com.fr.coverage.core.jacoco.parser.JacocoXmlFinder;
import com.fr.coverage.core.jacoco.parser.XMLTestedInfoParser;
import com.fr.coverage.core.jacoco.report.CoverageResult;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/23
 */
public class CodeLineCoverageReporter {
    private File jacocoDir;

    public CodeLineCoverageReporter(File jacocoDir) {
        this.jacocoDir = jacocoDir;
    }

    public Map<CodeLine, Boolean> getCoverageMap() {
        List<String> paths = new JacocoXmlFinder(jacocoDir).find();
        Map<CodeLine, Boolean> map = new HashMap<>();
        for (String xmlPath : paths) {
            Map<CodeLine, Boolean> testedInfoMap = new XMLTestedInfoParser(new File(xmlPath)).parse();
            map.putAll(testedInfoMap);
        }
        return map;
    }

    public CoverageResult getCoverageResult(){
        Map<CodeLine, Boolean> coverageMap = getCoverageMap();
        long coverageCount = coverageMap.entrySet().stream().filter(line -> Boolean.TRUE.equals(line.getValue())).count();
        long totalCount = coverageMap.size();
        return new CoverageResult(((double)coverageCount) / totalCount, coverageCount, totalCount);
    }
}
