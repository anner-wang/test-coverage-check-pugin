package com.fr.coverage.concurrent.manager;


import cn.hutool.core.util.StrUtil;
import com.fr.coverage.bean.RemoteInfo;
import com.fr.stable.AssistUtils;

public class GitTask implements Task {
    private RemoteInfo from;
    private RemoteInfo to;
    private String latestCommitId;
    private TaskStatus status;

    private TaskListener taskListener;

    public GitTask(TaskListener taskListener, String latestCommitId, RemoteInfo from, RemoteInfo to) {
        this.taskListener = taskListener;
        this.from = from;
        this.to = to;
        this.latestCommitId = latestCommitId;
        this.status = TaskStatus.WAIT;
    }


    public RemoteInfo getFrom() {
        return from;
    }

    public RemoteInfo getTo() {
        return to;
    }

    @Override
    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(TaskStatus taskStatus) {
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
    public void complete() {
        status = TaskStatus.FINISH;
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
