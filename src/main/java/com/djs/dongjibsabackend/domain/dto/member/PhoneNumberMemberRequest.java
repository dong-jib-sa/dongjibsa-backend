package com.djs.dongjibsabackend.domain.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberMemberRequest {

    private String nickName;
    private String phoneNumber;
}