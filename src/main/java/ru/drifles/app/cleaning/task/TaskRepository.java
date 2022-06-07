package ru.drifles.app.cleaning.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.room.address.id = :id " +
            "AND t.lastCompleted >= t.period " +
            "ORDER BY t.currentPriority DESC")
    List<Task> findTasksByAddressIdOrderByPriority(Long id);
}
