package ru.drifles.app.cleaning.schedule;

import ru.drifles.app.cleaning.address.Address;
import ru.drifles.app.cleaning.task.Task;

import java.util.HashMap;
import java.util.Map;

public class Schedule {
    private final Map<Task, Boolean> taskStatusMap = new HashMap<>();

    public void addTask(Task task) {
        taskStatusMap.put(task, false);
    }

    public Map<Task, Boolean> getTaskStatusMap() {
        return taskStatusMap;
    }

    public void updateTaskStatus(Long taskId) {
        for (var entry : taskStatusMap.entrySet()) {
            if (entry.getKey().getId().equals(taskId)) {
                taskStatusMap.replace(entry.getKey(), !entry.getValue());
                break;
            }
        }
    }

    public boolean containsTask(Task task) {
        return taskStatusMap.containsKey(task);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "taskStatusMap=" + taskStatusMap +
                '}';
    }
}
