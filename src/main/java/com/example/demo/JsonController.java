package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {

    @GetMapping("/json")
    public UserResponse getJson() {
        return new UserResponse("24", "유인식");
    }
}
