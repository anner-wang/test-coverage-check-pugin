package com.fr.coverage.concurrent.manager;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.fr.coverage.concurrent.RequestMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class Manager implements TaskListener {
    private Queue<Task> waitQueue = new LinkedList<>();
    private Queue<Task> runningQueue = new LinkedList<>();

    private final Lock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();

    public Task tryTake() {
        try {
            if (lock.tryLock()) {
                if (waitQueue.peek() == null) {
                    return null;
                }
                Task task = waitQueue.peek();
                if (isSameRunningTaskExist(task)) {
                    return null;
                }
                waitQueue.poll();
                return task;
            }
        } catch (Exception ignore) {

        } finally {
            lock.unlock();
        }
        return null;
    }

    public Task take() throws InterruptedException {
        Task task;
        try {
            lock.lock();
            while ((task = tryTake()) == null) {
                condition.await();
            }
            return task;
        } finally {
            lock.unlock();
        }
    }

    public void put(Task task) {
        try {
            lock.lock();
            waitQueue.add(task);
            StaticLog.info(StrUtil.format("Task {} put in the  wait queue, now size = {}", task.getId(), waitQueue.size()));
            condition.signalAll();
        } catch (Exception ignore) {

        } finally {
            lock.unlock();
        }
    }


    @Override
    public void onStart(Task task) {
        try {
            lock.lock();
            runningQueue.add(task);
            StaticLog.info(StrUtil.format("Task {} put into  the running queue , now size = {}", task.getId(), runningQueue.size()));
        } catch (Exception ignore) {

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onFail(Task task) {
    }

    @Override
    public void onComplete(Task task) {
        try {
            lock.lock();
            runningQueue.remove(task);
            condition.signal();
            StaticLog.info(StrUtil.format("Task {} remove from the running queue ,  now size = {}", task.getId(), runningQueue.size()));
        } catch (Exception ignore) {
        } finally {
            lock.unlock();
        }
    }

    public int getWaitNumber() {
        try {
            lock.lock();
            return waitQueue.size();
        } catch (Exception ignore) {

        } finally {
            lock.unlock();
        }
        return -1;
    }

    public int getRunningNumber() {
        try {
            lock.lock();
            return runningQueue.size();
        } catch (Exception ignore) {

        } finally {
            lock.unlock();
        }
        return -1;
    }

    private boolean isSameRunningTaskExist(Task task) {
        for (Task runningTask : runningQueue) {
            if (runningTask.getGroup().equals(task.getGroup())) {
                return true;
            }
        }
        return false;
    }
}
