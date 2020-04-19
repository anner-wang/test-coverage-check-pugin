package com.fr.jacoco.parser;

import com.fr.log.FineLoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lucian.Chen
 * @version 10.0
 * Created by Lucian.Chen on 2019/11/13
 */
public class XMLHelper {

    private static final String PACKAGE_TITLE = "package";
    private static final String SOURCE_FILE_TITLE = "sourcefile";
    private static final String LINE_TITLE = "line";
    private static final String NAME_TITLE = "name";
    private static final String LINE_NUMBER_TITLE = "nr";
    private static final String COVER_TITLE = "mi";
    private static final String IS_COVER = "0";

    public static Map<String, Map<Integer, Boolean>> parserXML(File xmlFile) {
        Map<String, Map<Integer, Boolean>> map = new HashMap<>();
        // 创建Reader对象
        SAXReader reader = new SAXReader();
        // 加载xml
        Document document = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFile)))) {
            reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            document = reader.read(in, "UTF-8");
//            document = reader.read(xmlFile);
//        } catch (Exception e) {
//            FineLoggerFactory.getLogger().error(e.getMessage(), e);
//            return map;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException | SAXException | DocumentException e) {
            FineLoggerFactory.getLogger().error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        // 获取根节点
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements(PACKAGE_TITLE);

        for (Element element : elements) {

            List<Element> sourceFiles = element.elements(SOURCE_FILE_TITLE);

            for (Element file : sourceFiles) {
                List<Element> lines = file.elements(LINE_TITLE);
                Map<Integer, Boolean> lineMap = new LinkedHashMap<>();
                String packageName = convertDelimiters(element.attributeValue(NAME_TITLE)) + "." + file.attributeValue(NAME_TITLE);

                for (Element line : lines) {
                    lineMap.put(Integer.parseInt(line.attributeValue(LINE_NUMBER_TITLE)), IS_COVER.equals(line.attributeValue(COVER_TITLE)));
                }
                map.put(packageName, lineMap);
            }

        }
        return map;
    }

    private static String convertDelimiters(String packageName) {
        return packageName.replaceAll("(/)|(\\\\)", ".");
    }


}
