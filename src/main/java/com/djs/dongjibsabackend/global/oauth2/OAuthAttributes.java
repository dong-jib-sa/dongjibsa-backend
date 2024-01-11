package com.djs.dongjibsabackend.global.oauth2;

import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.domain.enums.Role;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import com.djs.dongjibsabackend.global.oauth2.userinfo.OAuth2UserInfo;
import com.djs.dongjibsabackend.global.oauth2.userinfo.kakaoOAuth2UserInfo;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of (SocialType socialType, String userNameAttributeName,
                                      Map<String, Object> attributes) {
        // if (socialType == SocialType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        // }

        // return ofApple(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                              .nameAttributeKey(userNameAttributeName)
                              .oauth2UserInfo(new kakaoOAuth2UserInfo(attributes))
                              .build();
    }

    public MemberEntity toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return MemberEntity.builder()
                           .socialType(socialType)
                           .socialId(oauth2UserInfo.getId())
                           .email(UUID.randomUUID() + "@socialUser.com")
                           .role(Role.GUEST)
                           .build();
    }

}
