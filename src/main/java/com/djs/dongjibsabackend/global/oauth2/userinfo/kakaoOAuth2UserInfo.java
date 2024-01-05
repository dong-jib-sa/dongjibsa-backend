package com.djs.dongjibsabackend.global.oauth2.userinfo;

import java.util.Map;

public class kakaoOAuth2UserInfo extends OAuth2UserInfo{

    public kakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id")); // Long -> String
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        if (kakaoAccount == null) {
            return null;
        }
        return (String) kakaoAccount.get("email");
    }
}
