package com.WHS.Robotics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 사업소개
    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
