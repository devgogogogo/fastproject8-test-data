package com.fastcampus.testdata.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {


    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
}
