package com.fr.coverage.controller;

import cn.hutool.json.JSONUtil;
import com.fr.coverage.bean.RemoteInfo;
import com.fr.coverage.bean.ResponseInfo;
import com.fr.coverage.concurrent.RequestMap;
import com.fr.coverage.concurrent.manager.GitTask;
import com.fr.coverage.concurrent.manager.Manager;
import com.fr.coverage.concurrent.manager.Task;
import com.fr.coverage.concurrent.process.AsyncService;
import com.fr.coverage.git.helper.Converter;
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
    public String calTestCoverage(@RequestParam(value = "latestCommitId", required = true) String latestCommitId,
                                  @RequestParam(value = "from", required = true) String fromRef,
                                  @RequestParam(value = "to", required = true) String toRef) throws Exception {
        RemoteInfo from = Converter.RefStr2RemoteInfo(fromRef, true);
        RemoteInfo to = Converter.RefStr2RemoteInfo(toRef, false);
        Task task = new GitTask(manager, latestCommitId, from, to);
        if (map.contains(task.getId())) {
            return JSONUtil.toJsonStr(map.get(task.getId()));
        }
        manager.put(task);
        map.put(latestCommitId, task);
        asyncService.execute();
        return JSONUtil.toJsonStr(new ResponseInfo(map.get(task.getId())));
    }
}
