package com.mark.common.exception;

public class ImageException extends RuntimeException {
    private String msg;
    private Integer code;

    public ImageException(String msg) {
        super(msg);
    }

    public ImageException(String msg, Integer code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ImageException(String message, Throwable cause) {
        super(message, cause);
        this.msg = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
