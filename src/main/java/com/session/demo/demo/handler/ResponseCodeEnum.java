package com.session.demo.demo.handler;

import org.springframework.http.HttpStatus;

public enum ResponseCodeEnum {

    SUCCESS("00", HttpStatus.OK),
    NOT_FOUND("02", HttpStatus.NOT_FOUND),
    FAILED("03", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_BALANCE("04", HttpStatus.BAD_REQUEST),
    TRANSACTION_AMOUNT_CANT_BE_ZERO("05", HttpStatus.BAD_REQUEST);

    private String code;
    private HttpStatus httpStatus;

    ResponseCodeEnum(String code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name();
    }

}
