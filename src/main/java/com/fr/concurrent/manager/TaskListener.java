package com.fr.concurrent.manager;

public interface TaskListener {
    void onStart(Task task);

    void onCalculate(Task task);

    void onComplete(Task task);
}
