package com.delivery.controller;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.delivery.controller")
public class ControllerAdvice {
//    @ExceptionHandler
//    public ResponseEntity<?> handleException(HttpMessageNotReadableException exc) {
//        ExceptionDto response = new ExceptionDto(
//                HttpStatus.BAD_REQUEST.value(),
//                exc.getMessage(),
//                System.currentTimeMillis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
}
