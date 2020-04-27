package com.fr.coverage.concurrent.process;

import cn.hutool.core.util.StrUtil;
import com.fr.coverage.bean.ResponseInfo;
import com.fr.coverage.concurrent.RequestMap;
import com.fr.coverage.concurrent.manager.Manager;
import com.fr.coverage.concurrent.manager.Task;
import com.fr.coverage.concurrent.manager.TaskStatus;
import com.fr.coverage.git.Calculate;
import com.fr.coverage.git.RemoteCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncService {
    private Manager manager;
    private RequestMap map;

    @Autowired
    public AsyncService(Manager manager, RequestMap map) {
        this.manager = manager;
        this.map = map;
    }

    @Async("taskExecutor")
    public void execute() {
        Task task = null;
        try {
            task = manager.take();
            task.start();
            map.updateStatus(task.getId(), task.getStatus());
            Calculate calculate = new RemoteCalculate(task.getFrom(), task.getTo());
            double coverage = calculate.calTestCoverage();
            task.complete();
            map.updateCoverage(task.getId(), coverage);
            map.updateStatus(task.getId(), task.getStatus());
        } catch (Exception e) {
            map.updateStatus(task.getId(), TaskStatus.FAIL);
            map.updateMessage(task.getId(), e.getMessage());
        }
    }
}
