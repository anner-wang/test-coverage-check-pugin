package com.fr.coverage.concurrent.manager;

public interface TaskListener {
    void onStart(Task task);

    void onComplete(Task task);
}
