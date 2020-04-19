package com.fr.concurrent.process;

import com.fr.bean.ResponseInfo;
import com.fr.concurrent.RequestMap;
import com.fr.concurrent.manager.Manager;
import com.fr.concurrent.manager.Task;
import com.fr.git.Calculate;
import com.fr.git.RemoteCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncService  {
    private Manager manager;
    private RequestMap map;

    @Autowired
    public AsyncService(Manager manager, RequestMap map) {
        this.manager = manager;
        this.map = map;
    }

    @Async("taskExecutor")
    public void execute() throws Exception {
        Task task = manager.take();
        Calculate calculate = new RemoteCalculate(task.getFrom(), task.getTo());
        double coverage = calculate.calTestCoverage();
        task.complete();
        map.put(task.getGroup(), new ResponseInfo(task.getStatus(), coverage));
    }
}
