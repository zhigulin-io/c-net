package ru.drifles.app.cleaning.task;

import javax.persistence.*;

@Entity
@Table(name = "task", schema = "public")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room", nullable = false, length = 30)
    private String task;

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

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String task() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Integer period() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer duration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer score() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer defaultPriority() {
        return defaultPriority;
    }

    public void setDefaultPriority(Integer defaultPriority) {
        this.defaultPriority = defaultPriority;
    }

    public Integer currentPriority() {
        return currentPriority;
    }

    public void setCurrentPriority(Integer currentPriority) {
        this.currentPriority = currentPriority;
    }

    public Integer lastCompleted() {
        return lastCompleted;
    }

    public void setLastCompleted(Integer lastCompleted) {
        this.lastCompleted = lastCompleted;
    }

    public Long roomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String task;
        private Integer period;
        private Integer duration = 10;
        private Integer score = 5;
        private Integer defaultPriority = 1;
        private Integer currentPriority = 1;
        private Integer lastCompleted = 0;
        private Long roomId;

        private Builder() {}

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setTask(String task) {
            this.task = task;
            return this;
        }

        public Builder setPeriod(Integer period) {
            this.period = period;
            return this;
        }

        public Builder setDuration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public Builder setScore(Integer score) {
            this.score = score;
            return this;
        }

        public Builder setDefaultPriority(Integer defaultPriority) {
            this.defaultPriority = defaultPriority;
            return this;
        }

        public Builder setCurrentPriority(Integer currentPriority) {
            this.currentPriority = currentPriority;
            return this;
        }

        public Builder setLastCompleted(Integer lastCompleted) {
            this.lastCompleted = lastCompleted;
            return this;
        }

        public Builder setRoomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public Task build() {
            var entity = new Task();
            entity.id = this.id;
            entity.task = this.task;
            entity.period = this.period;
            entity.duration = this.duration;
            entity.score = this.score;
            entity.defaultPriority = this.defaultPriority;
            entity.currentPriority = this.currentPriority;
            entity.lastCompleted = this.lastCompleted;
            entity.roomId = this.roomId;
            return entity;
        }
    }
}
