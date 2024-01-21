package com.djs.dongjibsabackend.exception;

import com.djs.dongjibsabackend.domain.dto.ErrorResponse;
import com.djs.dongjibsabackend.domain.dto.Response;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runTimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("INTERNAL SERVER ERROR");
    }

    // @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> notReadableExceptionHandler(HttpMessageNotReadableException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("요청 본문이 필요합니다.");
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appExceptionHandler(AppException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                             .body(Response.error(new ErrorResponse(e.getErrorCode().toString(), e.getErrorMessage())));
    }
}