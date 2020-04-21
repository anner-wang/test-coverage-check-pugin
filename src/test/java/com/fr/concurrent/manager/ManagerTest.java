package com.fr.concurrent.manager;

import com.fr.bean.RemoteInfo;
import junit.framework.TestCase;
import org.junit.Assert;

public class ManagerTest extends TestCase {

    public void testManager() throws InterruptedException {
        Manager manager = new Manager();

        RemoteInfo from1 = new RemoteInfo();
        from1.setUserName("user1");
        RemoteInfo from2 = new RemoteInfo();
        from2.setUserName("user2");
        RemoteInfo from3 = new RemoteInfo();
        from3.setUserName("user3");
        RemoteInfo to1 = new RemoteInfo();
        RemoteInfo to2 = new RemoteInfo();
        RemoteInfo to3 = new RemoteInfo();

        Task t1 = new GitTask(manager, from1, to1);
        Task t2 = new GitTask(manager, from2, to2);
        Task t3 = new GitTask(manager, from3, to3);

        manager.put(t1);
        Assert.assertEquals(1, manager.getWaitNumber());
        Assert.assertEquals(0, manager.getRunningNumber());
        Task t =manager.take();
        t.calculate();
        Assert.assertEquals(0, manager.getWaitNumber());
        Assert.assertEquals(1, manager.getRunningNumber());
        t.complete();
        Assert.assertEquals(0, manager.getWaitNumber());
        Assert.assertEquals(0, manager.getRunningNumber());

        manager.put(t2);
        manager.put(t3);
        Assert.assertEquals(2, manager.getWaitNumber());
        Assert.assertEquals(0, manager.getRunningNumber());
        t = manager.take();
        Assert.assertEquals(t2.getGroup(), t.getGroup());
        t.calculate();
        Assert.assertEquals(1, manager.getWaitNumber());
        Assert.assertEquals(1, manager.getRunningNumber());
        t.complete();
        Assert.assertEquals(1, manager.getWaitNumber());
        Assert.assertEquals(0, manager.getRunningNumber());
        t = manager.take();
        Assert.assertEquals(t3.getGroup(), t.getGroup());
        Assert.assertEquals(0, manager.getWaitNumber());
        Assert.assertEquals(0, manager.getRunningNumber());
        t.calculate();
        Assert.assertEquals(0, manager.getWaitNumber());
        Assert.assertEquals(1, manager.getRunningNumber());
        t.complete();
        Assert.assertEquals(0, manager.getWaitNumber());
        Assert.assertEquals(0, manager.getRunningNumber());
    }

}