package edu.icesi.pensamiento_computacional.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    
    @GetMapping("Home")
    public String getHome(){
        return"home";
    }

    
}