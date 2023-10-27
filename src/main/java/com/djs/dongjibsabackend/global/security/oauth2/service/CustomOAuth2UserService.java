package com.djs.dongjibsabackend.global.security.oauth2.service;

import com.djs.dongjibsabackend.domain.SocialType;
import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.global.security.oauth2.OAuth2Attributes;
import com.djs.dongjibsabackend.repository.UserRepository;
import jakarta.validation.constraints.Size.List;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행, OAtuht2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName =
            userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oauth2User.getAttributes();

        OAuth2Attributes extractAttributes = OAuth2Attributes.of(socialType, userNameAttributeName, attributes);

        UserEntity createdUser = getUser(extractAttributes, socialType);

        return new DefaultOAuth2User(
            Collections.singleton(
                new SimpleGrantedAuthority(createdUser.getRole().getKey())),
            attributes,
            extractAttributes.getNameAttributeKey());
    }

    private SocialType getSocialType(String registrationId) {
//        if (KAKAO.equals(registrationId)) {
//            return So
//        }
        return SocialType.KAKAO;
    }

    private UserEntity getUser(OAuth2Attributes attributes, SocialType socialType) {
        UserEntity findUser = userRepository.findBySocialTypeAndSocialId(
            socialType,
            attributes.getOauth2UserInfo().getId()).orElse(null);
        return findUser;
    }

    private UserEntity saveUser(OAuth2Attributes attributes, SocialType socialType) {
        UserEntity createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return userRepository.save(createdUser);
    }
}
