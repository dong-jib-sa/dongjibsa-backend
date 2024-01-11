package com.djs.dongjibsabackend.global.oauth2.service;

import com.djs.dongjibsabackend.domain.entity.MemberEntity;
import com.djs.dongjibsabackend.domain.enums.SocialType;
import com.djs.dongjibsabackend.global.oauth2.CustomOAuth2User;
import com.djs.dongjibsabackend.global.oauth2.OAuthAttributes;
import com.djs.dongjibsabackend.repository.MemberRepository;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() - OAuth2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User user = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint().getUserNameAttributeName(); // Key
        Map<String, Object> attributes = user.getAttributes();

        OAuthAttributes extracted = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        MemberEntity createdUser = getUser(extracted, socialType);

        return new CustomOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
            attributes,
            extracted.getNameAttributeKey(),
            createdUser.getEmail(),
            createdUser.getRole()
        );
    }

    private SocialType getSocialType (String registrationId) {
        if (KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.APPLE;
    }

    private MemberEntity getUser(OAuthAttributes attributes, SocialType socialType) {
        MemberEntity findUser = memberRepository.findBySocialTypeAndSocialId(socialType, attributes.getOauth2UserInfo().getId())
                                                .orElse(null);

        if (findUser == null) {
            return saveUser(attributes, socialType);
        }
        return findUser;
    }

    private MemberEntity saveUser(OAuthAttributes attributes, SocialType socialType) {
        MemberEntity createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return memberRepository.save(createdUser);
    }
}
