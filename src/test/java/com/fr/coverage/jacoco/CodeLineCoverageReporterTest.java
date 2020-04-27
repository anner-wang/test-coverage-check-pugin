package com.fr.coverage.jacoco;

import com.fr.coverage.jacoco.report.CoverageResult;
import com.fr.coverage.utils.TestResourceUtils;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/23
 */
public class CodeLineCoverageReporterTest extends TestCase {
    @Test
    public void testGetCoverageResult() {
        CodeLineCoverageReporter codeLineCoverageReporter = new CodeLineCoverageReporter(TestResourceUtils.getResourceFile("git/simpleCase/build/jacoco"));
        CoverageResult coverageResult = codeLineCoverageReporter.getCoverageResult();
        Assert.assertEquals(0.5555, coverageResult.getCoverageRatio(), 0.001);
        Assert.assertEquals(30, coverageResult.getCoverageLineCount());
        Assert.assertEquals(54, coverageResult.getTotalLineCount());
    }
}
