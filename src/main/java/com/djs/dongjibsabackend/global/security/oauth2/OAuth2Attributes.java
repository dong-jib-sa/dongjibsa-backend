package com.djs.dongjibsabackend.global.security.oauth2;

import com.djs.dongjibsabackend.domain.SocialType;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.global.security.oauth2.userInfo.KakaoOAuth2UserInfo;
import com.djs.dongjibsabackend.global.security.oauth2.userInfo.OAuth2UserInfo;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2Attributes {

    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    public OAuth2Attributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuth2Attributes of(SocialType socialType,
                                     String userNameAttributeName, Map<String, Object> attributes) {

//        if (socialType == SocialType.KAKAO) {
//            return ofKakao(userNameAttributeName, attributes);
//        }

        return ofKakao(userNameAttributeName, attributes);

//        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName,
                                           Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
            .nameAttributeKey(userNameAttributeName)
            .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                              .build();
    }

    public UserEntity toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return UserEntity.builder()
                         .socialType(socialType)
                         .socialId(oauth2UserInfo.getId())
                         .build();
    }

}
