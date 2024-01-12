package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.member.MemberDto;
import com.djs.dongjibsabackend.domain.dto.member.OAuthMemberRequest;
import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.member.UserRequest;
import com.djs.dongjibsabackend.domain.enums.SocialType;
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

    // 휴대전화번호 저장
    @PostMapping("/verifyPhoneNumber")
    public Response login (@RequestBody UserRequest userRequest) throws IOException {

        boolean isUser = memberService.verifyPhoneNumber(userRequest);

        if (isUser) {
            return Response.success("이미 회원입니다.");
        } else {
            return Response.success("회원으로 등록했습니다.");
        }
    }

    @PostMapping("/oauth2/verifyOAuthUser")
    public Response verify(@RequestBody OAuthMemberRequest oAuthMemberRequest) {

        boolean isOAuthUser = memberService.verifySocialIdAndEmail(oAuthMemberRequest);
        if (isOAuthUser) {
            if (oAuthMemberRequest.getSocialType().equals(SocialType.KAKAO)) {
                return Response.success("기존 Kakao 로그인 유저입니다.");
            } else {
                return Response.success("기존 Apple 로그인 유저입니다.");
            }
        } else {
            if (oAuthMemberRequest.getSocialType().equals(SocialType.KAKAO)) {
                return Response.success("신규 Kakao 로그인 유저입니다.");
            } else {
                return Response.success("신규 Apple 로그인 유저입니다.");
            }
        }
    }

    @PostMapping("/oauth2/registerOAuthUser")
    public Response registerOAuthUser(@RequestBody OAuthMemberRequest oAuthMemberRequest) {
        MemberDto memberDto = memberService.saveOAuthUser(oAuthMemberRequest);
        return Response.success(memberDto);
    }
}