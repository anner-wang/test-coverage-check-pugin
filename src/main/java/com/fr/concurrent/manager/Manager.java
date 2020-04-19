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
    private Queue<Task> queue = new LinkedList<>();

    private final Lock lock = new ReentrantLock(true);
    private final Condition condition = lock.newCondition();

    @Override
    public void onComplete(Task task) {
        try {
            lock.lock();
            queue.remove(task);
            StaticLog.info(StrUtil.format("Task {} remove from the manager queue", task.getGroup()));
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onStart(Task task) {
        try {
            lock.lock();
            queue.add(task);
            StaticLog.info(StrUtil.format("Task {} put in the manager queue", task.getGroup()));
            condition.notifyAll();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public Task take() throws InterruptedException {
        try {
            lock.lock();
            while (isEmpty()) {
                condition.await();
            }
            Task task = get();
            task.setStatus(TaskStatus.RUNNING);
            StaticLog.info(StrUtil.format("Task {} take from the manager queue", task.getGroup()));
            return task;
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return null;
    }

    public int waitTaskNumber() {
        try {
            lock.lock();
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        try {
            lock.lock();
            for (Task task : queue) {
                if (task.getStatus() == TaskStatus.WAIT) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return true;
    }

    private Task get() {
        try {
            lock.lock();
            for (Task task : queue) {
                if (task.getStatus() == TaskStatus.WAIT) {
                    return task;
                }
            }
            return null;
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return null;
    }
}
