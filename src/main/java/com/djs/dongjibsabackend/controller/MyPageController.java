package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.myPage.MyIndicatorResponse;
import com.djs.dongjibsabackend.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/my-page")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    public Response getMyIndicator(Long userId) {

        MyIndicatorResponse myIndicatorResponse = myPageService.calculate(userId);

        return Response.success(myIndicatorResponse);
    }

}
