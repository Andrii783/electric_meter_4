package palko.electric_meter_4.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import palko.electric_meter_4.model.Address;
import palko.electric_meter_4.model.Meter;
import palko.electric_meter_4.service.AddressService;
import palko.electric_meter_4.service.IndexService;
import palko.electric_meter_4.service.MeterService;
import palko.electric_meter_4.service.PersonService;

@Controller
@RequestMapping("/meters")
public class MeterController {
    private final MeterService meterService;
    private final AddressService addressService;
    private final IndexService indexService;
    private final PersonService personService;

    @Autowired
    public MeterController(MeterService meterService, AddressService addressService, IndexService indexService, PersonService personService) {
        this.meterService = meterService;
        this.addressService = addressService;
        this.indexService = indexService;
        this.personService = personService;
    }


    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("meters", meterService.getAll());
        return "meters/getAll";
    }
//    @GetMapping("/one/{id}")
//    public String get

    @GetMapping("/{address_id}")
    public String getByAllMeterAddressId(Model model, @PathVariable("address_id") int address_id) {
        Address address = addressService.getById(address_id);
        model.addAttribute("person",address.getOwner());
        model.addAttribute("meters", meterService.getAllMeterByAddressId(address_id));
        model.addAttribute("address_id",address_id);
        return "meters/meter";
    }



    @GetMapping("/save/{address_id}")
    public String saveMeter(@ModelAttribute("meter") Meter meter, Model model,
                            @PathVariable("address_id") int address_id) {
        Address address = addressService.getById(address_id);
        model.addAttribute("person",address.getOwner());
        model.addAttribute("address_id", address_id);
        return "meters/new";
    }

    @PostMapping("/save/{address_id}")
    public String save(@ModelAttribute("meter") @Valid Meter meter, BindingResult bindingResult,
                       @PathVariable("address_id") int address_id) {
        if (bindingResult.hasErrors()) {
            return "meters/new";
        }
        meter.setAddress(addressService.getById(address_id));
        meterService.save(meter);
        return "redirect:/meters/" + address_id;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("meter", meterService.getById(id));
        return "meters/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("meter") @Valid Meter meter, @PathVariable("id") int id,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "meters/edit";
        }
        meterService.edit(meter, id);
        return "redirect:/meter";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        meterService.delete(id);
        return "redirect:/meter";
    }
}
