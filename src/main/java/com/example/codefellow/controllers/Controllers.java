package com.example.codefellow.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controllers {

    @GetMapping("/")
    String home(){
        return "home";
    }

    @GetMapping("/post")
    String post(){
        return "post";
    }
}
