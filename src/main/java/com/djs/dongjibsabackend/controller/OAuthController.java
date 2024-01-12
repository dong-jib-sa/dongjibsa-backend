package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.member.MemberDto;
import com.djs.dongjibsabackend.service.OAuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class OAuthController {

    private final OAuthService oAuthService;

    @ResponseBody
    @GetMapping("/oauth2/code/kakao")
    public Response kakaoCallBack(@RequestParam String code) throws Exception{
        String accessToken = oAuthService.getKakaoAccessToken(code);
        MemberDto memberDto = oAuthService.createKakaoUser(accessToken);
        return Response.success(memberDto);
    }
}