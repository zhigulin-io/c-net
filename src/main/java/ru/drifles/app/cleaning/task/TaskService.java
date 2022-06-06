package ru.drifles.app.cleaning.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.drifles.app.cleaning.room.Room;

@Service
public class TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(Task preparedTask, Long roomId) {
        var room = new Room(null);
        room.setId(roomId);
        preparedTask.setRoom(room);
        return repository.save(preparedTask);
    }

    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Task updateTask(Task newTask) {
        var actual = repository.findById(newTask.getId()).orElseThrow();

        if (newTask.getTaskName() != null)
            actual.setTaskName(newTask.getTaskName());

        if (newTask.getDuration() != null)
            actual.setDuration(newTask.getDuration());

        if (newTask.getPeriod() != null)
            actual.setPeriod(newTask.getPeriod());

        if (newTask.getDefaultPriority() != null)
            actual.setDefaultPriority(newTask.getDefaultPriority());

        if (newTask.getScore() != null)
            actual.setScore(newTask.getScore());

        return repository.save(actual);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }
}
