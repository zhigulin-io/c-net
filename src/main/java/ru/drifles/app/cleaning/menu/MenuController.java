package ru.drifles.app.cleaning.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.drifles.app.cleaning.goal.GoalService;

@Controller
@RequestMapping({"/", "/index", "/menu"})
public class MenuController {

    private final GoalService goalService;

    public MenuController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public String getMainMenu(Model model) {
        var primaryGoal = goalService.getPrimaryGoal();
        primaryGoal.setId(null);
        primaryGoal.setScoreCap(null);
        primaryGoal.setStatus(null);
        model.addAttribute("primaryGoal", primaryGoal);
        return "menuPage";
    }
}
