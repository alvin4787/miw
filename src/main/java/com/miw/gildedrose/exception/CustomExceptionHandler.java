package com.miw.gildedrose.exception;

import com.miw.gildedrose.controller.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<Object> handleConflict(CustomException ex, WebRequest request) {

        return handleExceptionInternal(ex, new ErrorResponse(ex.getErrorCode().getCode(), ex.getErrorCode().getDescription()), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
}
