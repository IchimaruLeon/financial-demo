package com.session.demo.demo.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Setter
@Getter
public class ResponseModel<T> {

    private static final ResponseModel<Object> SUCCESS_INSTANCE;
    static {
        SUCCESS_INSTANCE = new ResponseModel<>(ResponseCodeEnum.SUCCESS.name(), ResponseCodeEnum.SUCCESS.name(), null);
    }

    private String code;
    private String message;

    @JsonInclude(Include.NON_NULL)
    private T data;

    public ResponseModel(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseModel(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity<ResponseModel> success() {
        return ResponseEntity.ok(SUCCESS_INSTANCE);
    }

    public static <T> ResponseEntity<ResponseModel<T>> success(T data) {
        return ResponseEntity.ok(successWithData(data));
    }

    public static ResponseEntity fail(ResponseCodeEnum code) {
        return fail(code, "");
    }

    public static <T> ResponseEntity<ResponseModel<T>> fail(ResponseCodeEnum code, T data) {
        return ResponseEntity.ok(failWithCodeAndData(code, data));
    }

    public static ResponseEntity<ResponseModel> fail(ResponseCodeEnum code, String message) {
        return ResponseEntity.status(code.getHttpStatus()).body(failWithCode(code, message));
    }

    private static <T> ResponseModel<T> successWithData(T data) {
        return new ResponseModel<>(ResponseCodeEnum.SUCCESS.name(), ResponseCodeEnum.SUCCESS.name(), data);
    }

    private static <T> ResponseModel<T> failWithCodeAndData(ResponseCodeEnum code, T data) {
        return new ResponseModel<>(code.name(), code.name(), data);
    }

    private static <T> ResponseModel<T> failWithCode(ResponseCodeEnum code, String message) {
        return new ResponseModel<>(code.name(), message);
    }

//    public boolean successful() {
//        return ResponseCodeEnum.SUCCESS.name().equals(code);
//    }

}
