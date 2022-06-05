package ru.drifles.app.cleaning.healthcheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "healthcheck")
public class HealthCheckController {

    private static final long STATUS_ID = 1L;

    private final HealthCheckRepository repository;

    @Autowired
    public HealthCheckController(HealthCheckRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String getHealthInfo(Model model) {
        var statusOptional = repository.findById(STATUS_ID);
        model.addAttribute(
                "status",
                statusOptional.orElse(HealthCheckEntity.builder().setId(-1L).setStatus("DB_ERROR").build())
        );
        return "healthcheckPage";
    }

}
