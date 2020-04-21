package com.fr.controller;

import com.fr.bean.RemoteInfo;
import com.fr.bean.ResponseInfo;
import com.fr.concurrent.RequestMap;
import com.fr.concurrent.manager.GitTask;
import com.fr.concurrent.manager.Manager;
import com.fr.concurrent.manager.Task;
import com.fr.concurrent.process.AsyncService;
import com.fr.git.helper.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hook")
public class HookController {

    private AsyncService asyncService;
    private RequestMap map;
    private Manager manager;

    @Autowired
    public HookController(AsyncService asyncService, RequestMap requestMap, Manager manager) {
        this.asyncService = asyncService;
        this.map = requestMap;
        this.manager = manager;
    }

    @RequestMapping("/coverage")
    public double calTestCoverage(@RequestParam(value = "from", required = true) String fromRef,
                                  @RequestParam(value = "to", required = true) String toRef) throws Exception {
        RemoteInfo from = Converter.RefStr2RemoteInfo(fromRef, true);
        RemoteInfo to = Converter.RefStr2RemoteInfo(toRef, false);
        Task task = new GitTask(manager, from, to);
        if (map.contains(task.getGroup())) {
            return map.get(task.getGroup()).getCoverage();
        }
        manager.put(task);
        map.put(task.getGroup(), new ResponseInfo(task.getStatus(), 0));
        asyncService.execute();
        return map.get(task.getGroup()).getCoverage();
    }
}
