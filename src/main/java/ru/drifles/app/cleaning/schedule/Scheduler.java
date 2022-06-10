package ru.drifles.app.cleaning.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.drifles.app.cleaning.address.Address;
import ru.drifles.app.cleaning.address.AddressService;
import ru.drifles.app.cleaning.goal.GoalService;
import ru.drifles.app.cleaning.task.Task;
import ru.drifles.app.cleaning.task.TaskService;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class Scheduler {

    private static int minutesPerDay = 20;

    private final TaskService taskService;
    private final AddressService addressService;
    private final GoalService goalService;

    private final Map<Address, Schedule> schedules;

    public Scheduler(TaskService taskService, AddressService addressService, GoalService goalService) {
        this.taskService = taskService;
        this.addressService = addressService;
        this.goalService = goalService;
        this.schedules = new HashMap<>();

        updateSchedules();
    }

    public void updateTaskStatus(Task task) {
        schedules.get(task.getRoom().getAddress()).updateTaskStatus(task);
    }

    public void deleteTask(Task task) {
        if (isTaskEditable(task)) {
            taskService.deleteTask(task);
            recreateSchedule(task.getRoom().getAddress());
        }
    }

    public Task updateTask(Task task) {
        var actual = taskService.getTaskById(task.getId());
        if (isTaskEditable(actual)) {
            var updatedTask = taskService.updateTask(task);
            recreateSchedule(actual.getRoom().getAddress());
            return updatedTask;
        }
        return null;
    }

    public boolean isTaskEditable(Task task) {
        var address = task.getRoom().getAddress();
        var schedule = schedules.get(address);
        if (schedule == null)
            return true;
        return !schedule.containsTask(task) || schedule.getMarkedNumber() == 0;
    }

    @Scheduled(cron = "0 */10 * * * *")
    @Transactional
    public void updateSchedules() {
        updateTasks();
        schedules.clear();
        addressService.getAll().forEach(this::recreateSchedule);
    }

    public Map<Address, Schedule> getSchedules() {
        return schedules;
    }

    private void updateTasks() {
        var tasks = taskService.getAll();
        tasks.forEach(task -> {
            var schedule = schedules.get(task.getRoom().getAddress());

            var newLastCompleted = task.getLastCompleted() + 1;
            var newPriority = task.getCurrentPriority();

            if (schedule != null) {
                var status = schedule.getTaskStatusMap().get(task);
                if (status != null) {
                    if (status) {
                        goalService.addScore(task.getScore());
                        newLastCompleted = 1;
                        newPriority = task.getDefaultPriority();
                    } else {
                        newPriority++;
                    }
                }
            }

            task.setLastCompleted(newLastCompleted);
            task.setCurrentPriority(newPriority);
        });
        taskService.saveAll(tasks);
    }

    private void recreateSchedule(Address address) {
        var taskList = taskService.getAllForAddressBasedOnPriority(address);

        if (taskList.size() != 0) {
            var schedule = new Schedule();
            int totalDuration = 0;

            for (var task : taskList)
                if (totalDuration + task.getDuration() <= minutesPerDay)
                    schedule.addTask(task);

            schedules.put(address, schedule);
        }
    }
}
