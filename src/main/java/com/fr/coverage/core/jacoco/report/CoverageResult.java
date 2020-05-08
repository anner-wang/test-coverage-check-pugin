package com.fr.coverage.core.jacoco.report;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2020/4/23
 */
public class CoverageResult {
    private double coverageRatio;
    private long coverageLineCount;
    private long totalLineCount;

    public CoverageResult(double coverageRatio, long coverageLineCount, long totalLineCount) {
        this.coverageRatio = coverageRatio;
        this.coverageLineCount = coverageLineCount;
        this.totalLineCount = totalLineCount;
    }

    public double getCoverageRatio() {
        return coverageRatio;
    }

    public void setCoverageRatio(double coverageRatio) {
        this.coverageRatio = coverageRatio;
    }

    public long getTotalLineCount() {
        return totalLineCount;
    }

    public void setTotalLineCount(long totalLineCount) {
        this.totalLineCount = totalLineCount;
    }

    public long getCoverageLineCount() {
        return coverageLineCount;
    }

    public void setCoverageLineCount(long coverageLineCount) {
        this.coverageLineCount = coverageLineCount;
    }
}
