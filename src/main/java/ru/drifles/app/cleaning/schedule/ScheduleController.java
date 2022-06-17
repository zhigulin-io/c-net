package ru.drifles.app.cleaning.schedule;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.drifles.app.cleaning.task.TaskService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final Scheduler scheduler;
    private final TaskService taskService;

    public ScheduleController(Scheduler scheduler, TaskService taskService) {
        this.scheduler = scheduler;
        this.taskService = taskService;
    }

    @GetMapping
    public String getSchedules(Model model) {
        var schedules = scheduler.getSchedules().entrySet();
        model.addAttribute("schedules", schedules);
        return "schedule/list";
    }

    @GetMapping("/{id}")
    public RedirectView updateTaskStatus(@PathVariable("id") Long id) {
        var task = taskService.getTaskById(id);
        scheduler.updateTaskStatus(task);
        return new RedirectView("/schedule");
    }

    @PostMapping("/timeCap")
    public RedirectView setTimeCap(@RequestParam(name = "timeCap") Integer timeCap) {
        scheduler.setTimeCap(timeCap);
        return new RedirectView("/");
    }
}
