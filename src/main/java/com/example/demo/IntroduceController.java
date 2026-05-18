package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IntroduceController {

    @GetMapping("/introduce")
    public String introduce(@RequestParam(name = "name", required = false) String name, Model model) {
        if(name == null || name.isEmpty()) {
            return "introduce";
        }
        model.addAttribute("name", name);
        return "introduce-name";
    }
}