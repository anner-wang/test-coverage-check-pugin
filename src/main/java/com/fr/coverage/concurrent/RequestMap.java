package com.fr.coverage.concurrent;

import cn.hutool.log.StaticLog;
import com.fr.coverage.concurrent.manager.Task;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestMap {
    private Map<String, Task> map = new ConcurrentHashMap<>();

    public Task get(String requestStr) {
        return map.get(requestStr);
    }


    public void put(String id, Task task) {
        map.put(id, task);
        StaticLog.info("A new request {} put in the request map", id);
    }

    public boolean contains(String group) {
        return map.containsKey(group);
    }
}
