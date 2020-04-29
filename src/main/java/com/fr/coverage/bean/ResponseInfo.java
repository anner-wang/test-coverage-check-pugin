package com.fr.coverage.bean;

import cn.hutool.core.util.StrUtil;
import com.fr.coverage.concurrent.manager.Task;
import com.fr.coverage.concurrent.manager.TaskStatus;
import com.fr.stable.AssistUtils;

public class ResponseInfo {
    private double coverage;
    private String status;
    private String detail;

    public ResponseInfo() {
        this.coverage = 0;
        this.status = TaskStatus.WAIT.toString();
        this.detail = StrUtil.EMPTY;
    }

    public ResponseInfo(Task task) {
        this.coverage = task.getCoverage();
        this.status = task.getStatus().toString();
        this.detail = task.getDetail();
    }

    public double getCoverage() {
        return coverage;
    }

    public void setCoverage(double coverage) {
        this.coverage = coverage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ResponseInfo{" +
                "coverage=" + coverage +
                ", status='" + status + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ResponseInfo
                && AssistUtils.equals(status, ((ResponseInfo) obj).status)
                && AssistUtils.equals(coverage, ((ResponseInfo) obj).coverage)
                && AssistUtils.equals(detail, ((ResponseInfo) obj).detail);
    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(status, coverage, detail);
    }
}