package org.runelive.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class TaskManager {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);

    public static void submit(Task task) {
        EXECUTOR.submit(task);
    }

}