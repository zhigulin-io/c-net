package ru.drifles.app.cleaning.goal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.logging.Logger;

@Controller
@RequestMapping("/goals")
public class GoalController {
    private static final Logger LOG = Logger.getLogger(GoalController.class.getName());

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public String getGoalsPage(Model model) {
        model.addAttribute("goals", goalService.getAllGoals());
        return "goal/list";
    }

    @GetMapping("/update")
    public String goalEditor(Model model, @RequestParam String id) {
        try {
            var goalId = Long.parseLong(id);
            model.addAttribute("goal", goalService.getGoalById(goalId).orElse(null));
        } catch(Throwable th) {
            LOG.severe("Error in UPDATE GET method: " + th.getMessage());
        }
        return "goal/update";
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView updateGoal(@RequestBody MultiValueMap<String, String> formData) {
        try {
            var id = Long.parseLong(formData.get("id").get(0));
            var goal = formData.get("goal").get(0);
            var scoreCap = Integer.parseInt(formData.get("scoreCap").get(0));

            goalService.updateGoal(id, goal, scoreCap);
        } catch (Throwable th) {
            LOG.severe("Error in UPDATE POST method: " + th.getMessage());
        }
        return new RedirectView("/goals");
    }

    @GetMapping("/transaction")
    public String prepareTransaction(Model model) {
        model.addAttribute("goals", goalService.getAllGoals());
        return "goal/transaction";
    }

    @PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView doTransaction(@RequestBody MultiValueMap<String, String> formData) {
        try {
            var sourceId = Long.parseLong(formData.get("sourceId").get(0));
            var destinationId = Long.parseLong(formData.get("destinationId").get(0));
            var amount = Integer.parseInt(formData.get("amount").get(0));
            goalService.doTransaction(sourceId, destinationId, amount);
        } catch (Throwable th) {
            LOG.severe("Error in TRANSACTION POST method: " + th.getMessage());
        }
        return new RedirectView("/goals");
    }

    @GetMapping("/create")
    public String goalCreator() {
        return "goal/create";
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createGoal(@RequestBody MultiValueMap<String, String> formData) {
        try {
            var goal = formData.get("goal").get(0);
            var scoreCap = Integer.parseInt(formData.get("scoreCap").get(0));
            var entity = goalService.createGoal(goal, scoreCap);
            if (entity != null)
                LOG.info("GOAL was created with id = " + entity.id());
        } catch (Throwable th) {
            LOG.severe("Error in CREATE POST method: " + th.getMessage());
        }
        return new RedirectView("/goals");
    }

    @GetMapping("/delete")
    public RedirectView deleteGoal(@RequestParam String id) {
        try {
            var goalId = Long.parseLong(id);
            goalService.deleteGoalById(goalId);
        } catch(Throwable th) {
            LOG.severe("Error in UPDATE GET method: " + th.getMessage());
        }
        return new RedirectView("/goals");
    }
}
