package com.session.demo.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({AppException.class})
    public ResponseEntity<ResponseModel> handleAppException(AppException ae) {
        return ResponseModel.fail(ae.getCode(), ae.getMessageKey());
    }

}
