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
        return "20230727 서버 배포 테스트(첫번째 재시도)";
    }
}
