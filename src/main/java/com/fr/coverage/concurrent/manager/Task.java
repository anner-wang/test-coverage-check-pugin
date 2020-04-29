package com.fr.coverage.concurrent.manager;

import com.fr.coverage.bean.RemoteInfo;

public interface Task {
    RemoteInfo getFrom();

    RemoteInfo getTo();

    TaskStatus getStatus();

    void setStatus(TaskStatus taskStatus);

    String getId();

    String getGroup();

    double getCoverage();

    void setCoverage(double coverage);

    String getDetail();

    void setDetail(String detail);

    void start();

    void fail();

    void complete();
}
