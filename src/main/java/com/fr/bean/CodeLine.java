package com.fr.bean;

import java.util.Objects;

/**
 * @description:
 * @author: Anner
 * @time: 2020/4/10 9:43
 */
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeLine codeLine = (CodeLine) o;
        return Objects.equals(path, codeLine.path) &&
                Objects.equals(line, codeLine.line);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, line);
    }
}
