package com.mark.common.exception;

public class ShoppingException extends RuntimeException {
    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ShoppingException(String msg) {
        super(msg);
    }
    public ShoppingException(String msg, Throwable ex) {
        super(msg, ex);
    }
    public ShoppingException(Throwable ex) {
        super(ex);
    }
}
