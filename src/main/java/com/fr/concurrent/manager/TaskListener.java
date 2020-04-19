package com.fr.concurrent.manager;

public interface TaskListener {

    void onComplete(Task task);

    void onStart(Task task);

}
