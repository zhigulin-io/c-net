package ru.drifles.app.cleaning.goal;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {
    private static final long PRIMARY_GOAL_ID = 1L;

    private final GoalRepository repository;

    public GoalService(GoalRepository repository) {
        this.repository = repository;
    }

    public Goal getPrimaryGoal() {
        var goalOptional = repository.findById(PRIMARY_GOAL_ID);
        return goalOptional.orElseThrow(
                () -> new RuntimeException("Could not find primary goal by id (" + PRIMARY_GOAL_ID + ")")
        );
    }

    public Iterable<Goal> getAllGoals() {
        return repository.findAll();
    }

    public Iterable<Goal> getAllGoalsExcludePrimary() {
        return repository.findAllExcludePrimary();
    }
    
    public Optional<Goal> getGoalById(Long id) {
        return repository.findById(id);
    }

    public void updateGoal(Long id, String goal, Integer scoreCap) {
        var entity = repository.findById(id).orElseThrow();
        entity.setGoal(goal);
        entity.setScoreCap(scoreCap);
        repository.save(entity);
    }

    public boolean doTransaction(Long sourceId, Long destinationId, Integer amount) {
        if (sourceId.equals(destinationId)) {
            return false;
        }

        var source = repository.findById(sourceId).orElseThrow();
        var destination = repository.findById(destinationId).orElseThrow();

        if (source.currentScore() >= amount && amount + destination.currentScore() <= destination.scoreCap()) {
            source.setCurrentScore(source.currentScore() - amount);
            destination.setCurrentScore(destination.currentScore() + amount);

            if (destination.currentScore() >= destination.scoreCap())
                destination.setStatus(true);
            if (source.currentScore() < source.scoreCap())
                source.setStatus(false);

            repository.saveAll(List.of(source, destination));
            return true;
        }
        
        return false;
    }

    public Goal createGoal(String goal, Integer scoreCap) {
        var entity = Goal.builder()
                .setGoal(goal)
                .setScoreCap(scoreCap)
                .build();
        return repository.save(entity);
    }

    public void deleteGoalById(Long id) {
        repository.deleteById(id);
    }

    public void addScore(Integer score) {
        var goal = getPrimaryGoal();
        goal.setCurrentScore(goal.currentScore() + score);
        repository.save(goal);
    }
}
