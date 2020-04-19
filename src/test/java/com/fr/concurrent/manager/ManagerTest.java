package com.fr.concurrent.manager;

import com.fr.bean.RemoteInfo;
import org.junit.Assert;
import org.junit.Test;

public class ManagerTest {

    @Test
    public void testManager() throws InterruptedException {
        Manager manager = new Manager();

        RemoteInfo from = new RemoteInfo();
        RemoteInfo to = new RemoteInfo();

        Task t1 = new GitTask(manager, from, to);
        Task t2 = new GitTask(manager, from, to);
        Task t3 = new GitTask(manager, from, to);
        Task t4 = new GitTask(manager, from, to);

        t1.start();
        Assert.assertEquals(TaskStatus.WAIT, t1.getStatus());
        Assert.assertEquals(t1, manager.take());
        Assert.assertEquals(TaskStatus.RUNNING, t1.getStatus());
        Assert.assertTrue(manager.isEmpty());

        t2.start();
        Assert.assertEquals(2, manager.waitTaskNumber());
        Assert.assertFalse(manager.isEmpty());

        t1.complete();
        Assert.assertEquals(1, manager.waitTaskNumber());
        Assert.assertFalse(manager.isEmpty());
        Assert.assertEquals(t2, manager.take());
        Assert.assertEquals(true, manager.isEmpty());
        Assert.assertEquals(1, manager.waitTaskNumber());

        Assert.assertEquals(TaskStatus.FINISH, t1.getStatus());
        Assert.assertEquals(TaskStatus.RUNNING, t2.getStatus());

        t3.start();
        t4.start();
        t2.complete();
        Assert.assertEquals(2, manager.waitTaskNumber());
        Assert.assertEquals(t3, manager.take());
        Assert.assertEquals(t4, manager.take());
        Assert.assertTrue(manager.isEmpty());
    }

}