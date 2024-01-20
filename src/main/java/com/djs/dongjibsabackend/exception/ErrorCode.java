package com.djs.dongjibsabackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // User ErrorCodes
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User Not Found."),
    DUPLICATE_PHONE_NUMBER(HttpStatus.CONFLICT, "PhoneNumber is Duplicated."),
    DUPLICATE_USER(HttpStatus.CONFLICT, "User Already Exist."),
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized."),

    // Location ErrorCodes
    LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Location Not Exist."),
    INGREDIENT_NOT_AVAILABLE(HttpStatus.NOT_FOUND, "Ingredient Not Exist."),

    // Ingredient ErrorCodes

    // Post ErrorCodes
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post Not Exist."),

    // Image ErrorCodes
    WRONG_FILE_FORMAT(HttpStatus.BAD_REQUEST, "Wrong File Format."),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "Images Not Exist."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurs.");

    private HttpStatus httpStatus;
    private String message;

}