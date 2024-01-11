package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.member.MemberRequest;
import com.djs.dongjibsabackend.service.MemberService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class MemberController {

    // 의존성 주입
    private final MemberService memberService;

    // 로그인
    @PostMapping("/verifyPhoneNumber")
    public Response login(@RequestBody MemberRequest memberRequest) throws IOException {

        boolean isUser = memberService.verifyPhoneNumber(memberRequest);

        if (isUser) {
            return Response.success("이미 회원입니다.");
        } else {
            return Response.success("회원으로 등록했습니다.");
        }
    }

}