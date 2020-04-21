package com.fr.concurrent.manager;


import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.StrUtil;
import com.fr.bean.RemoteInfo;

public class GitTask implements Task {
    private RemoteInfo from;
    private RemoteInfo to;
    private TaskStatus status;

    private TaskListener taskListener;

    public GitTask(TaskListener taskListener, RemoteInfo from, RemoteInfo to) {
        this.taskListener = taskListener;
        this.from = from;
        this.to = to;
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
    public String getGroup() {
        long hash = HashUtil.mixHash(StrUtil.format("{}-{}-{}", from.getUserName(), from.getRemoteURL(), to.getRemoteURL()));
        return String.valueOf(Math.abs(hash));
    }

    @Override
    public void start() {
        status = TaskStatus.WAIT;
        taskListener.onStart(this);
    }

    @Override
    public void calculate() {
        status=TaskStatus.RUNNING;
        taskListener.onCalculate(this);
    }

    @Override
    public void complete() {
        status = TaskStatus.FINISH;
        taskListener.onComplete(this);
    }
}
