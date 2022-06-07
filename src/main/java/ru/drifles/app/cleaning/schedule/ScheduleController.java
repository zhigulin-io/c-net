package ru.drifles.app.cleaning.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final Scheduler scheduler;

    @Autowired
    public ScheduleController(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @GetMapping
    public String getSchedules(Model model) {
        var schedules = scheduler.getSchedules().entrySet();
        model.addAttribute("schedules", schedules);
        return "schedule/list";
    }

    @PostMapping("/{id}")
    public RedirectView updateTaskStatus(@PathVariable("id") Long taskId, @RequestParam Long addressId) {
        scheduler.updateTaskStatus(addressId, taskId);
        return new RedirectView("/schedule");
    }
}
