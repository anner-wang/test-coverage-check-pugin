package com.fr.coverage.concurrent.manager;

import com.fr.coverage.bean.RemoteInfo;

public interface Task {
    RemoteInfo getFrom();

    RemoteInfo getTo();

    TaskStatus getStatus();

    void setStatus(TaskStatus taskStatus);

    String getId();

    void start();

    void complete();
}
