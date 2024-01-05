package com.djs.dongjibsabackend.global.oauth2.userinfo;

import java.util.Map;

public abstract class OAuth2UserInfo {

    // 추상클래스를 상속하는 클래스에서만 사용할 수 있는 필드
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();
    public abstract String getEmail();
}
