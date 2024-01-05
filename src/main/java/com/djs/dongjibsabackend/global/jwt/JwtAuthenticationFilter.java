package com.djs.dongjibsabackend.global.jwt;

import com.djs.dongjibsabackend.domain.entity.UserEntity;
import com.djs.dongjibsabackend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PASSED_URL = "/login"; // "/login"으로 들어오는 요청은 Filter 작동 X
    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper(); // 왜 쓰지?

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // login 요청은
        if (request.getRequestURI().equals(PASSED_URL)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract RefreshToken
        String refreshToken = tokenProvider.extractRefreshToken(request)
            .filter(tokenProvider::verifyToken)
            .orElse(null);

        // Reissue Refresh-Token
        if (refreshToken != null) {
            reIssueAccessToken(response, verifyRefreshToken(refreshToken));
            return;
        }

        // Check Access-Token and Pass to another filter
        if (refreshToken == null) {
            checkAccessTokenAndAuthentication(request, response, filterChain);

        }

    }

    public Optional<UserEntity> verifyRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }

    public void reIssueAccessToken (HttpServletResponse response, Optional<UserEntity> user) {
        if (user.isPresent()) {
            String reIssuedRefreshToken = reIssueRefreshToken(user.get());
            tokenProvider.sendAccessAndRefreshTokenWithHeader(response,
                                                              tokenProvider.createAccessToken(user.get().getEmail()),
                                                              reIssuedRefreshToken);
        }
    }

    private String reIssueRefreshToken(UserEntity user) {

        String reIssuedRefreshToken = tokenProvider.createRefreshToken();
        user.updateRefreshToken(reIssuedRefreshToken);
        userRepository.saveAndFlush(user);
        return reIssuedRefreshToken;
    }

    public void checkAccessTokenAndAuthentication (HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

        tokenProvider.extractAccessToken(request).filter(tokenProvider::verifyToken)
            .ifPresent(accessToken -> tokenProvider.extreactEmail(accessToken)
                .ifPresent(email -> userRepository.findByEmail(email)
                    .ifPresent(this::saveAuthentication)));

        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(UserEntity userEntity) {
         UserDetails user = User.builder()
                                .username(userEntity.getEmail())
                                .build();
         Authentication auth = new UsernamePasswordAuthenticationToken(user, null, authoritiesMapper.mapAuthorities(user.getAuthorities()));

         SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
         securityContext.setAuthentication(auth);
         SecurityContextHolder.setContext(securityContext);
    }

}