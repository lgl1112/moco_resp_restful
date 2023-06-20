package com.lgl.moco.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    @GetMapping("/test")
    public String baseReturn(){
        return "ok";
    }


}
