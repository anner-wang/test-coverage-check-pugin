package com.fr.concurrent.manager;

import com.fr.bean.RemoteInfo;

public interface Task {
    RemoteInfo getFrom();

    RemoteInfo getTo();

    TaskStatus getStatus();

    void setStatus(TaskStatus taskStatus);

    String getGroup();

    void start();

    void calculate();

    void complete();
}
