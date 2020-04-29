package com.fr.coverage.concurrent.process;

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

    @Autowired
    public AsyncService(Manager manager) {
        this.manager = manager;
    }

    @Async("taskExecutor")
    public void execute() throws InterruptedException {
        Task task = manager.take();
        try {
            task.start();
            Calculate calculate = new RemoteCalculate(task.getFrom(), task.getTo());
            task.setCoverage(calculate.calTestCoverage());
        } catch (Exception e) {
            task.fail();
            task.setDetail(e.getMessage());
        } finally {
            task.complete();
        }
    }
}
