package com.djs.dongjibsabackend.global.oauth2.handler;

import com.djs.dongjibsabackend.domain.enums.Role;
import com.djs.dongjibsabackend.global.jwt.TokenProvider;
import com.djs.dongjibsabackend.global.oauth2.CustomOAuth2User;
import com.djs.dongjibsabackend.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final String bearer = "Bearer ";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {

        log.info("OAuth2 Login Success!");

        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // ROLE_GUEST는 뉴비
            if (oAuth2User.getRole() == Role.GUEST) {
                log.info("email: {}", oAuth2User.getEmail());
                String accessToken = tokenProvider.createAccessToken(oAuth2User.getEmail());
                response.addHeader(tokenProvider.getAccessHeader(), bearer + accessToken);

                // 이후 로직 작성
                tokenProvider.sendAccessAndRefreshTokenWithHeader(response, accessToken, null);

            } else {
                loginSuccess(response, oAuth2User);
            }

        } catch (Exception e) {
            throw e;
        }

    }

    private void loginSuccess (HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String userEmail = oAuth2User.getEmail();
        String accessToken = tokenProvider.createAccessToken(userEmail);
        String refreshToken = tokenProvider.createRefreshToken();

        response.addHeader(tokenProvider.getAccessHeader(), bearer + accessToken);
        response.addHeader(tokenProvider.getRefreshHeader(), bearer + refreshToken);

        tokenProvider.sendAccessAndRefreshTokenWithHeader(response, accessToken, refreshToken);
        tokenProvider.updateRefreshToken(userEmail, refreshToken);
    }
}
