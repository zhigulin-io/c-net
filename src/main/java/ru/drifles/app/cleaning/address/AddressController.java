package ru.drifles.app.cleaning.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.logging.Logger;

@Controller
@RequestMapping("/addresses")
public class AddressController {
    private static final Logger LOG = Logger.getLogger(AddressController.class.getName());

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public String getAddressesList(Model model) {
        var addresses = addressService.getAll();
        model.addAttribute("addresses", addresses);
        return "addresses/list";
    }

    @GetMapping("/{id}")
    public String getAddress(@PathVariable Long id, Model model) {
        var address = addressService.getAddressWithRooms(id);
        model.addAttribute("address", address);
        return "addresses/details";
    }

    @GetMapping("/create")
    public String getAddressCreator() {
        return "addresses/create";
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createAddress(@RequestParam String address) {
        try {
            var entity = addressService.createAddress(address);
            if (entity != null)
                LOG.info("Address was created with id = " + entity.getId());
        } catch (Throwable th) {
            LOG.severe("Error in CREATE POST method: " + th.getMessage());
        }

        return new RedirectView("/addresses");
    }

    @GetMapping("/{id}/update")
    public String getAddressesUpdater(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("address", addressService.getAddressById(id));
        } catch (Throwable th) {
            LOG.severe("Error in UPDATE GET method: " + th.getMessage());
        }
        return "addresses/update";
    }

    @PostMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView updateAddress(@PathVariable Long id, @RequestParam String address) {
        try {
            var entity = addressService.updateAddress(id, address);
            if (entity != null)
                LOG.info("Address was updated with id = " + entity.getId());
        } catch (Throwable th) {
            LOG.severe("Error in UPDATE POST method: " + th.getMessage());
        }
        return new RedirectView("/addresses");
    }

    @GetMapping("/{id}/delete")
    public RedirectView deleteAddress(@PathVariable Long id) {
        try {
            addressService.deleteAddressById(id);
            LOG.info("Address was deleted with id = " + id);
        } catch (Throwable th) {
            LOG.severe("Error in DELETE GET method: " + th.getMessage());
        }
        return new RedirectView("/addresses");
    }




}
