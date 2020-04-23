package com.fr.coverage.jacoco.parser;


import com.fr.stable.StringUtils;
import junit.framework.TestCase;

public class FilePathClassNameConverterTest extends TestCase {
    public void testNoPackage(){
        convertAndAssert("com.fr.A1.java", "/root/dir/src/java/main/com/fr/A1.java");
        convertAndAssert("com.finebi.A1.java", "com/finebi/A1.java");
    }

    public void testRootPath(){
        convertAndAssert("com.fr.p.A1.java", "/root/dir/src/java/main/com/fr/p/A1.java");
        convertAndAssert("com.fr.p.A1.java", "/root/com/fr/p/A1.java");
        convertAndAssert("com.fr.p.A1.java", "com/fr/p/A1.java");
    }

    public void testWithDriveLetter(){
        convertAndAssert("com.fr.p.A1.java", "C:/root/dir/src/java/main/com/fr/p/A1.java");
        convertAndAssert("com.fr.p.A1.java", "I:/root/com/fr/p/A1.java");
        convertAndAssert("com.fr.p.A1.java", "Z:/com/fr/p/A1.java");
    }

    public void testSlash(){
        convertAndAssert("com.fr.p.A1.java", "C:/root\\dir/src\\java/main/com/fr/p/A1.java");
        convertAndAssert("com.fr.p.A1.java", "I:\\root\\com\\fr\\p\\A1.java");
        convertAndAssert("com.fr.p.A1.java", "Z://com/fr/p/A1.java");
    }

    public void testNotInFrOrFineBI(){
        convertAndAssert(StringUtils.EMPTY, "C:/root\\dir/src\\java/main/com/notInFr/p/A1.java");
        convertAndAssert(StringUtils.EMPTY, "com/notInFr/p/A1.java");
    }

    public void testNonstandard(){
        convertAndAssert(StringUtils.EMPTY, ".java");
        convertAndAssert(StringUtils.EMPTY, "com..fr");
        convertAndAssert(StringUtils.EMPTY, "~~//\\\\");
    }

    private void convertAndAssert(String expected, String filePath) {
        assertEquals(expected,new FilePathClassNameConverter(filePath).convert());
    }


}
