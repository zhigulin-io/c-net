package ru.drifles.app.cleaning.healthcheck;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckRepository extends CrudRepository<HealthCheckEntity, Long> {
}
