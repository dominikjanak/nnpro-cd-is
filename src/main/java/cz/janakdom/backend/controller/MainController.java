package cz.janakdom.backend.controller;

import cz.janakdom.backend.model.database.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.TimeZone;

@ApiIgnore
@RequestMapping(value = "/")
@Controller
public class MainController {
    @GetMapping("/")

    public String index(Model model) {
        model.addAttribute("datetime",  new Date().toString());
        return "index";
    }
}
