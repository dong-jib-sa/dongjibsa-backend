package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.member.MemberDto;
import com.djs.dongjibsabackend.domain.dto.member.OAuthMemberRequest;
import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.member.PhoneNumberMemberRequest;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import com.djs.dongjibsabackend.repository.PostRepository;
import com.djs.dongjibsabackend.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "회원 API", description = "회원가입 및 탈퇴를 구현한 API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class MemberController {

    // 의존성 주입
    private final MemberService memberService;

    /* Case 1: 전화번호 검증 */
    @PostMapping("/verifyPhoneNumberUser")
    public Response verifyPhoneNumber(@RequestBody PhoneNumberMemberRequest phoneNumberMemberRequest) throws IOException {

        boolean isUser = memberService.verifyPhoneNumber(phoneNumberMemberRequest);

        if (isUser) { // True -> 존재하는 회원이다 -> 서비스 이용 화면으로 랜딩
            return Response.success("이미 존재하는 회원입니다.");
        } else { // False -> 신규 유저 -> 약관 동의 화면 랜딩
            return Response.success("신규 회원입니다. 약관 동의 후 회원 가입을 진행합니다.");
        }
    }

    /* 전화번호가 존재하지 않으면 신규회원 등록 */
    @PostMapping("/registerPhoneNumberUser")
    public Response registerPhoneNumber (@RequestBody PhoneNumberMemberRequest phoneNumberMemberRequest) {

        MemberDto savedMemberDto = memberService.savePhoneNumberUser(phoneNumberMemberRequest);
        return Response.success(savedMemberDto);
    }

    /* Case 2: OAuth 유저 검증 */
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

    /* 최초 등록하는 OAuth 유저일 경우 회원등록 */
    @PostMapping("/oauth2/registerOAuthUser")
    public Response registerOAuthUser(@RequestBody OAuthMemberRequest oAuthMemberRequest) {
        MemberDto memberDto = memberService.saveOAuthUser(oAuthMemberRequest);
        return Response.success(memberDto);
    }

    /* 회원 탈퇴 */
    @DeleteMapping("/{memberId}")
    public Response delete(@PathVariable Long memberId) {
        String result = memberService.deleteMember(memberId);
        return Response.success(result);
    }
}