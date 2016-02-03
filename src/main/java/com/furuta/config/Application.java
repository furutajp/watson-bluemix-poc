package com.furuta.config;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class Application {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "The time now is " + new Date();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}