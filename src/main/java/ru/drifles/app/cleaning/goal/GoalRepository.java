package ru.drifles.app.cleaning.goal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends CrudRepository<Goal, Long> {
    @Query("SELECT g FROM Goal g WHERE g.id > 1")
    Iterable<Goal> findAllExcludePrimary();
}
