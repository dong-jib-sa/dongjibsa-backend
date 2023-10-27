package com.djs.dongjibsabackend.global.security.oauth2.userInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id")); // Long type id를 String으로 캐스팅
    }

    @Override
    public String getNickName() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (account == null || profile == null)
            return null;
        return (String) profile.get("nickname");
    }
}
