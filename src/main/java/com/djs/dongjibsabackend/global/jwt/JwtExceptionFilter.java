package com.djs.dongjibsabackend.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter {

    public String secretKey;


}
