package com.example.ssiach11ex2.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class TestController {

    @GetMapping("/test")
    public String test(){
        log.info("test controller");
        return "TEST!";
    }

}
