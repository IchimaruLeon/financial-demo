package com.session.demo.demo.handler;

public class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ResponseCodeEnum code;
    private String messageKey;
    private Object[] errorParams;

    public AppException(ResponseCodeEnum code) {
        this.code = code;
    }

    public AppException(ResponseCodeEnum code, Object[] errorParams) {
        this.code = code;
        this.errorParams = errorParams;
    }

    public AppException(ResponseCodeEnum code, Object[] errorParams, Throwable cause) {
        super(cause.getMessage(), cause);
        this.code = code;
        this.errorParams = errorParams;
    }

    public AppException(ResponseCodeEnum code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public AppException(ResponseCodeEnum code, String messageKey, Object[] errorParams) {
        this.code = code;
        this.messageKey = messageKey;
        this.errorParams = errorParams;
    }

    public AppException(ResponseCodeEnum code, Throwable cause) {
        super(cause.getMessage(), cause);
        this.code = code;
    }

    public ResponseCodeEnum getCode() {
        return code;
    }

    public void setCode(ResponseCodeEnum code) {
        this.code = code;
    }

    public Object[] getErrorParams() {
        return errorParams;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setErrorParams(Object[] errorParams) {
        this.errorParams = errorParams;
    }
}
