package com.fr.bean;

import com.fr.concurrent.manager.TaskStatus;

public class ResponseInfo {
    private TaskStatus status;
    private double coverage;

    public ResponseInfo(TaskStatus status, double coverage) {
        this.status = status;
        this.coverage = coverage;
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

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "status=" + status +
                ", coverage=" + coverage +
                '}';
    }
}
