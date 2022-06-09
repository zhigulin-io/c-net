package ru.drifles.app.cleaning.task;

import ru.drifles.app.cleaning.room.Room;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tasks", schema = "public")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "task", nullable = false, length = 30)
    private String taskName;

    @Column(name = "repeat_in", nullable = false)
    private Integer period;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "default_priority", nullable = false)
    private Integer defaultPriority;

    @Column(name = "current_priority", nullable = false)
    private Integer currentPriority;

    @Column(name = "lastCompleted", nullable = false)
    private Integer lastCompleted;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Transient
    private boolean editable;

    public Task() {
    }

    public Task(String taskName, Integer period, Integer duration, Integer score, Integer defaultPriority) {
        this.taskName = taskName;
        this.period = period;
        this.duration = duration;
        this.score = score;
        this.defaultPriority = defaultPriority;
        this.currentPriority = defaultPriority;
        this.lastCompleted = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getDefaultPriority() {
        return defaultPriority;
    }

    public void setDefaultPriority(Integer defaultPriority) {
        this.defaultPriority = defaultPriority;
    }

    public Integer getCurrentPriority() {
        return currentPriority;
    }

    public void setCurrentPriority(Integer currentPriority) {
        this.currentPriority = currentPriority;
    }

    public Integer getLastCompleted() {
        return lastCompleted;
    }

    public void setLastCompleted(Integer lastCompleted) {
        this.lastCompleted = lastCompleted;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", period=" + period +
                ", duration=" + duration +
                ", score=" + score +
                ", defaultPriority=" + defaultPriority +
                ", currentPriority=" + currentPriority +
                ", lastCompleted=" + lastCompleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id)
                && taskName.equals(task.taskName)
                && period.equals(task.period)
                && duration.equals(task.duration)
                && score.equals(task.score)
                && defaultPriority.equals(task.defaultPriority)
                && currentPriority.equals(task.currentPriority)
                && lastCompleted.equals(task.lastCompleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, period, duration, score, defaultPriority, currentPriority, lastCompleted);
    }
}
