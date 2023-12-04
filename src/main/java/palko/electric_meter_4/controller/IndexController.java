package palko.electric_meter_4.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import palko.electric_meter_4.model.Address;
import palko.electric_meter_4.model.Index;
import palko.electric_meter_4.model.Meter;
import palko.electric_meter_4.model.Person;
import palko.electric_meter_4.service.AddressService;
import palko.electric_meter_4.service.IndexService;
import palko.electric_meter_4.service.MeterService;
import palko.electric_meter_4.service.PersonService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/indexes")
public class IndexController {
    private final IndexService indexService;
    private final AddressService addressService;
    private final MeterService meterService;
    private final PersonService personService;

    @Autowired
    public IndexController(IndexService indexService, AddressService addressService, MeterService meterService, PersonService personService) {
        this.indexService = indexService;
        this.addressService = addressService;
        this.meterService = meterService;
        this.personService = personService;
    }


    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("indexes", indexService.getAll());
        return "indexes/getAll";
    }

    @GetMapping("/{meter_id}")
    public String getByIndexMeter(Model model, @PathVariable("meter_id") int meter_id) {
        model.addAttribute("indexes", indexService.getAllIndexByMeterId(meter_id));
        //model.addAttribute("indexes",indexService.getAll());
        model.addAttribute("meter_id",meter_id);

        //        Meter meter = meterService.save(meter_id);
//        Address address = addressService.save(meter);
//        model.addAttribute("person", personService.getById(address));
        return "indexes/index";
    }

    //   @GetMapping("/{id}")
    //   public String getIndexesId(Model model, @PathVariable("id") int id, @ModelAttribute("address") Address address) {
//        model.addAttribute("indexes", indexesDAO.getById(id));
//        model.addAttribute("address",addressDAO.getById(id));
//        Optional<Address> meterAddress = indexesDAO.getById();
////        if (meterAddress.isPresent()) {
////            model.addAttribute("meter", meterAddress.get());
////        } else model.addAttribute("addresses", addressDAO.getAll());
//        return "indications/indexes";
//    }
    @GetMapping("/save/{meter_id}")
    public String saveIndexes(Model model, @PathVariable("meter_id") int meter_id) {

        model.addAttribute("meter_id", meter_id);
        return "indexes/new";
    }

    @PostMapping("/save/{meter_id}")
    public String save(@RequestParam("index") int index, @PathVariable("meter_id") int meter_id) {
        Index index1 = new Index();
        index1.setIndex(index);
        index1.setDate(LocalDateTime.now());
        index1.setMeter(meterService.getById(meter_id));
        indexService.save(index1);

        return "redirect:/meters/" + meterService.getById(meter_id).getAddress().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("index", indexService.getById(id));
        return "indexes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("index") Index index, @PathVariable("id") int id,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "indexes/edit";
        }
        Index index1 = indexService.getById(id);
        index1.setIndex(index.getIndex());
        indexService.edit(index1, id);
        return "redirect:/indexes/"+index1.getMeter().getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        indexService.delete(id);
        return "redirect:/indexes";
    }
}


