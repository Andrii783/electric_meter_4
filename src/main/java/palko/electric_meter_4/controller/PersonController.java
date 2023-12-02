package palko.electric_meter_4.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import palko.electric_meter_4.model.Person;
import palko.electric_meter_4.service.AddressService;
import palko.electric_meter_4.service.PersonService;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;
    private final AddressService addressService;

    @Autowired
    public PersonController(PersonService personService, AddressService addressService) {
        this.personService = personService;
        this.addressService = addressService;
    }


    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("people", personService.getAll());
        return "people/getAll";
    }

    @GetMapping("/{id}")
    public String getByID(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.getById(id));
        model.addAttribute("address", addressService.getById(id));
        model.addAttribute("addresses", personService.getAllAddressesPersonId(id));
        model.addAttribute("meters", personService.getAllMetersByPersonId(id));
        return "people/person";
    }

    @GetMapping("/new")
    public String newPersonPage(@ModelAttribute("person")Person person) {
        return "people/new";
    }

    @PostMapping()
    public String newPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personService.save(person);
        return "redirect:/people/"+person.getId();
    }

    @GetMapping("/edit/{id}")
    public String editPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.getById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personService.edit(person, id);
        return "redirect:/people/"+id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }



}
