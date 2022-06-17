package ru.drifles.app.cleaning.task;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.drifles.app.cleaning.schedule.Scheduler;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final Scheduler scheduler;

    public TaskController(TaskService taskService, Scheduler scheduler) {
        this.taskService = taskService;
        this.scheduler = scheduler;
    }

    @GetMapping("/create")
    public String getTaskCreator(Model model, @RequestParam Long roomId) {
        model.addAttribute("roomId", roomId);
        return "tasks/create";
    }

    @PostMapping("/create")
    public RedirectView createTask(@ModelAttribute("task") Task task, @RequestParam Long roomId) {
        task.setCurrentPriority(task.getDefaultPriority());

        var actual = taskService.createTask(task, roomId);
        return new RedirectView("/rooms/" + actual.getRoom().getId());
    }

    @GetMapping("/{id}/update")
    public String getTaskUpdater(Model model, @PathVariable Long id) {
        var task = taskService.getTaskById(id);
        if (scheduler.isTaskEditable(task)) {
            model.addAttribute("task", task);
        }
        return "tasks/update";
    }

    @PostMapping("/update")
    public RedirectView updateTask(@ModelAttribute("task") Task task) {
        var updatedTask = scheduler.updateTask(task);
        return new RedirectView("/rooms/" + updatedTask.getRoom().getId());
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteTask(@PathVariable Long id) {
        var task = taskService.getTaskById(id);
        scheduler.deleteTask(task);
        return new RedirectView("/rooms/" + task.getRoom().getId());
    }
}
