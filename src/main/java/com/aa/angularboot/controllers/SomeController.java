package com.aa.angularboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {
    @RequestMapping("/fields")
    public String[] fields() {
        return new String[]{"city", "sex"};
    }
}
