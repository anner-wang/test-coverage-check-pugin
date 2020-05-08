package com.fr.coverage.core.concurrent.manager;


import com.fr.coverage.core.bean.RemoteInfo;
import com.fr.coverage.core.json.bean.UncoveredJsonInfo;

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

    UncoveredJsonInfo getUncoveredJsonInfo();

    void setUncoveredJsonInfo(UncoveredJsonInfo uncoveredJsonInfo);

    void start();

    void fail();

    void complete();
}
