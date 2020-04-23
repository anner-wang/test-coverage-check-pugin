package com.fr.coverage.concurrent;

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

    public void put(String group, ResponseInfo responseInfo) {
        if (map.containsKey(group)) {
            map.put(group, responseInfo);
            if (responseInfo.getStatus() == TaskStatus.FINISH) {

            }
            StaticLog.info("Update request {}  ---> {}  ", group, responseInfo);
        } else {
            map.put(group, responseInfo);
            StaticLog.info("A new request {} put in the request map", group);
        }
    }

    public boolean contains(String group) {
        return map.containsKey(group);
    }
}
