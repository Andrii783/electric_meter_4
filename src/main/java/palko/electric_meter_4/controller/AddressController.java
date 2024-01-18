package palko.electric_meter_4.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import palko.electric_meter_4.model.Address;
import palko.electric_meter_4.security.PersonDetails;
import palko.electric_meter_4.service.AddressService;
import palko.electric_meter_4.service.MeterService;
import palko.electric_meter_4.service.PersonService;

@Controller
@RequestMapping("/addresses")

public class AddressController {
    private final AddressService addressService;
    private final PersonService personService;
    private final MeterService meterService;


    @Autowired
    public AddressController(AddressService addressService, PersonService personService, MeterService meterService) {
        this.addressService = addressService;
        this.personService = personService;
        this.meterService = meterService;
    }


    @GetMapping()
    public String getAddressAll(Model model) {
        model.addAttribute("addresses", addressService.getAll());
        return "addresses/getAll";
    }

    @GetMapping("/home")
    public String getAddressId(Model model, @AuthenticationPrincipal PersonDetails personDetails) {
        model.addAttribute("addresses", addressService.getAllByOwnerId(personDetails.getPerson().getId()));
        model.addAttribute("person",personService.getById(personDetails.getPerson().getId()));
        model.addAttribute("meters",meterService.getAll());

        return "addresses/address";
    }

    @GetMapping("/save/{owner_id}")
    public String saveAddress(@ModelAttribute("address") Address address, Model model, @PathVariable("owner_id") int owner_id) {
        model.addAttribute("owner_id", owner_id);
        return "addresses/newAddress";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("address") @Valid Address address,
                       BindingResult bindingResult, @AuthenticationPrincipal PersonDetails personDetails,Model model) {
        if (bindingResult.hasErrors()) {
            return "addresses/newAddress";
        }
        model.addAttribute("person",personService.getById(personDetails.getPerson().getId()));
        address.setOwner(personService.getById(personDetails.getPerson().getId()));
        Address address1 = addressService.save(address);

        return "redirect:/addresses/home";
    }

    @GetMapping("/edit/{id}")
    public String editAddress(@PathVariable("id") int id, Model model) {
        model.addAttribute("address", addressService.getById(id));
        return "addresses/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("address") @Valid Address address,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "addresses/edit";
        }
        addressService.edit(address, id);
        return "redirect:/addresses/" + id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        addressService.delete(id);
        return "redirect:/address";
    }

//    @GetMapping("/release/{id}")
//    public String release(@PathVariable("id") int id) {
//        addressService.release(id);
//        return "redirect:/animals/" + id;
//    }
//
//    @GetMapping("/assign/{id}")
//    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
//        addressDAO.assign(person, id);
//        return "redirect:/animals/" + id;
//    }


}
