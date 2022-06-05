package ru.drifles.app.cleaning.healthcheck;

import javax.persistence.*;

@Entity
@Table(name = "health_check", schema = "public")
public class HealthCheckEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "status", nullable = false)
    public String status;

    public Long id() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String status() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String status;

        private Builder() {}

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public HealthCheckEntity build() {
            var result = new HealthCheckEntity();
            result.id = this.id;
            result.status = this.status;
            return result;
        }
    }
}
