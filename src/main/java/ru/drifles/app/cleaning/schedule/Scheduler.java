package ru.drifles.app.cleaning.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.drifles.app.cleaning.address.Address;
import ru.drifles.app.cleaning.address.AddressRepository;
import ru.drifles.app.cleaning.task.Task;
import ru.drifles.app.cleaning.task.TaskRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Scheduled(cron = "@daily")
    public void updateSchedules() {
        // TODO: Обновление задач

        // Очистка расписания
        schedules.clear();

        // Заполнение новыми тасками
        var addresses = addressRepository.findAll();
        addresses.forEach(address -> {
            var tasks = taskRepository.findTasksByAddressIdOrderByPriority(address.getId());
            if (tasks.size() != 0) {
                var schedule = new Schedule();
                int totalDuration = 0;

                for (var task : tasks)
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
