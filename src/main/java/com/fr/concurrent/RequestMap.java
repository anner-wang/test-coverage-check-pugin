package com.fr.concurrent;

import cn.hutool.log.StaticLog;
import com.fr.bean.ResponseInfo;
import com.fr.concurrent.manager.Task;
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
            StaticLog.info("update request {}  ---> {}  ", group, responseInfo);
        } else {
            StaticLog.info("A new request {} put in the request map", group);
            map.put(group, responseInfo);
        }
    }

    public boolean contains(String group) {
        return map.containsKey(group);
    }
}
