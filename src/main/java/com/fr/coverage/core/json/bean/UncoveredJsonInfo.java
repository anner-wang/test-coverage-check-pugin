package com.fr.coverage.core.json.bean;

import com.fr.stable.AssistUtils;

import java.util.Map;

public class UncoveredJsonInfo {
    // 最终的覆盖率
    private double coverage;
    // 有效的代码行数
    private int validLineNumber;
    // 被测试到的代码行数
    private int testedLineNumber;
    // 新增的代码的细节
    private Map<String, String> detail;

    public UncoveredJsonInfo() {
    }

    public UncoveredJsonInfo(double coverage, int validLineNumber, int testedLineNumber, Map<String, String> detail) {
        this.coverage = coverage;
        this.validLineNumber = validLineNumber;
        this.testedLineNumber = testedLineNumber;
        this.detail = detail;
    }

    public double getCoverage() {
        return coverage;
    }

    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    public int getValidLineNumber() {
        return validLineNumber;
    }

    public void setValidLineNumber(int validLineNumber) {
        this.validLineNumber = validLineNumber;
    }

    public int getTestedLineNumber() {
        return testedLineNumber;
    }

    public void setTestedLineNumber(int testedLineNumber) {
        this.testedLineNumber = testedLineNumber;
    }

    public Map<String, String> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, String> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "JsonInfo{" +
                "com.fr.coverage=" + coverage +
                ", validLineNumber=" + validLineNumber +
                ", testedLineNumber=" + testedLineNumber +
                ", detail=" + detail +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UncoveredJsonInfo
                && AssistUtils.equals(coverage, ((UncoveredJsonInfo) obj).coverage)
                && AssistUtils.equals(validLineNumber, ((UncoveredJsonInfo) obj).validLineNumber)
                && AssistUtils.equals(testedLineNumber, ((UncoveredJsonInfo) obj).testedLineNumber)
                && AssistUtils.equals(detail, ((UncoveredJsonInfo) obj).detail);

    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(coverage, validLineNumber, testedLineNumber, detail);
    }
}
