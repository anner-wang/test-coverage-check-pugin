package com.fr.coverage.core.bean;

import com.fr.stable.AssistUtils;

public class CodeLine {
    private String path;
    private int line;

    public CodeLine(String path, Integer line) {
        this.path = path;
        this.line = line;
    }

    public CodeLine() {
    }

    public String getPath() {
        return path;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "CodeLineBean{" +
                "path='" + path + '\'' +
                ", line=" + line +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CodeLine
                && AssistUtils.equals(this.path,((CodeLine)obj).path)
                && AssistUtils.equals(this.line,((CodeLine)obj).line);
    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(path, line);
    }
}
