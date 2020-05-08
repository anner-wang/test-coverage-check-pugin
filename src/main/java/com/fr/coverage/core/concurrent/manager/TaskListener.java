package com.fr.coverage.core.concurrent.manager;

public interface TaskListener {
    void onStart(Task task);

    void onFail(Task task);

    void onComplete(Task task);
}
