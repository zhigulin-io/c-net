package ru.drifles.app.cleaning.schedule;

import ru.drifles.app.cleaning.task.Task;

import java.util.HashMap;
import java.util.Map;

public class Schedule {
    private int markedNumber = 0;
    private final Map<Task, Boolean> taskStatusMap = new HashMap<>();

    public void addTask(Task task) {
        taskStatusMap.put(task, false);
    }

    public Map<Task, Boolean> getTaskStatusMap() {
        return taskStatusMap;
    }

    public void updateTaskStatus(Task task) {
        var status = taskStatusMap.get(task);
        System.out.println("OLD STATUS = " + taskStatusMap.get(task));
        if (status != null) {
            taskStatusMap.replace(task, !status);
            markedNumber += status ? 1 : -1;
        }
        System.out.println("NEW STATUS = " + taskStatusMap.get(task));
    }

    public boolean containsTask(Task task) {
        return taskStatusMap.containsKey(task);
    }

    public int getMarkedNumber() {
        return markedNumber;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "taskStatusMap=" + taskStatusMap +
                '}';
    }
}
