package com.djs.dongjibsabackend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private String errorCode;
    private String message;
}