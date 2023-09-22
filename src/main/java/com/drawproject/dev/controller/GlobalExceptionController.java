package com.drawproject.dev.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ResponseError {
        private HttpStatus status;
        private String message;
        private String throwable;

    }

    /*
    @ExceptionHandler will register the given method for a given
    exception type, so that ControllerAdvice can invoke this method
    logic if a given exception type is thrown inside the web application.
    * */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseError> exceptionHandler(Exception exception){

        ResponseError rep = new ResponseError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getCause().toString());

        return new ResponseEntity<ResponseError>(rep, HttpStatus.BAD_REQUEST);
    }

}