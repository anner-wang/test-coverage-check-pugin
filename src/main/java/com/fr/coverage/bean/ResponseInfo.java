package com.fr.coverage.bean;

import com.fr.coverage.concurrent.manager.TaskStatus;
import com.fr.stable.AssistUtils;

public class ResponseInfo {
    private TaskStatus status;
    private double coverage;
    private boolean isAlive;

    public ResponseInfo(TaskStatus status, double coverage) {
        this.status = status;
        this.coverage = coverage;
        this.isAlive = true;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public double getCoverage() {
        return coverage;
    }

    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    public void finish() {
        isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "status=" + status +
                ", coverage=" + coverage +
                ", isAlive=" + isAlive +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ResponseInfo
                && AssistUtils.equals(status, ((ResponseInfo) obj).status)
                && AssistUtils.equals(coverage, ((ResponseInfo) obj).coverage)
                && AssistUtils.equals(isAlive, ((ResponseInfo) obj).isAlive);
    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(status, coverage, isAlive);
    }
}
