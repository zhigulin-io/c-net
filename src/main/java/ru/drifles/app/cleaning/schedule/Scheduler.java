package ru.drifles.app.cleaning.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.drifles.app.cleaning.address.Address;
import ru.drifles.app.cleaning.address.AddressRepository;
import ru.drifles.app.cleaning.task.TaskRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
public class Scheduler {

    private final TaskRepository taskRepository;
    private final AddressRepository addressRepository;

    private final Map<Address, Schedule> schedules;

    public Scheduler(TaskRepository taskRepository, AddressRepository addressRepository) {
        this.taskRepository = taskRepository;
        this.addressRepository = addressRepository;
        this.schedules = new HashMap<>();

        updateSchedules();
    }

    public void updateTaskStatus(Long addressId, Long taskId) {
        schedules.forEach((address, schedule) -> {
            if (address.getId().equals(addressId)) {
                schedule.updateTaskStatus(taskId);
            }
        });
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void updateSchedules() {
        var tasks = taskRepository.findAll();
        tasks.forEach(task -> {
            var address = task.getRoom().getAddress();
            var schedule = schedules.get(address);
            if (schedule != null) {
                var status = schedule.getTaskStatusMap().get(task);
                System.out.println("KURWA: " + status);
                if (status != null) {
                    if (status) {
                        task.setLastCompleted(0);
                        task.setCurrentPriority(task.getDefaultPriority());
                    } else {
                        task.setCurrentPriority(task.getCurrentPriority() + 1);
                    }
                }
            }
            task.setLastCompleted(task.getLastCompleted() + 1);
        });
        taskRepository.saveAll(tasks);

        // Очистка расписания
        schedules.clear();

        // Заполнение новыми тасками
        var addresses = addressRepository.findAll();
        addresses.forEach(address -> {
            var taskList = taskRepository.findTasksByAddressIdOrderByPriority(address.getId());
            if (taskList.size() != 0) {
                var schedule = new Schedule();
                int totalDuration = 0;

                for (var task : taskList)
                    if (totalDuration + task.getDuration() <= 60)
                        schedule.addTask(task);

                schedules.put(address, schedule);
            }
        });
    }

    public Map<Address, Schedule> getSchedules() {
        return schedules;
    }
}
