package com.fr.concurrent.manager;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
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

    public Task take() {
        try {
            lock.lock();
            while (waitQueue.peek() == null) {
                condition.await();
            }
            Task task = waitQueue.remove();
            StaticLog.info(StrUtil.format("Task {} take from the wait queue , now size = {}", task.getGroup(), waitQueue.size()));
            return task;
        } catch (Exception ignore) {

        } finally {
            lock.unlock();
        }
        return null;
    }

    public void put(Task task) {
        try {
            lock.lock();
            for (Task t : waitQueue) {
                if (task.getGroup().equals(t.getGroup())) {
                    StaticLog.warn(StrUtil.format("Task {} already  in the  wait queue, now size = {}", task.getGroup(), waitQueue.size()));
                    return;
                }
            }
            for (Task t : runningQueue) {
                if (task.getGroup().equals(t.getGroup())) {
                    StaticLog.warn(StrUtil.format("Task {} already  in the  running queue, now size = {}", task.getGroup(), runningQueue.size()));
                    return;
                }
            }
            waitQueue.add(task);
            StaticLog.info(StrUtil.format("Task {} put in the  wait queue, now size = {}", task.getGroup(), waitQueue.size()));
            condition.notifyAll();
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

    @Override
    public void onStart(Task task) {

    }

    @Override
    public void onCalculate(Task task) {
        try {
            lock.lock();
            waitQueue.remove(task);
            runningQueue.add(task);
            StaticLog.info(StrUtil.format("Task {} put into  the running queue , now size = {}", task.getGroup(), runningQueue.size()));
        } catch (Exception ignore) {

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onComplete(Task task) {
        try {
            lock.lock();
            runningQueue.remove(task);
            StaticLog.info(StrUtil.format("Task {} remove from the running queue ,  now size = {}", task.getGroup(), runningQueue.size()));
        } catch (Exception ignore) {

        } finally {
            lock.unlock();
        }
    }
}
