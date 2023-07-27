package com.djs.dongjibsabackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestController {

    @GetMapping("/cicdTest")
    public String test1() {
        return "20230728 동집사 백엔드 서버  ◕‿◕✿ ";
    }
}
