package palko.electric_meter_4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class FirstController {
    @GetMapping()
    public String FirstPage (){
        return "loginSignupForm/index";
    }
}

