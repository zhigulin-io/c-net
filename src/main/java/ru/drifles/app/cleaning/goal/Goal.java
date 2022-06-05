package ru.drifles.app.cleaning.goal;

import javax.persistence.*;

@Entity
@Table(name = "goal", schema = "public")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goal", nullable = false, length = 30)
    private String goal;

    @Column(name = "score_cap", nullable = false)
    private Integer scoreCap;

    @Column(name = "current_score", nullable = false)
    private Integer currentScore;

    @Column(name = "status", nullable = false)
    private Boolean status;

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String goal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Integer scoreCap() {
        return scoreCap;
    }

    public void setScoreCap(Integer scoreCap) {
        this.scoreCap = scoreCap;
    }

    public Integer currentScore() {
        return currentScore;
    }

    public void setCurrentScore(Integer currentScore) {
        this.currentScore = currentScore;
    }

    public Boolean status() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private String goal;
        private int scoreCap;
        private int currentScore;
        private boolean status;

        private Builder() {}

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setGoal(String goal) {
            this.goal = goal;
            return this;
        }

        public Builder setScoreCap(int scoreCap) {
            this.scoreCap = scoreCap;
            return this;
        }

        public Builder setCurrentScore(int currentScore) {
            this.currentScore = currentScore;
            return this;
        }

        public Builder setStatus(boolean status) {
            this.status = status;
            return this;
        }

        public Goal build() {
            var goal = new Goal();
            goal.id = this.id;
            goal.goal = this.goal;
            goal.scoreCap = this.scoreCap;
            goal.currentScore = this.currentScore;
            goal.status = this.status;
            return goal;
        }
    }
}
