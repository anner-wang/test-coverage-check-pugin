package com.fr.coverage.core.concurrent.manager;


import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.fr.coverage.core.bean.RemoteInfo;
import com.fr.coverage.core.json.bean.UncoveredJsonInfo;
import com.fr.stable.AssistUtils;

public class GitTask implements Task {
    private String latestCommitId;

    private RemoteInfo from;
    private RemoteInfo to;

    private TaskStatus status;
    private double coverage;
    private String detail;

    private UncoveredJsonInfo uncoveredJsonInfo;

    private TaskListener taskListener;

    public GitTask(TaskListener taskListener, String latestCommitId, RemoteInfo from, RemoteInfo to) {
        this.taskListener = taskListener;
        this.from = from;
        this.to = to;
        this.latestCommitId = latestCommitId;
        this.status = TaskStatus.WAIT;
        this.coverage = 0;
        this.detail = StrUtil.EMPTY;
    }


    public RemoteInfo getFrom() {
        return from;
    }

    public RemoteInfo getTo() {
        return to;
    }

    @Override
    public double getCoverage() {
        return coverage;
    }

    @Override
    public void setCoverage(double coverage) {
        StaticLog.warn(StrUtil.format("update {}  coverage = {}", latestCommitId, coverage));
        this.coverage = coverage;
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public void setDetail(String detail) {
        StaticLog.info(StrUtil.format("update {} detail = {}", latestCommitId, detail));
        this.detail = detail;
    }

    @Override
    public UncoveredJsonInfo getUncoveredJsonInfo() {
        return uncoveredJsonInfo;
    }

    @Override
    public void setUncoveredJsonInfo(UncoveredJsonInfo uncoveredJsonInfo) {
        this.uncoveredJsonInfo = uncoveredJsonInfo;
    }

    @Override
    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(TaskStatus taskStatus) {
        StaticLog.info(StrUtil.format(" update={} status = {}", latestCommitId, status));
        status = taskStatus;
    }

    @Override
    public String getId() {
        return latestCommitId;
    }

    @Override
    public String getGroup() {
        return StrUtil.format("{}-{}", to.getRepoName(), to.getBranchName());
    }

    @Override
    public void start() {
        status = TaskStatus.RUNNING;
        taskListener.onStart(this);
    }

    @Override
    public void fail() {
        status = TaskStatus.FAIL;
        taskListener.onFail(this);
    }

    @Override
    public void complete() {
        status = status == TaskStatus.FAIL ? status : TaskStatus.FINISH;
        taskListener.onComplete(this);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GitTask
                && AssistUtils.equals(this.to, ((GitTask) obj).to)
                && AssistUtils.equals(this.from, ((GitTask) obj).from)
                && AssistUtils.equals(this.status, ((GitTask) obj).status)
                && AssistUtils.equals(this.latestCommitId, ((GitTask) obj).latestCommitId)
                && AssistUtils.equals(this.taskListener, ((GitTask) obj).taskListener);
    }

    @Override
    public int hashCode() {
        return AssistUtils.hashCode(from, to, latestCommitId, status, taskListener);
    }
}
