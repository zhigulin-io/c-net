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
}
