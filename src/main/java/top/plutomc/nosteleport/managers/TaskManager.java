package top.plutomc.nosteleport.managers;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import top.plutomc.nosteleport.tasks.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class TaskManager {
    private static ExecutorService taskExecutor;

    private TaskManager() {
    }

    public static void init() {
        taskExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactoryBuilder()
                .setNameFormat("NosTeleport Task Executor - %s")
                .build());
    }

    public static void submit(Task task) {
        taskExecutor.submit(task::run);
    }

    public static ExecutorService getTaskExecutor() {
        return taskExecutor;
    }

    public static void stop() {
        if (!taskExecutor.isShutdown()) {
            taskExecutor.shutdown();
        }
    }
}
