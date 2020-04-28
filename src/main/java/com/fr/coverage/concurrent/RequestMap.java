package com.fr.coverage.concurrent;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.fr.coverage.bean.ResponseInfo;
import com.fr.coverage.concurrent.manager.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestMap {
    private Map<String, ResponseInfo> map = new ConcurrentHashMap<>();

    public ResponseInfo get(String requestStr) {
        return map.get(requestStr);
    }


    public void put(String id) {
        map.put(id, new ResponseInfo());
        StaticLog.info("A new request {} put in the request map", id);
    }

    public void updateStatus(String id, TaskStatus taskStatus) {
        ResponseInfo responseInfo = map.get(id);
        update(id, responseInfo.getCoverage(), taskStatus.toString(), responseInfo.getDetail());
        StaticLog.info("Update request {}  status -> {} ", id, taskStatus);
    }

    public void updateMessage(String id, String detail) {
        ResponseInfo responseInfo = map.get(id);
        update(id, responseInfo.getCoverage(), responseInfo.getStatus(), detail);
        StaticLog.info("Update request {}  message -> {} ", id, detail);
    }

    public void updateCoverage(String id, double coverage) {
        ResponseInfo responseInfo = map.get(id);
        update(id, coverage, responseInfo.getStatus(), responseInfo.getDetail());
        StaticLog.warn("Update request {}  coverage -> {} ", id, coverage);
    }

    private void update(String id, double coverage, String status, String detail) {
        ResponseInfo responseInfo = map.get(id);
        responseInfo.setCoverage(coverage);
        responseInfo.setDetail(detail);
        responseInfo.setStatus(status);
        map.put(id, responseInfo);
    }

    public boolean contains(String group) {
        return map.containsKey(group);
    }
}
