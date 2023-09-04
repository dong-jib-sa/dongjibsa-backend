package com.djs.dongjibsabackend.controller;

import com.djs.dongjibsabackend.domain.dto.Response;
import com.djs.dongjibsabackend.domain.dto.user.JoinUserRequest;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.service.SmsService;
import com.djs.dongjibsabackend.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SmsService smsService;

    @PostMapping("/join-with-phone-number")
    public Response<String> join(JoinUserRequest joinUserRequest) {
        String phoneNumber = joinUserRequest.getPhoneNumber();

        Optional<UserEntity> user = userService.findUserByPhoneNumber(phoneNumber);

        if (user.isEmpty()) {
            // 인증번호 sms 발송
            smsService.generateOTP(phoneNumber);
        }

        return Response.success("입력하신 전화번호로 인증번호 sms를 발송했습니다.");
    }

    @PostMapping("/join-with-code")
    public Response<String> verifyCode(JoinUserRequest joinUserRequest, String inputCode) throws Exception {

        String phoneNumber = joinUserRequest.getPhoneNumber();
        smsService.verifyUserOTP(phoneNumber, inputCode);
        userService.join(phoneNumber);

        return Response.success("회원가입 완료");

    }

}
