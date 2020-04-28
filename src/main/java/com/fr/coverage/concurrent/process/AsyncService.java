package com.fr.coverage.concurrent.process;

import com.fr.coverage.concurrent.RequestMap;
import com.fr.coverage.concurrent.manager.Manager;
import com.fr.coverage.concurrent.manager.Task;
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
    public void execute() throws InterruptedException {
        Task task = manager.take();
        double coverage = 0;
        try {
            task.start();
            map.updateStatus(task.getId(), task.getStatus());
            Calculate calculate = new RemoteCalculate(task.getFrom(), task.getTo());
            coverage = calculate.calTestCoverage();
            map.updateStatus(task.getId(), task.getStatus());
        } catch (Exception e) { 
            task.fail();
            map.updateStatus(task.getId(), task.getStatus());
            map.updateMessage(task.getId(), e.getMessage());
        } finally {
            task.complete();
            map.updateCoverage(task.getId(), coverage);
        }
    }
}
