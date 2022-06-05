package ru.drifles.app.cleaning.room;

import org.aspectj.weaver.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.drifles.app.cleaning.address.Address;
import ru.drifles.app.cleaning.address.AddressService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/rooms")
public class RoomController {
    private static final Logger LOG = Logger.getLogger(RoomController.class.getName());

    private final RoomService roomService;
    private final AddressService addressService;

    @Autowired
    public RoomController(RoomService roomService, AddressService addressService) {
        this.roomService = roomService;
        this.addressService = addressService;
    }

    @GetMapping
    public String getRoomList(Model model) {
        var rooms = roomService.getAllRooms();
        var addresses = addressService.getAllAddresses();
        rooms.forEach(room -> {
            var addressString = StreamSupport.stream(addresses.spliterator(), false)
                    .filter(address -> Objects.equals(address.id(), room.addressId()))
                    .map(Address::address)
                    .findFirst()
                    .orElseThrow();
            room.setAddress(addressString);
        });
        model.addAttribute("rooms", rooms);
        return "rooms/list";
    }

    @GetMapping("/create")
    public String getRoomCreator(Model model) {
        model.addAttribute("addresses", addressService.getAllAddresses());
        return "rooms/create";
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createRoom(@RequestParam String room, @RequestParam Long addressId) {
        var entity = roomService.createRoom(room, addressId);
        if (entity != null)
            LOG.info("Room was created with id = " + entity.id());
        return new RedirectView("/rooms");
    }

    @GetMapping("/update")
    public String getRoomUpdater(Model model, @RequestParam Long id) {
        model.addAttribute("room", roomService.getRoomById(id));
        return "rooms/update";
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView updateRoom(@RequestParam Long id, @RequestParam String room, @RequestParam Long addressId) {
        var entity = roomService.updateRoom(id, room, addressId);
        if (entity != null)
            LOG.info("Room was updated with id = " + entity.id());
        return new RedirectView("/rooms");
    }

    @GetMapping("/delete")
    public RedirectView deleteRoom(@RequestParam Long id) {
        roomService.deleteRoom(id);
        LOG.info("Room was deleted with id = " + id);
        return new RedirectView("/rooms");
    }
}
