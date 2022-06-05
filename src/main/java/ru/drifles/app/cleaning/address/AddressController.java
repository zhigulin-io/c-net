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
        model.addAttribute("addresses", addressService.getAllAddresses());
        return "addresses/list";
    }

    @GetMapping("/create")
    public String getAddressCreator() {
        return "addresses/create";
    }

    @GetMapping("/update")
    public String getAddressesUpdater(@RequestParam String id, Model model) {
        try {
            var addressId = Long.parseLong(id);
            model.addAttribute("address", addressService.getAddressById(addressId));
        } catch (Throwable th) {
            LOG.severe("Error in UPDATE GET method: " + th.getMessage());
        }
        return "addresses/update";
    }

    @GetMapping("/delete")
    public RedirectView deleteAddress(@RequestParam String id) {
        try {
            var addressId = Long.parseLong(id);
            addressService.deleteAddressById(addressId);
        } catch (Throwable th) {
            LOG.severe("Error in DELETE GET method: " + th.getMessage());
        }
        return new RedirectView("/addresses");
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createAddress(@RequestBody MultiValueMap<String, String> formData) {
        try {
            var address = formData.get("address").get(0);
            var entity = addressService.createAddress(address);
            if (entity != null)
                LOG.info("Address was created with id = " + entity.id());
        } catch (Throwable th) {
            LOG.severe("Error in CREATE POST method: " + th.getMessage());
        }

        return new RedirectView("/addresses");
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView updateAddress(@RequestBody MultiValueMap<String, String> formData) {
        try {
            var id = Long.parseLong(formData.get("id").get(0));
            var address = formData.get("address").get(0);
            var entity = addressService.updateAddress(id, address);
            if (entity != null)
                LOG.info("Address was updated with id = " + entity.id());
        } catch (Throwable th) {
            LOG.severe("Error in UPDATE POST method: " + th.getMessage());
        }
        return new RedirectView("/addresses");
    }
}
