package com.jeet.bookmarkapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class UserManagement {

    @GetMapping("/greet")
    public String index() {
        return "Hello World";
    }
}
