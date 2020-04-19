package com.fr.jacoco.parser;

import com.fr.bean.CodeLine;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/17
 */
public class XMLTestedInfoParser {

    private File xmlFile;

    public XMLTestedInfoParser(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public Map<CodeLine, Boolean> parse() {
        Map<String, Map<Integer, Boolean>> stringMapMap = XMLHelper.parserXML(xmlFile);
        Map<CodeLine, Boolean> result = buildCodeLineMap(stringMapMap);
        return result;
    }

    /**
     * 重新构建Map，把两层Map转成一层
     *
     * {
     * a:{1:true,2:false}
     * b:{1:true}
     * }
     *
     * ==>
     *
     *    {a-1:true,a-2:false,b-1:true}
     */
    private Map<CodeLine, Boolean> buildCodeLineMap(Map<String, Map<Integer, Boolean>> stringMapMap) {
        return stringMapMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().entrySet().stream().collect(Collectors.toMap(integerBooleanEntry -> new CodeLine(entry.getKey(), integerBooleanEntry.getKey()), integerBooleanEntry -> integerBooleanEntry.getValue())).entrySet().stream())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }
}
