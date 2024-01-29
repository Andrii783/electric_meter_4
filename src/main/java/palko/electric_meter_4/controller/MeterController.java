package palko.electric_meter_4.controller;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import palko.electric_meter_4.model.Address;
import palko.electric_meter_4.model.Index;
import palko.electric_meter_4.model.Meter;
import palko.electric_meter_4.service.*;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/meters")
public class MeterController {
    private final MeterService meterService;
    private final AddressService addressService;
    private final IndexService indexService;
    private final ReportGenerator reportGenerator;

    @Autowired
    public MeterController(MeterService meterService, AddressService addressService, IndexService indexService, ReportGenerator reportGenerator) {
        this.meterService = meterService;
        this.addressService = addressService;
        this.indexService = indexService;
        this.reportGenerator = reportGenerator;
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
        model.addAttribute("person", address.getOwner());
        model.addAttribute("meters", meterService.getAllMeterByAddressId(address_id));
        model.addAttribute("address_id", address_id);
        return "meters/meter";
    }


    @GetMapping("/save/{address_id}")
    public String saveMeter(@ModelAttribute("meter") Meter meter, Model model,
                            @PathVariable("address_id") int address_id) {
        Address address = addressService.getById(address_id);
        model.addAttribute("person", address.getOwner());
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
    @SneakyThrows
    @GetMapping("/generate/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("id") int id) {
        List<Index> indexList = indexService.getTwoLast(id);
        int current = indexList.get(0).getIndex();
        int previous= indexList.get(1).getIndex();
        ByteArrayOutputStream stream= reportGenerator.generateReport(id, current, previous);
        byte [] file=stream.toByteArray();
        ByteArrayResource resource = new ByteArrayResource(file);
        String fileName = "Звіт за "+ LocalDate.now();
        try {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".pdf")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length)
                .body(resource);
    }
//    @GetMapping(value = "/generate/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<?> generatePdfReport(@PathVariable("id") int id) throws IOException {
//        List<Index> indexList = indexService.getTwoLast(id);
//        int current = indexList.get(0).getIndex();
//        int previous= indexList.get(1).getIndex();
//        ReportGenerator.generateReport(id, current, previous);
//
//        try {
//            ByteArrayOutputStream reportOutputStream = ReportGenerator.generateReport(id, current, previous);
//            ByteArrayInputStream reportInputStream = new ByteArrayInputStream(reportOutputStream.toByteArray());
//
//            return ResponseEntity
//                    .ok()
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(reportInputStream);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}
