package com.djs.dongjibsabackend.global.config;

import com.djs.dongjibsabackend.global.jwt.JwtAuthenticationFilter;
import com.djs.dongjibsabackend.global.jwt.TokenProvider;
import com.djs.dongjibsabackend.global.oauth2.handler.OAuth2LoginFailureHandler;
import com.djs.dongjibsabackend.global.oauth2.handler.OAuth2LoginSuccessHandler;
import com.djs.dongjibsabackend.global.oauth2.service.CustomOAuth2UserService;
import com.djs.dongjibsabackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] SWAGGER_AUTH = {
        "/api-docs/swagger-config/**",
        "/swagger-ui.html/**",
        "/swagger-ui/**",
        "/api-docs/**",
        "/swagger-resources/**",
        "/webjars/**",
        };
    private static final String[] ALLOWED_AUTH = {}; // 홈 화면

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//-------------------------------------------------------------------------------------------
        http
            .formLogin().disable() // FormLogin 사용 X
            .httpBasic().disable()// httpBasic 사용 X
            .csrf().disable() // csrf 보안 사용 X
            .headers(headers -> headers.frameOptions().disable())
//-------------------------------------------------------------------------------------------
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//-------------------------------------------------------------------------------------------
            .authorizeHttpRequests(authorize ->
                    authorize.requestMatchers(SWAGGER_AUTH).permitAll()
                             .requestMatchers(ALLOWED_AUTH).permitAll()
                             .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
            )
//-------------------------------------------------------------------------------------------
            .oauth2Login()
            .successHandler(oAuth2LoginSuccessHandler)
            .failureHandler(oAuth2LoginFailureHandler)
            .userInfoEndpoint().userService(customOAuth2UserService);
        return http.build();
    }
}