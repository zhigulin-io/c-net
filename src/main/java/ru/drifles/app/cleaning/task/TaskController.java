package ru.drifles.app.cleaning.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
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
        model.addAttribute("task", task);
        return "tasks/update";
    }

    @PostMapping("/{id}/update")
    public RedirectView updateTask(@ModelAttribute("task") Task task, @PathVariable Long id) {
        task.setId(id);
        var updatedTask = taskService.updateTask(task);
        return new RedirectView("/rooms/" + updatedTask.getRoom().getId());
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteTask(@PathVariable Long id, @RequestParam Long roomId) {
        taskService.deleteTask(id);
        return new RedirectView("/rooms/" + roomId);
    }
}
