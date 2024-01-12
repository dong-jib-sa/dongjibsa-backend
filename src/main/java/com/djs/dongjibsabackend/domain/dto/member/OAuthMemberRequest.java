package com.djs.dongjibsabackend.domain.dto.member;

import com.djs.dongjibsabackend.domain.enums.SocialType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthMemberRequest {

    private String email;
    private String socialId;
    private SocialType socialType;
}