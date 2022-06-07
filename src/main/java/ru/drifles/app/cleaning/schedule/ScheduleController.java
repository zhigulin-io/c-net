package ru.drifles.app.cleaning.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final Scheduler scheduler;

    @Autowired
    public ScheduleController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @GetMapping
    public void getSchedules() {
        scheduler.getSchedules();
    }
}
