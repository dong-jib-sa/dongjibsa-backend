package com.djs.dongjibsabackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {



    // User ErrorCodes
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User Not Found."),

    // Location ErrorCodes
    LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Location Not Exist."),
    ;

    private HttpStatus httpStatus;
    private String message;

}
