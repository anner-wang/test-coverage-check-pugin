package com.fr.coverage.manager;

import com.fr.coverage.bean.RemoteInfo;
import com.fr.coverage.concurrent.manager.GitTask;
import com.fr.coverage.concurrent.manager.Manager;
import com.fr.coverage.concurrent.manager.Task;
import junit.framework.TestCase;
import org.junit.Assert;

public class ManagerTest extends TestCase {

    private Manager manager;
    private Task t1;
    private Task t2;
    private Task t3;
    private Task t4;

    @Override
    protected void setUp() throws Exception {
        manager = new Manager();

        RemoteInfo from1 = new RemoteInfo();
        RemoteInfo from2 = new RemoteInfo();
        RemoteInfo from3 = new RemoteInfo();
        RemoteInfo to1 = new RemoteInfo();
        RemoteInfo to2 = new RemoteInfo();
        RemoteInfo to3 = new RemoteInfo();

        t1 = new GitTask(manager, "user1-commit", from1, to1);
        t2 = new GitTask(manager, "user2-commit", from2, to2);
        t3 = new GitTask(manager, "user3-commit", from3, to3);
        t4 = new GitTask(manager, "user1-commit", from3, to3);
    }

    public void testTake() {
        // 测试一次put 和 take
        manager.put(t1);
        Assert.assertEquals(1, manager.getWaitNumber());
        Task tt1 = manager.tryTake();
        Assert.assertEquals(t1, tt1);
        Assert.assertEquals(0, manager.getWaitNumber());
        Assert.assertNull(manager.tryTake());

        // 测试put相同的id的task
        manager.put(t4);
        tt1.start();
        Assert.assertEquals(1, manager.getWaitNumber());
        // 虽然是两个对象（两次请求就会出现两个不同的对象，但是id相同），但是相同的commitId，所以这个时候不可以take
        Assert.assertNull(manager.tryTake());

        // 测试上一个相同id的任务完成，现在可以正常的take了
        tt1.complete();
        Task tt2 = manager.tryTake();
        Assert.assertNotNull(tt2);

        // 状态不一致
        Assert.assertNotEquals(tt2, tt1);
        Assert.assertEquals(0, manager.getWaitNumber());
        tt2.complete();
        Assert.assertEquals(0, manager.getRunningNumber());
    }

    public void testPut() {
        // 测试一个put
        manager.put(t1);
        Assert.assertEquals(1,manager.getWaitNumber());
        Assert.assertEquals(0,manager.getRunningNumber());
        // 任务没有处理，继续put
        manager.put(t2);
        Assert.assertEquals(2,manager.getWaitNumber());
        Assert.assertEquals(0,manager.getRunningNumber());
        // put一个相同id的task
        manager.put(t4);
        Assert.assertEquals(3,manager.getWaitNumber());
        Assert.assertEquals(0,manager.getRunningNumber());
        // 任务开始处理
        Task tt1=manager.tryTake();
        Assert.assertEquals(t1, tt1);
        Assert.assertEquals(2,manager.getWaitNumber());
        Assert.assertEquals(0,manager.getRunningNumber());
        tt1.start();
        Assert.assertEquals(2,manager.getWaitNumber());
        Assert.assertEquals(1,manager.getRunningNumber());
        // 继续put
        manager.put(t3);
        Assert.assertEquals(3,manager.getWaitNumber());
        Assert.assertEquals(1,manager.getRunningNumber());
    }
}