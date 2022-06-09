package ru.drifles.app.cleaning.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.drifles.app.cleaning.schedule.Scheduler;

import java.util.logging.Logger;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private static final Logger LOG = Logger.getLogger(RoomController.class.getName());

    private final RoomService roomService;
    private final Scheduler scheduler;

    @Autowired
    public RoomController(RoomService roomService, Scheduler scheduler) {
        this.roomService = roomService;
        this.scheduler = scheduler;
    }

    @GetMapping("/{id}")
    public String getRoomDetails(Model model, @PathVariable Long id) {
        var room = roomService.getRoomWithTasks(id);
        for (var task : room.getTasks()) {
            task.setEditable(scheduler.isTaskEditable(task));
        }
        model.addAttribute("room", room);
        return "rooms/details";
    }

    @GetMapping("/create")
    public String getRoomCreator(Model model, @RequestParam Long addressId) {
        model.addAttribute("addressId", addressId);
        return "rooms/create";
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createRoom(@RequestParam String room, @RequestParam Long addressId) {
        var entity = roomService.createRoom(room, addressId);
        LOG.info("Room was created with id = " + entity.getId());
        return new RedirectView("/addresses/" + addressId);
    }

    @GetMapping("/{id}/update")
    public String getRoomUpdater(Model model, @PathVariable Long id) {
        var room = roomService.getRoomById(id);
        model.addAttribute("room", roomService.getRoomById(id));
        model.addAttribute("addressId", room.getAddress().getId());
        return "rooms/update";
    }

    @PostMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView updateRoom(@RequestParam Long id, @RequestParam String room) {
        var entity = roomService.updateRoom(id, room);
        LOG.info("Room was updated with id = " + entity.getId());
        return new RedirectView("/addresses/" + entity.getAddress().getId());
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteRoom(@PathVariable Long id, @RequestParam Long addressId) {
        roomService.deleteRoom(id);
        LOG.info("Room was deleted with id = " + id);
        return new RedirectView("/addresses/" + addressId);
    }
}
